/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.FileWrapper;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v2TagFactory;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.MpegFrame;
import com.mpatric.mp3agic.MutableInteger;
import com.mpatric.mp3agic.NoSuchTagException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Mp3File
extends FileWrapper {
    private static final int DEFAULT_BUFFER_LENGTH = 65536;
    private static final int MINIMUM_BUFFER_LENGTH = 40;
    private static final int XING_MARKER_OFFSET_1 = 13;
    private static final int XING_MARKER_OFFSET_2 = 21;
    private static final int XING_MARKER_OFFSET_3 = 36;
    protected int bufferLength;
    private int xingOffset = -1;
    private int startOffset = -1;
    private int endOffset = -1;
    private int frameCount = 0;
    private Map<Integer, MutableInteger> bitrates = new HashMap<Integer, MutableInteger>();
    private int xingBitrate;
    private double bitrate = 0.0;
    private String channelMode;
    private String emphasis;
    private String layer;
    private String modeExtension;
    private int sampleRate;
    private boolean copyright;
    private boolean original;
    private String version;
    private ID3v1 id3v1Tag;
    private ID3v2 id3v2Tag;
    private byte[] customTag;
    private boolean scanFile;

    protected Mp3File() {
    }

    public Mp3File(String string) throws IOException, UnsupportedTagException, InvalidDataException {
        this(string, 65536, true);
    }

    public Mp3File(String string, int n) throws IOException, UnsupportedTagException, InvalidDataException {
        this(string, n, true);
    }

    public Mp3File(String string, boolean bl) throws IOException, UnsupportedTagException, InvalidDataException {
        this(string, 65536, bl);
    }

    public Mp3File(String string, int n, boolean bl) throws IOException, UnsupportedTagException, InvalidDataException {
        super(string);
        this.init(n, bl);
    }

    public Mp3File(File file) throws IOException, UnsupportedTagException, InvalidDataException {
        this(file, 65536, true);
    }

    public Mp3File(File file, int n) throws IOException, UnsupportedTagException, InvalidDataException {
        this(file, n, true);
    }

    public Mp3File(File file, int n, boolean bl) throws IOException, UnsupportedTagException, InvalidDataException {
        super(file);
        this.init(n, bl);
    }

    public Mp3File(Path path) throws IOException, UnsupportedTagException, InvalidDataException {
        this(path, 65536, true);
    }

    public Mp3File(Path path, int n) throws IOException, UnsupportedTagException, InvalidDataException {
        this(path, n, true);
    }

    public Mp3File(Path path, int n, boolean bl) throws IOException, UnsupportedTagException, InvalidDataException {
        super(path);
        this.init(n, bl);
    }

    private void init(int n, boolean bl) throws IOException, UnsupportedTagException, InvalidDataException {
        if (n < 41) {
            throw new IllegalArgumentException("Buffer too small");
        }
        this.bufferLength = n;
        this.scanFile = bl;
        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(this.path, StandardOpenOption.READ);){
            this.initId3v1Tag(seekableByteChannel);
            this.scanFile(seekableByteChannel);
            if (this.startOffset < 0) {
                throw new InvalidDataException("No mpegs frames found");
            }
            this.initId3v2Tag(seekableByteChannel);
            if (bl) {
                this.initCustomTag(seekableByteChannel);
            }
        }
    }

    protected int preScanFile(SeekableByteChannel seekableByteChannel) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        try {
            seekableByteChannel.position(0L);
            byteBuffer.clear();
            int n = seekableByteChannel.read(byteBuffer);
            if (n == 10) {
                try {
                    byte[] arrby = byteBuffer.array();
                    ID3v2TagFactory.sanityCheckTag(arrby);
                    return 10 + BufferTools.unpackSynchsafeInteger(arrby[6], arrby[7], arrby[8], arrby[9]);
                }
                catch (NoSuchTagException | UnsupportedTagException baseException) {}
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return 0;
    }

    private void scanFile(SeekableByteChannel seekableByteChannel) throws IOException, InvalidDataException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.bufferLength);
        int n = this.preScanFile(seekableByteChannel);
        seekableByteChannel.position(n);
        boolean bl = false;
        int n2 = n;
        while (!bl) {
            byteBuffer.clear();
            int n3 = seekableByteChannel.read(byteBuffer);
            byte[] arrby = byteBuffer.array();
            if (n3 < this.bufferLength) {
                bl = true;
            }
            if (n3 < 40) continue;
            try {
                int n4 = 0;
                if (this.startOffset < 0) {
                    n4 = this.scanBlockForStart(arrby, n3, n, n4);
                    if (this.startOffset >= 0 && !this.scanFile) {
                        return;
                    }
                    n2 = this.startOffset;
                }
                n4 = this.scanBlock(arrby, n3, n, n4);
                seekableByteChannel.position(n += n4);
            }
            catch (InvalidDataException invalidDataException) {
                if (this.frameCount < 2) {
                    this.startOffset = -1;
                    this.xingOffset = -1;
                    this.frameCount = 0;
                    this.bitrates.clear();
                    bl = false;
                    n = n2 + 1;
                    if (n == 0) {
                        throw new InvalidDataException("Valid start of mpeg frames not found", invalidDataException);
                    }
                    seekableByteChannel.position(n);
                    continue;
                }
                return;
            }
        }
    }

    private int scanBlockForStart(byte[] arrby, int n, int n2, int n3) {
        while (n3 < n - 40) {
            if (arrby[n3] == -1 && (arrby[n3 + 1] & -32) == -32) {
                try {
                    MpegFrame mpegFrame = new MpegFrame(arrby[n3], arrby[n3 + 1], arrby[n3 + 2], arrby[n3 + 3]);
                    if (this.xingOffset < 0 && this.isXingFrame(arrby, n3)) {
                        this.xingOffset = n2 + n3;
                        this.xingBitrate = mpegFrame.getBitrate();
                        n3 += mpegFrame.getLengthInBytes();
                        continue;
                    }
                    this.startOffset = n2 + n3;
                    this.channelMode = mpegFrame.getChannelMode();
                    this.emphasis = mpegFrame.getEmphasis();
                    this.layer = mpegFrame.getLayer();
                    this.modeExtension = mpegFrame.getModeExtension();
                    this.sampleRate = mpegFrame.getSampleRate();
                    this.version = mpegFrame.getVersion();
                    this.copyright = mpegFrame.isCopyright();
                    this.original = mpegFrame.isOriginal();
                    ++this.frameCount;
                    this.addBitrate(mpegFrame.getBitrate());
                    return n3 += mpegFrame.getLengthInBytes();
                }
                catch (InvalidDataException invalidDataException) {
                    ++n3;
                    continue;
                }
            }
            ++n3;
        }
        return n3;
    }

    private int scanBlock(byte[] arrby, int n, int n2, int n3) throws InvalidDataException {
        while (n3 < n - 40) {
            MpegFrame mpegFrame = new MpegFrame(arrby[n3], arrby[n3 + 1], arrby[n3 + 2], arrby[n3 + 3]);
            this.sanityCheckFrame(mpegFrame, n2 + n3);
            int n4 = n2 + n3 + mpegFrame.getLengthInBytes() - 1;
            if (n4 >= this.maxEndOffset()) break;
            this.endOffset = n2 + n3 + mpegFrame.getLengthInBytes() - 1;
            ++this.frameCount;
            this.addBitrate(mpegFrame.getBitrate());
            n3 += mpegFrame.getLengthInBytes();
        }
        return n3;
    }

    private int maxEndOffset() {
        int n = (int)this.getLength();
        if (this.hasId3v1Tag()) {
            n -= 128;
        }
        return n;
    }

    private boolean isXingFrame(byte[] arrby, int n) {
        if (arrby.length >= n + 13 + 3) {
            if ("Xing".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, n + 13, 4))) {
                return true;
            }
            if ("Info".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, n + 13, 4))) {
                return true;
            }
            if (arrby.length >= n + 21 + 3) {
                if ("Xing".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, n + 21, 4))) {
                    return true;
                }
                if ("Info".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, n + 21, 4))) {
                    return true;
                }
                if (arrby.length >= n + 36 + 3) {
                    if ("Xing".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, n + 36, 4))) {
                        return true;
                    }
                    if ("Info".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, n + 36, 4))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void sanityCheckFrame(MpegFrame mpegFrame, int n) throws InvalidDataException {
        if (this.sampleRate != mpegFrame.getSampleRate()) {
            throw new InvalidDataException("Inconsistent frame header");
        }
        if (!this.layer.equals(mpegFrame.getLayer())) {
            throw new InvalidDataException("Inconsistent frame header");
        }
        if (!this.version.equals(mpegFrame.getVersion())) {
            throw new InvalidDataException("Inconsistent frame header");
        }
        if ((long)(n + mpegFrame.getLengthInBytes()) > this.getLength()) {
            throw new InvalidDataException("Frame would extend beyond end of file");
        }
    }

    private void addBitrate(int n) {
        Integer n2 = new Integer(n);
        MutableInteger mutableInteger = this.bitrates.get(n2);
        if (mutableInteger != null) {
            mutableInteger.increment();
        } else {
            this.bitrates.put(n2, new MutableInteger(1));
        }
        this.bitrate = (this.bitrate * (double)(this.frameCount - 1) + (double)n) / (double)this.frameCount;
    }

    private void initId3v1Tag(SeekableByteChannel seekableByteChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        seekableByteChannel.position(this.getLength() - 128L);
        byteBuffer.clear();
        int n = seekableByteChannel.read(byteBuffer);
        if (n < 128) {
            throw new IOException("Not enough bytes read");
        }
        try {
            this.id3v1Tag = new ID3v1Tag(byteBuffer.array());
        }
        catch (NoSuchTagException noSuchTagException) {
            this.id3v1Tag = null;
        }
    }

    private void initId3v2Tag(SeekableByteChannel seekableByteChannel) throws IOException, UnsupportedTagException, InvalidDataException {
        if (this.xingOffset == 0 || this.startOffset == 0) {
            this.id3v2Tag = null;
        } else {
            int n = this.hasXingFrame() ? this.xingOffset : this.startOffset;
            ByteBuffer byteBuffer = ByteBuffer.allocate(n);
            seekableByteChannel.position(0L);
            byteBuffer.clear();
            int n2 = seekableByteChannel.read(byteBuffer);
            if (n2 < n) {
                throw new IOException("Not enough bytes read");
            }
            try {
                this.id3v2Tag = ID3v2TagFactory.createTag(byteBuffer.array());
            }
            catch (NoSuchTagException noSuchTagException) {
                this.id3v2Tag = null;
            }
        }
    }

    private void initCustomTag(SeekableByteChannel seekableByteChannel) throws IOException {
        int n = (int)(this.getLength() - (long)(this.endOffset + 1));
        if (this.hasId3v1Tag()) {
            n -= 128;
        }
        if (n <= 0) {
            this.customTag = null;
        } else {
            ByteBuffer byteBuffer = ByteBuffer.allocate(n);
            seekableByteChannel.position(this.endOffset + 1);
            byteBuffer.clear();
            int n2 = seekableByteChannel.read(byteBuffer);
            this.customTag = byteBuffer.array();
            if (n2 < n) {
                throw new IOException("Not enough bytes read");
            }
        }
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    public int getStartOffset() {
        return this.startOffset;
    }

    public int getEndOffset() {
        return this.endOffset;
    }

    public long getLengthInMilliseconds() {
        double d = 8 * (this.endOffset - this.startOffset);
        return (long)(d / this.bitrate + 0.5);
    }

    public long getLengthInSeconds() {
        return (this.getLengthInMilliseconds() + 500L) / 1000L;
    }

    public boolean isVbr() {
        return this.bitrates.size() > 1;
    }

    public int getBitrate() {
        return (int)(this.bitrate + 0.5);
    }

    public Map<Integer, MutableInteger> getBitrates() {
        return this.bitrates;
    }

    public String getChannelMode() {
        return this.channelMode;
    }

    public boolean isCopyright() {
        return this.copyright;
    }

    public String getEmphasis() {
        return this.emphasis;
    }

    public String getLayer() {
        return this.layer;
    }

    public String getModeExtension() {
        return this.modeExtension;
    }

    public boolean isOriginal() {
        return this.original;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean hasXingFrame() {
        return this.xingOffset >= 0;
    }

    public int getXingOffset() {
        return this.xingOffset;
    }

    public int getXingBitrate() {
        return this.xingBitrate;
    }

    public boolean hasId3v1Tag() {
        return this.id3v1Tag != null;
    }

    public ID3v1 getId3v1Tag() {
        return this.id3v1Tag;
    }

    public void setId3v1Tag(ID3v1 iD3v1) {
        this.id3v1Tag = iD3v1;
    }

    public void removeId3v1Tag() {
        this.id3v1Tag = null;
    }

    public boolean hasId3v2Tag() {
        return this.id3v2Tag != null;
    }

    public ID3v2 getId3v2Tag() {
        return this.id3v2Tag;
    }

    public void setId3v2Tag(ID3v2 iD3v2) {
        this.id3v2Tag = iD3v2;
    }

    public void removeId3v2Tag() {
        this.id3v2Tag = null;
    }

    public boolean hasCustomTag() {
        return this.customTag != null;
    }

    public byte[] getCustomTag() {
        return this.customTag;
    }

    public void setCustomTag(byte[] arrby) {
        this.customTag = arrby;
    }

    public void removeCustomTag() {
        this.customTag = null;
    }

    public void save(String string) throws IOException, NotSupportedException {
        if (this.path.toAbsolutePath().compareTo(Paths.get(string, new String[0]).toAbsolutePath()) == 0) {
            throw new IllegalArgumentException("Save filename same as source filename");
        }
        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(Paths.get(string, new String[0]), EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE), new FileAttribute[0]);){
            ByteBuffer byteBuffer;
            if (this.hasId3v2Tag()) {
                byteBuffer = ByteBuffer.wrap(this.id3v2Tag.toBytes());
                byteBuffer.rewind();
                seekableByteChannel.write(byteBuffer);
            }
            this.saveMpegFrames(seekableByteChannel);
            if (this.hasCustomTag()) {
                byteBuffer = ByteBuffer.wrap(this.customTag);
                byteBuffer.rewind();
                seekableByteChannel.write(byteBuffer);
            }
            if (this.hasId3v1Tag()) {
                byteBuffer = ByteBuffer.wrap(this.id3v1Tag.toBytes());
                byteBuffer.rewind();
                seekableByteChannel.write(byteBuffer);
            }
            seekableByteChannel.close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveMpegFrames(SeekableByteChannel seekableByteChannel) throws IOException {
        int n = this.xingOffset;
        if (n < 0) {
            n = this.startOffset;
        }
        if (n < 0) {
            return;
        }
        if (this.endOffset < n) {
            return;
        }
        SeekableByteChannel seekableByteChannel2 = Files.newByteChannel(this.path, StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.bufferLength);
        try {
            seekableByteChannel2.position(n);
            do {
                byteBuffer.clear();
                int n2 = seekableByteChannel2.read(byteBuffer);
                byteBuffer.rewind();
                if (n + n2 > this.endOffset) break;
                byteBuffer.limit(n2);
                seekableByteChannel.write(byteBuffer);
                n += n2;
            } while (true);
            byteBuffer.limit(this.endOffset - n + 1);
            seekableByteChannel.write(byteBuffer);
        }
        finally {
            seekableByteChannel2.close();
        }
    }
}

