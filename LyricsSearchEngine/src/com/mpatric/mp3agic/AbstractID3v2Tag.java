/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.EncodedText;
import com.mpatric.mp3agic.ID3v1Genres;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v2ChapterFrameData;
import com.mpatric.mp3agic.ID3v2ChapterTOCFrameData;
import com.mpatric.mp3agic.ID3v2CommentFrameData;
import com.mpatric.mp3agic.ID3v2Frame;
import com.mpatric.mp3agic.ID3v2FrameSet;
import com.mpatric.mp3agic.ID3v2ObseleteFrame;
import com.mpatric.mp3agic.ID3v2ObseletePictureFrameData;
import com.mpatric.mp3agic.ID3v2PictureFrameData;
import com.mpatric.mp3agic.ID3v2PopmFrameData;
import com.mpatric.mp3agic.ID3v2TagFactory;
import com.mpatric.mp3agic.ID3v2TextFrameData;
import com.mpatric.mp3agic.ID3v2UrlFrameData;
import com.mpatric.mp3agic.ID3v2WWWFrameData;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NoSuchTagException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractID3v2Tag
implements ID3v2 {
    public static final String ID_IMAGE = "APIC";
    public static final String ID_ENCODER = "TENC";
    public static final String ID_URL = "WXXX";
    public static final String ID_ARTIST_URL = "WOAR";
    public static final String ID_COMMERCIAL_URL = "WCOM";
    public static final String ID_COPYRIGHT_URL = "WCOP";
    public static final String ID_AUDIOFILE_URL = "WOAF";
    public static final String ID_AUDIOSOURCE_URL = "WOAS";
    public static final String ID_RADIOSTATION_URL = "WORS";
    public static final String ID_PAYMENT_URL = "WPAY";
    public static final String ID_PUBLISHER_URL = "WPUB";
    public static final String ID_COPYRIGHT = "TCOP";
    public static final String ID_ORIGINAL_ARTIST = "TOPE";
    public static final String ID_BPM = "TBPM";
    public static final String ID_COMPOSER = "TCOM";
    public static final String ID_PUBLISHER = "TPUB";
    public static final String ID_COMMENT = "COMM";
    public static final String ID_TEXT_LYRICS = "USLT";
    public static final String ID_GENRE = "TCON";
    public static final String ID_YEAR = "TYER";
    public static final String ID_DATE = "TDAT";
    public static final String ID_ALBUM = "TALB";
    public static final String ID_TITLE = "TIT2";
    public static final String ID_KEY = "TKEY";
    public static final String ID_ARTIST = "TPE1";
    public static final String ID_ALBUM_ARTIST = "TPE2";
    public static final String ID_TRACK = "TRCK";
    public static final String ID_PART_OF_SET = "TPOS";
    public static final String ID_COMPILATION = "TCMP";
    public static final String ID_CHAPTER_TOC = "CTOC";
    public static final String ID_CHAPTER = "CHAP";
    public static final String ID_GROUPING = "TIT1";
    public static final String ID_RATING = "POPM";
    public static final String ID_IMAGE_OBSELETE = "PIC";
    public static final String ID_ENCODER_OBSELETE = "TEN";
    public static final String ID_URL_OBSELETE = "WXX";
    public static final String ID_COPYRIGHT_OBSELETE = "TCR";
    public static final String ID_ORIGINAL_ARTIST_OBSELETE = "TOA";
    public static final String ID_BPM_OBSELETE = "TBP";
    public static final String ID_COMPOSER_OBSELETE = "TCM";
    public static final String ID_PUBLISHER_OBSELETE = "TBP";
    public static final String ID_COMMENT_OBSELETE = "COM";
    public static final String ID_GENRE_OBSELETE = "TCO";
    public static final String ID_YEAR_OBSELETE = "TYE";
    public static final String ID_DATE_OBSELETE = "TDA";
    public static final String ID_ALBUM_OBSELETE = "TAL";
    public static final String ID_TITLE_OBSELETE = "TT2";
    public static final String ID_KEY_OBSELETE = "TKE";
    public static final String ID_ARTIST_OBSELETE = "TP1";
    public static final String ID_ALBUM_ARTIST_OBSELETE = "TP2";
    public static final String ID_TRACK_OBSELETE = "TRK";
    public static final String ID_PART_OF_SET_OBSELETE = "TPA";
    public static final String ID_COMPILATION_OBSELETE = "TCP";
    public static final String ID_GROUPING_OBSELETE = "TT1";
    public static final byte PICTURETYPE_OTHER = 0;
    public static final byte PICTURETYPE_32PXICON = 1;
    public static final byte PICTURETYPE_OTHERICON = 2;
    public static final byte PICTURETYPE_FRONTCOVER = 3;
    public static final byte PICTURETYPE_BACKCOVER = 4;
    public static final byte PICTURETYPE_LEAFLET = 5;
    public static final byte PICTURETYPE_MEDIA = 6;
    public static final byte PICTURETYPE_LEADARTIST = 7;
    public static final byte PICTURETYPE_ARTIST = 8;
    public static final byte PICTURETYPE_CONDUCTOR = 9;
    public static final byte PICTURETYPE_BAND = 10;
    public static final byte PICTURETYPE_COMPOSER = 11;
    public static final byte PICTURETYPE_LYRICIST = 12;
    public static final byte PICTURETYPE_RECORDINGLOCATION = 13;
    public static final byte PICTURETYPE_DURING_RECORDING = 14;
    public static final byte PICTURETYPE_DURING_PERFORMANCE = 15;
    public static final byte PICTURETYPE_SCREEN_CAPTURE = 16;
    public static final byte PICTURETYPE_ILLUSTRATION = 18;
    public static final byte PICTURETYPE_BAND_LOGOTYPE = 19;
    public static final byte PICTURETYPE_PUBLISHER_LOGOTYPE = 20;
    protected static final String TAG = "ID3";
    protected static final String FOOTER_TAG = "3DI";
    protected static final int HEADER_LENGTH = 10;
    protected static final int FOOTER_LENGTH = 10;
    protected static final int MAJOR_VERSION_OFFSET = 3;
    protected static final int MINOR_VERSION_OFFSET = 4;
    protected static final int FLAGS_OFFSET = 5;
    protected static final int DATA_LENGTH_OFFSET = 6;
    protected static final int FOOTER_BIT = 4;
    protected static final int EXPERIMENTAL_BIT = 5;
    protected static final int EXTENDED_HEADER_BIT = 6;
    protected static final int COMPRESSION_BIT = 6;
    protected static final int UNSYNCHRONISATION_BIT = 7;
    protected static final int PADDING_LENGTH = 256;
    private static final String ITUNES_COMMENT_DESCRIPTION = "iTunNORM";
    protected boolean unsynchronisation = false;
    protected boolean extendedHeader = false;
    protected boolean experimental = false;
    protected boolean footer = false;
    protected boolean compression = false;
    protected boolean padding = false;
    protected String version = null;
    private int dataLength = 0;
    private int extendedHeaderLength;
    private byte[] extendedHeaderData;
    private boolean obseleteFormat = false;
    private final Map<String, ID3v2FrameSet> frameSets = new TreeMap<String, ID3v2FrameSet>();

    public AbstractID3v2Tag() {
    }

    public AbstractID3v2Tag(byte[] arrby) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        this(arrby, false);
    }

    public AbstractID3v2Tag(byte[] arrby, boolean bl) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        this.obseleteFormat = bl;
        this.unpackTag(arrby);
    }

    private void unpackTag(byte[] arrby) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        ID3v2TagFactory.sanityCheckTag(arrby);
        int n = this.unpackHeader(arrby);
        try {
            if (this.extendedHeader) {
                n = this.unpackExtendedHeader(arrby, n);
            }
            int n2 = this.dataLength;
            if (this.footer) {
                n2 -= 10;
            }
            n = this.unpackFrames(arrby, n, n2);
            if (this.footer) {
                n = this.unpackFooter(arrby, this.dataLength);
            }
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new InvalidDataException("Premature end of tag", arrayIndexOutOfBoundsException);
        }
    }

    private int unpackHeader(byte[] arrby) throws UnsupportedTagException, InvalidDataException {
        byte by = arrby[3];
        byte by2 = arrby[4];
        this.version = "" + by + "." + by2;
        if (by != 2 && by != 3 && by != 4) {
            throw new UnsupportedTagException("Unsupported version " + this.version);
        }
        this.unpackFlags(arrby);
        if ((arrby[5] & 15) != 0) {
            throw new UnsupportedTagException("Unrecognised bits in header");
        }
        this.dataLength = BufferTools.unpackSynchsafeInteger(arrby[6], arrby[7], arrby[8], arrby[9]);
        if (this.dataLength < 1) {
            throw new InvalidDataException("Zero size tag");
        }
        return 10;
    }

    protected abstract void unpackFlags(byte[] var1);

    private int unpackExtendedHeader(byte[] arrby, int n) {
        this.extendedHeaderLength = BufferTools.unpackSynchsafeInteger(arrby[n], arrby[n + 1], arrby[n + 2], arrby[n + 3]) + 4;
        this.extendedHeaderData = BufferTools.copyBuffer(arrby, n + 4, this.extendedHeaderLength);
        return this.extendedHeaderLength;
    }

    protected int unpackFrames(byte[] arrby, int n, int n2) {
        int n3;
        ID3v2Frame iD3v2Frame;
        for (n3 = n; n3 <= n2; n3 += iD3v2Frame.getLength()) {
            try {
                iD3v2Frame = this.createFrame(arrby, n3);
                this.addFrame(iD3v2Frame, false);
                continue;
            }
            catch (InvalidDataException invalidDataException) {
                break;
            }
        }
        return n3;
    }

    protected void addFrame(ID3v2Frame iD3v2Frame, boolean bl) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(iD3v2Frame.getId());
        if (iD3v2FrameSet == null) {
            iD3v2FrameSet = new ID3v2FrameSet(iD3v2Frame.getId());
            iD3v2FrameSet.addFrame(iD3v2Frame);
            this.frameSets.put(iD3v2Frame.getId(), iD3v2FrameSet);
        } else if (bl) {
            iD3v2FrameSet.clear();
            iD3v2FrameSet.addFrame(iD3v2Frame);
        } else {
            iD3v2FrameSet.addFrame(iD3v2Frame);
        }
    }

    protected ID3v2Frame createFrame(byte[] arrby, int n) throws InvalidDataException {
        if (this.obseleteFormat) {
            return new ID3v2ObseleteFrame(arrby, n);
        }
        return new ID3v2Frame(arrby, n);
    }

    protected ID3v2Frame createFrame(String string, byte[] arrby) {
        if (this.obseleteFormat) {
            return new ID3v2ObseleteFrame(string, arrby);
        }
        return new ID3v2Frame(string, arrby);
    }

    private int unpackFooter(byte[] arrby, int n) throws InvalidDataException {
        if (!"3DI".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, n, "3DI".length()))) {
            throw new InvalidDataException("Invalid footer");
        }
        return 10;
    }

    @Override
    public byte[] toBytes() throws NotSupportedException {
        byte[] arrby = new byte[this.getLength()];
        this.packTag(arrby);
        return arrby;
    }

    public void packTag(byte[] arrby) throws NotSupportedException {
        int n = this.packHeader(arrby, 0);
        if (this.extendedHeader) {
            n = this.packExtendedHeader(arrby, n);
        }
        n = this.packFrames(arrby, n);
        if (this.footer) {
            n = this.packFooter(arrby, this.dataLength);
        }
    }

    private int packHeader(byte[] arrby, int n) {
        byte by;
        try {
            BufferTools.stringIntoByteBuffer("ID3", 0, "ID3".length(), arrby, n);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        String[] arrstring = this.version.split("\\.");
        if (arrstring.length > 0) {
            arrby[n + 3] = by = Byte.parseByte(arrstring[0]);
        }
        if (arrstring.length > 1) {
            arrby[n + 4] = by = Byte.parseByte(arrstring[1]);
        }
        this.packFlags(arrby, n);
        BufferTools.packSynchsafeInteger(this.getDataLength(), arrby, n + 6);
        return n + 10;
    }

    protected abstract void packFlags(byte[] var1, int var2);

    private int packExtendedHeader(byte[] arrby, int n) {
        BufferTools.packSynchsafeInteger(this.extendedHeaderLength, arrby, n);
        BufferTools.copyIntoByteBuffer(this.extendedHeaderData, 0, this.extendedHeaderData.length, arrby, n + 4);
        return n + 4 + this.extendedHeaderData.length;
    }

    public int packFrames(byte[] arrby, int n) throws NotSupportedException {
        int n2 = this.packSpecifiedFrames(arrby, n, null, "APIC");
        n2 = this.packSpecifiedFrames(arrby, n2, "APIC", null);
        return n2;
    }

    private int packSpecifiedFrames(byte[] arrby, int n, String string, String string2) throws NotSupportedException {
        for (ID3v2FrameSet iD3v2FrameSet : this.frameSets.values()) {
            if (string != null && !string.equals(iD3v2FrameSet.getId()) || string2 != null && string2.equals(iD3v2FrameSet.getId())) continue;
            for (ID3v2Frame iD3v2Frame : iD3v2FrameSet.getFrames()) {
                if (iD3v2Frame.getDataLength() <= 0) continue;
                byte[] arrby2 = iD3v2Frame.toBytes();
                BufferTools.copyIntoByteBuffer(arrby2, 0, arrby2.length, arrby, n);
                n += arrby2.length;
            }
        }
        return n;
    }

    private int packFooter(byte[] arrby, int n) {
        byte by;
        try {
            BufferTools.stringIntoByteBuffer("3DI", 0, "3DI".length(), arrby, n);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        String[] arrstring = this.version.split("\\.");
        if (arrstring.length > 0) {
            arrby[n + 3] = by = Byte.parseByte(arrstring[0]);
        }
        if (arrstring.length > 1) {
            arrby[n + 4] = by = Byte.parseByte(arrstring[1]);
        }
        this.packFlags(arrby, n);
        BufferTools.packSynchsafeInteger(this.getDataLength(), arrby, n + 6);
        return n + 10;
    }

    private int calculateDataLength() {
        int n = 0;
        if (this.extendedHeader) {
            n += this.extendedHeaderLength;
        }
        if (this.footer) {
            n += 10;
        } else if (this.padding) {
            n += 256;
        }
        for (ID3v2FrameSet iD3v2FrameSet : this.frameSets.values()) {
            for (ID3v2Frame iD3v2Frame : iD3v2FrameSet.getFrames()) {
                n += iD3v2Frame.getLength();
            }
        }
        return n;
    }

    protected boolean useFrameUnsynchronisation() {
        return false;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    protected void invalidateDataLength() {
        this.dataLength = 0;
    }

    @Override
    public int getDataLength() {
        if (this.dataLength == 0) {
            this.dataLength = this.calculateDataLength();
        }
        return this.dataLength;
    }

    @Override
    public int getLength() {
        return this.getDataLength() + 10;
    }

    @Override
    public Map<String, ID3v2FrameSet> getFrameSets() {
        return this.frameSets;
    }

    @Override
    public boolean getPadding() {
        return this.padding;
    }

    @Override
    public void setPadding(boolean bl) {
        if (this.padding != bl) {
            this.invalidateDataLength();
            this.padding = bl;
        }
    }

    @Override
    public boolean hasFooter() {
        return this.footer;
    }

    @Override
    public void setFooter(boolean bl) {
        if (this.footer != bl) {
            this.invalidateDataLength();
            this.footer = bl;
        }
    }

    @Override
    public boolean hasUnsynchronisation() {
        return this.unsynchronisation;
    }

    @Override
    public void setUnsynchronisation(boolean bl) {
        if (this.unsynchronisation != bl) {
            this.invalidateDataLength();
            this.unsynchronisation = bl;
        }
    }

    @Override
    public boolean getObseleteFormat() {
        return this.obseleteFormat;
    }

    @Override
    public String getTrack() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TRK" : "TRCK");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setTrack(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TRCK", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getPartOfSet() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TPA" : "TPOS");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setPartOfSet(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TPOS", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public boolean isCompilation() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCP" : "TCMP");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return "1".equals(iD3v2TextFrameData.getText().toString());
        }
        return false;
    }

    @Override
    public void setCompilation(boolean bl) {
        this.invalidateDataLength();
        ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(bl ? "1" : "0"));
        this.addFrame(this.createFrame("TCMP", iD3v2TextFrameData.toBytes()), true);
    }

    @Override
    public String getGrouping() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TT1" : "TIT1");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setGrouping(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TIT1", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getArtist() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TP1" : "TPE1");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setArtist(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TPE1", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getAlbumArtist() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TP2" : "TPE2");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setAlbumArtist(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TPE2", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getTitle() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TT2" : "TIT2");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setTitle(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TIT2", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getAlbum() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TAL" : "TALB");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setAlbum(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TALB", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getYear() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TYE" : "TYER");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setYear(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TYER", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getDate() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TDA" : "TDAT");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setDate(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TDAT", iD3v2TextFrameData.toBytes()), true);
        }
    }

    private int getGenre(String string) {
        if (string != null && string.length() > 0) {
            try {
                return this.extractGenreNumber(string);
            }
            catch (NumberFormatException numberFormatException) {
                String string2 = this.extractGenreDescription(string);
                return ID3v1Genres.matchGenreDescription(string2);
            }
        }
        return -1;
    }

    @Override
    public int getGenre() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCO" : "TCON");
        if (iD3v2TextFrameData == null || iD3v2TextFrameData.getText() == null) {
            return -1;
        }
        return this.getGenre(iD3v2TextFrameData.getText().toString());
    }

    @Override
    public void setGenre(int n) {
        if (n >= 0) {
            this.invalidateDataLength();
            String string = n < ID3v1Genres.GENRES.length ? ID3v1Genres.GENRES[n] : "";
            String string2 = "(" + Integer.toString(n) + ")" + string;
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string2));
            this.addFrame(this.createFrame("TCON", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public int getBPM() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TBP" : "TBPM");
        if (iD3v2TextFrameData == null || iD3v2TextFrameData.getText() == null) {
            return -1;
        }
        String string = iD3v2TextFrameData.getText().toString();
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            return (int)Float.parseFloat(string.trim().replaceAll(",", "."));
        }
    }

    @Override
    public void setBPM(int n) {
        if (n >= 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(Integer.toString(n)));
            this.addFrame(this.createFrame("TBPM", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getKey() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TKE" : "TKEY");
        if (iD3v2TextFrameData == null || iD3v2TextFrameData.getText() == null) {
            return null;
        }
        return iD3v2TextFrameData.getText().toString();
    }

    @Override
    public void setKey(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TKEY", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getGenreDescription() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCO" : "TCON");
        if (iD3v2TextFrameData == null || iD3v2TextFrameData.getText() == null) {
            return null;
        }
        String string = iD3v2TextFrameData.getText().toString();
        if (string != null) {
            int n = this.getGenre(string);
            if (n >= 0 && n < ID3v1Genres.GENRES.length) {
                return ID3v1Genres.GENRES[n];
            }
            String string2 = this.extractGenreDescription(string);
            if (string2 != null && string2.length() > 0) {
                return string2;
            }
        }
        return null;
    }

    @Override
    public void setGenreDescription(String string) throws IllegalArgumentException {
        int n = ID3v1Genres.matchGenreDescription(string);
        if (n < 0) {
            throw new IllegalArgumentException("Unknown genre: " + string);
        }
        this.setGenre(n);
    }

    protected int extractGenreNumber(String string) throws NumberFormatException {
        int n;
        String string2 = string.trim();
        if (string2.length() > 0 && string2.charAt(0) == '(' && (n = string2.indexOf(41)) > 0) {
            return Integer.parseInt(string2.substring(1, n));
        }
        return Integer.parseInt(string2);
    }

    protected String extractGenreDescription(String string) throws NumberFormatException {
        String string2 = string.trim();
        if (string2.length() > 0) {
            int n;
            if (string2.charAt(0) == '(' && (n = string2.indexOf(41)) > 0) {
                return string2.substring(n + 1);
            }
            return string2;
        }
        return null;
    }

    @Override
    public String getComment() {
        ID3v2CommentFrameData iD3v2CommentFrameData = this.extractCommentFrameData(this.obseleteFormat ? "COM" : "COMM", false);
        if (iD3v2CommentFrameData != null && iD3v2CommentFrameData.getComment() != null) {
            return iD3v2CommentFrameData.getComment().toString();
        }
        return null;
    }

    @Override
    public void setComment(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2CommentFrameData iD3v2CommentFrameData = new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), "eng", null, new EncodedText(string));
            this.addFrame(this.createFrame("COMM", iD3v2CommentFrameData.toBytes()), true);
        }
    }

    @Override
    public String getItunesComment() {
        ID3v2CommentFrameData iD3v2CommentFrameData = this.extractCommentFrameData(this.obseleteFormat ? "COM" : "COMM", true);
        if (iD3v2CommentFrameData != null && iD3v2CommentFrameData.getComment() != null) {
            return iD3v2CommentFrameData.getComment().toString();
        }
        return null;
    }

    @Override
    public void setItunesComment(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2CommentFrameData iD3v2CommentFrameData = new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), "eng", new EncodedText("iTunNORM"), new EncodedText(string));
            this.addFrame(this.createFrame("COMM", iD3v2CommentFrameData.toBytes()), true);
        }
    }

    protected ID3v2CommentFrameData extractLyricsFrameData(String string) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(string);
        if (iD3v2FrameSet != null) {
            for (ID3v2Frame iD3v2Frame : iD3v2FrameSet.getFrames()) {
                try {
                    ID3v2CommentFrameData iD3v2CommentFrameData = new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData());
                    return iD3v2CommentFrameData;
                }
                catch (InvalidDataException invalidDataException) {
                }
            }
        }
        return null;
    }

    @Override
    public String getLyrics() {
        if (this.obseleteFormat) {
            return null;
        }
        ID3v2CommentFrameData iD3v2CommentFrameData = this.extractLyricsFrameData("USLT");
        if (iD3v2CommentFrameData != null) {
            return iD3v2CommentFrameData.getComment().toString();
        }
        return null;
    }

    @Override
    public void setLyrics(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2CommentFrameData iD3v2CommentFrameData = new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), "eng", null, new EncodedText(string));
            this.addFrame(this.createFrame("USLT", iD3v2CommentFrameData.toBytes()), true);
        }
    }

    @Override
    public String getComposer() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCM" : "TCOM");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setComposer(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TCOM", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getPublisher() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TBP" : "TPUB");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setPublisher(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TPUB", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getOriginalArtist() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TOA" : "TOPE");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setOriginalArtist(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TOPE", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getCopyright() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCR" : "TCOP");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setCopyright(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TCOP", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public String getArtistUrl() {
        ID3v2WWWFrameData iD3v2WWWFrameData = this.extractWWWFrameData("WOAR");
        if (iD3v2WWWFrameData != null) {
            return iD3v2WWWFrameData.getUrl();
        }
        return null;
    }

    @Override
    public void setArtistUrl(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2WWWFrameData iD3v2WWWFrameData = new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), string);
            this.addFrame(this.createFrame("WOAR", iD3v2WWWFrameData.toBytes()), true);
        }
    }

    @Override
    public String getCommercialUrl() {
        ID3v2WWWFrameData iD3v2WWWFrameData = this.extractWWWFrameData("WCOM");
        if (iD3v2WWWFrameData != null) {
            return iD3v2WWWFrameData.getUrl();
        }
        return null;
    }

    @Override
    public void setCommercialUrl(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2WWWFrameData iD3v2WWWFrameData = new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), string);
            this.addFrame(this.createFrame("WCOM", iD3v2WWWFrameData.toBytes()), true);
        }
    }

    @Override
    public String getCopyrightUrl() {
        ID3v2WWWFrameData iD3v2WWWFrameData = this.extractWWWFrameData("WCOP");
        if (iD3v2WWWFrameData != null) {
            return iD3v2WWWFrameData.getUrl();
        }
        return null;
    }

    @Override
    public void setCopyrightUrl(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2WWWFrameData iD3v2WWWFrameData = new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), string);
            this.addFrame(this.createFrame("WCOP", iD3v2WWWFrameData.toBytes()), true);
        }
    }

    @Override
    public String getAudiofileUrl() {
        ID3v2WWWFrameData iD3v2WWWFrameData = this.extractWWWFrameData("WOAF");
        if (iD3v2WWWFrameData != null) {
            return iD3v2WWWFrameData.getUrl();
        }
        return null;
    }

    @Override
    public void setAudiofileUrl(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2WWWFrameData iD3v2WWWFrameData = new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), string);
            this.addFrame(this.createFrame("WOAF", iD3v2WWWFrameData.toBytes()), true);
        }
    }

    @Override
    public String getAudioSourceUrl() {
        ID3v2WWWFrameData iD3v2WWWFrameData = this.extractWWWFrameData("WOAS");
        if (iD3v2WWWFrameData != null) {
            return iD3v2WWWFrameData.getUrl();
        }
        return null;
    }

    @Override
    public void setAudioSourceUrl(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2WWWFrameData iD3v2WWWFrameData = new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), string);
            this.addFrame(this.createFrame("WOAS", iD3v2WWWFrameData.toBytes()), true);
        }
    }

    @Override
    public String getRadiostationUrl() {
        ID3v2WWWFrameData iD3v2WWWFrameData = this.extractWWWFrameData("WORS");
        if (iD3v2WWWFrameData != null) {
            return iD3v2WWWFrameData.getUrl();
        }
        return null;
    }

    @Override
    public void setRadiostationUrl(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2WWWFrameData iD3v2WWWFrameData = new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), string);
            this.addFrame(this.createFrame("WORS", iD3v2WWWFrameData.toBytes()), true);
        }
    }

    @Override
    public String getPaymentUrl() {
        ID3v2WWWFrameData iD3v2WWWFrameData = this.extractWWWFrameData("WPAY");
        if (iD3v2WWWFrameData != null) {
            return iD3v2WWWFrameData.getUrl();
        }
        return null;
    }

    @Override
    public void setPaymentUrl(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2WWWFrameData iD3v2WWWFrameData = new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), string);
            this.addFrame(this.createFrame("WPAY", iD3v2WWWFrameData.toBytes()), true);
        }
    }

    @Override
    public String getPublisherUrl() {
        ID3v2WWWFrameData iD3v2WWWFrameData = this.extractWWWFrameData("WPUB");
        if (iD3v2WWWFrameData != null) {
            return iD3v2WWWFrameData.getUrl();
        }
        return null;
    }

    @Override
    public void setPublisherUrl(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2WWWFrameData iD3v2WWWFrameData = new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), string);
            this.addFrame(this.createFrame("WPUB", iD3v2WWWFrameData.toBytes()), true);
        }
    }

    @Override
    public String getUrl() {
        ID3v2UrlFrameData iD3v2UrlFrameData = this.extractUrlFrameData(this.obseleteFormat ? "WXX" : "WXXX");
        if (iD3v2UrlFrameData != null) {
            return iD3v2UrlFrameData.getUrl();
        }
        return null;
    }

    @Override
    public void setUrl(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2UrlFrameData iD3v2UrlFrameData = new ID3v2UrlFrameData(this.useFrameUnsynchronisation(), null, string);
            this.addFrame(this.createFrame("WXXX", iD3v2UrlFrameData.toBytes()), true);
        }
    }

    @Override
    public ArrayList<ID3v2ChapterFrameData> getChapters() {
        if (this.obseleteFormat) {
            return null;
        }
        return this.extractChapterFrameData("CHAP");
    }

    @Override
    public void setChapters(ArrayList<ID3v2ChapterFrameData> arrayList) {
        if (arrayList != null) {
            this.invalidateDataLength();
            boolean bl = true;
            for (ID3v2ChapterFrameData iD3v2ChapterFrameData : arrayList) {
                if (bl) {
                    bl = false;
                    this.addFrame(this.createFrame("CHAP", iD3v2ChapterFrameData.toBytes()), true);
                    continue;
                }
                this.addFrame(this.createFrame("CHAP", iD3v2ChapterFrameData.toBytes()), false);
            }
        }
    }

    @Override
    public ArrayList<ID3v2ChapterTOCFrameData> getChapterTOC() {
        if (this.obseleteFormat) {
            return null;
        }
        return this.extractChapterTOCFrameData("CTOC");
    }

    @Override
    public void setChapterTOC(ArrayList<ID3v2ChapterTOCFrameData> arrayList) {
        if (arrayList != null) {
            this.invalidateDataLength();
            boolean bl = true;
            for (ID3v2ChapterTOCFrameData iD3v2ChapterTOCFrameData : arrayList) {
                if (bl) {
                    bl = false;
                    this.addFrame(this.createFrame("CTOC", iD3v2ChapterTOCFrameData.toBytes()), true);
                    continue;
                }
                this.addFrame(this.createFrame("CTOC", iD3v2ChapterTOCFrameData.toBytes()), false);
            }
        }
    }

    @Override
    public String getEncoder() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(this.obseleteFormat ? "TEN" : "TENC");
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    @Override
    public void setEncoder(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame("TENC", iD3v2TextFrameData.toBytes()), true);
        }
    }

    @Override
    public byte[] getAlbumImage() {
        ID3v2PictureFrameData iD3v2PictureFrameData = this.createPictureFrameData(this.obseleteFormat ? "PIC" : "APIC");
        if (iD3v2PictureFrameData != null) {
            return iD3v2PictureFrameData.getImageData();
        }
        return null;
    }

    @Override
    public void setAlbumImage(byte[] arrby, String string) {
        this.setAlbumImage(arrby, string, (byte)0, null);
    }

    @Override
    public void setAlbumImage(byte[] arrby, String string, byte by, String string2) {
        if (arrby != null && arrby.length > 0 && string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2PictureFrameData iD3v2PictureFrameData = new ID3v2PictureFrameData(this.useFrameUnsynchronisation(), string, by, null == string2 ? null : new EncodedText(string2), arrby);
            this.addFrame(this.createFrame("APIC", iD3v2PictureFrameData.toBytes()), true);
        }
    }

    @Override
    public void clearAlbumImage() {
        this.clearFrameSet(this.obseleteFormat ? "PIC" : "APIC");
    }

    @Override
    public String getAlbumImageMimeType() {
        ID3v2PictureFrameData iD3v2PictureFrameData = this.createPictureFrameData(this.obseleteFormat ? "PIC" : "APIC");
        if (iD3v2PictureFrameData != null && iD3v2PictureFrameData.getMimeType() != null) {
            return iD3v2PictureFrameData.getMimeType();
        }
        return null;
    }

    @Override
    public void clearFrameSet(String string) {
        if (this.frameSets.remove(string) != null) {
            this.invalidateDataLength();
        }
    }

    @Override
    public int getWmpRating() {
        ID3v2PopmFrameData iD3v2PopmFrameData = this.extractPopmFrameData("POPM");
        if (iD3v2PopmFrameData != null && iD3v2PopmFrameData.getAddress() != null) {
            return iD3v2PopmFrameData.getRating();
        }
        return -1;
    }

    @Override
    public void setWmpRating(int n) {
        if (n >= 0 && n < 6) {
            this.invalidateDataLength();
            ID3v2PopmFrameData iD3v2PopmFrameData = new ID3v2PopmFrameData(this.useFrameUnsynchronisation(), n);
            byte[] arrby = iD3v2PopmFrameData.toBytes();
            this.addFrame(this.createFrame("POPM", arrby), true);
        }
    }

    private ArrayList<ID3v2ChapterFrameData> extractChapterFrameData(String string) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(string);
        if (iD3v2FrameSet != null) {
            ArrayList<ID3v2ChapterFrameData> arrayList = new ArrayList<ID3v2ChapterFrameData>();
            List<ID3v2Frame> list = iD3v2FrameSet.getFrames();
            for (ID3v2Frame iD3v2Frame : list) {
                try {
                    ID3v2ChapterFrameData iD3v2ChapterFrameData = new ID3v2ChapterFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData());
                    arrayList.add(iD3v2ChapterFrameData);
                }
                catch (InvalidDataException invalidDataException) {}
            }
            return arrayList;
        }
        return null;
    }

    private ArrayList<ID3v2ChapterTOCFrameData> extractChapterTOCFrameData(String string) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(string);
        if (iD3v2FrameSet != null) {
            ArrayList<ID3v2ChapterTOCFrameData> arrayList = new ArrayList<ID3v2ChapterTOCFrameData>();
            List<ID3v2Frame> list = iD3v2FrameSet.getFrames();
            for (ID3v2Frame iD3v2Frame : list) {
                try {
                    ID3v2ChapterTOCFrameData iD3v2ChapterTOCFrameData = new ID3v2ChapterTOCFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData());
                    arrayList.add(iD3v2ChapterTOCFrameData);
                }
                catch (InvalidDataException invalidDataException) {}
            }
            return arrayList;
        }
        return null;
    }

    protected ID3v2TextFrameData extractTextFrameData(String string) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(string);
        if (iD3v2FrameSet != null) {
            ID3v2Frame iD3v2Frame = iD3v2FrameSet.getFrames().get(0);
            try {
                ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData());
                return iD3v2TextFrameData;
            }
            catch (InvalidDataException invalidDataException) {
                // empty catch block
            }
        }
        return null;
    }

    private ID3v2WWWFrameData extractWWWFrameData(String string) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(string);
        if (iD3v2FrameSet != null) {
            ID3v2Frame iD3v2Frame = iD3v2FrameSet.getFrames().get(0);
            try {
                ID3v2WWWFrameData iD3v2WWWFrameData = new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData());
                return iD3v2WWWFrameData;
            }
            catch (InvalidDataException invalidDataException) {
                // empty catch block
            }
        }
        return null;
    }

    private ID3v2UrlFrameData extractUrlFrameData(String string) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(string);
        if (iD3v2FrameSet != null) {
            ID3v2Frame iD3v2Frame = iD3v2FrameSet.getFrames().get(0);
            try {
                ID3v2UrlFrameData iD3v2UrlFrameData = new ID3v2UrlFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData());
                return iD3v2UrlFrameData;
            }
            catch (InvalidDataException invalidDataException) {
                // empty catch block
            }
        }
        return null;
    }

    private ID3v2CommentFrameData extractCommentFrameData(String string, boolean bl) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(string);
        if (iD3v2FrameSet != null) {
            for (ID3v2Frame iD3v2Frame : iD3v2FrameSet.getFrames()) {
                try {
                    ID3v2CommentFrameData iD3v2CommentFrameData = new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData());
                    if (bl && "iTunNORM".equals(iD3v2CommentFrameData.getDescription().toString())) {
                        return iD3v2CommentFrameData;
                    }
                    if (bl) continue;
                    return iD3v2CommentFrameData;
                }
                catch (InvalidDataException invalidDataException) {
                }
            }
        }
        return null;
    }

    private ID3v2PictureFrameData createPictureFrameData(String string) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(string);
        if (iD3v2FrameSet != null) {
            ID3v2Frame iD3v2Frame = iD3v2FrameSet.getFrames().get(0);
            try {
                ID3v2PictureFrameData iD3v2PictureFrameData = this.obseleteFormat ? new ID3v2ObseletePictureFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData()) : new ID3v2PictureFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData());
                return iD3v2PictureFrameData;
            }
            catch (InvalidDataException invalidDataException) {
                // empty catch block
            }
        }
        return null;
    }

    private ID3v2PopmFrameData extractPopmFrameData(String string) {
        ID3v2FrameSet iD3v2FrameSet = this.frameSets.get(string);
        if (iD3v2FrameSet != null) {
            ID3v2Frame iD3v2Frame = iD3v2FrameSet.getFrames().get(0);
            try {
                ID3v2PopmFrameData iD3v2PopmFrameData = new ID3v2PopmFrameData(this.useFrameUnsynchronisation(), iD3v2Frame.getData());
                return iD3v2PopmFrameData;
            }
            catch (InvalidDataException invalidDataException) {
                // empty catch block
            }
        }
        return null;
    }

    public boolean equals(Object object) {
        if (!(object instanceof AbstractID3v2Tag)) {
            return false;
        }
        if (super.equals(object)) {
            return true;
        }
        AbstractID3v2Tag abstractID3v2Tag = (AbstractID3v2Tag)object;
        if (this.unsynchronisation != abstractID3v2Tag.unsynchronisation) {
            return false;
        }
        if (this.extendedHeader != abstractID3v2Tag.extendedHeader) {
            return false;
        }
        if (this.experimental != abstractID3v2Tag.experimental) {
            return false;
        }
        if (this.footer != abstractID3v2Tag.footer) {
            return false;
        }
        if (this.compression != abstractID3v2Tag.compression) {
            return false;
        }
        if (this.dataLength != abstractID3v2Tag.dataLength) {
            return false;
        }
        if (this.extendedHeaderLength != abstractID3v2Tag.extendedHeaderLength) {
            return false;
        }
        if (this.version == null) {
            if (abstractID3v2Tag.version != null) {
                return false;
            }
        } else {
            if (abstractID3v2Tag.version == null) {
                return false;
            }
            if (!this.version.equals(abstractID3v2Tag.version)) {
                return false;
            }
        }
        if (this.frameSets == null) {
            if (abstractID3v2Tag.frameSets != null) {
                return false;
            }
        } else {
            if (abstractID3v2Tag.frameSets == null) {
                return false;
            }
            if (!this.frameSets.equals(abstractID3v2Tag.frameSets)) {
                return false;
            }
        }
        return true;
    }
}

