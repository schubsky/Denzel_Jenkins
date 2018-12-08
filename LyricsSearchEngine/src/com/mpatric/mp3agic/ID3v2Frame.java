/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ID3v2Frame {
    private static final int HEADER_LENGTH = 10;
    private static final int ID_OFFSET = 0;
    private static final int ID_LENGTH = 4;
    protected static final int DATA_LENGTH_OFFSET = 4;
    private static final int FLAGS1_OFFSET = 8;
    private static final int FLAGS2_OFFSET = 9;
    private static final int PRESERVE_TAG_BIT = 6;
    private static final int PRESERVE_FILE_BIT = 5;
    private static final int READ_ONLY_BIT = 4;
    private static final int GROUP_BIT = 6;
    private static final int COMPRESSION_BIT = 3;
    private static final int ENCRYPTION_BIT = 2;
    private static final int UNSYNCHRONISATION_BIT = 1;
    private static final int DATA_LENGTH_INDICATOR_BIT = 0;
    protected String id;
    protected int dataLength = 0;
    protected byte[] data = null;
    private boolean preserveTag = false;
    private boolean preserveFile = false;
    private boolean readOnly = false;
    private boolean group = false;
    private boolean compression = false;
    private boolean encryption = false;
    private boolean unsynchronisation = false;
    private boolean dataLengthIndicator = false;

    public ID3v2Frame(byte[] arrby, int n) throws InvalidDataException {
        this.unpackFrame(arrby, n);
    }

    public ID3v2Frame(String string, byte[] arrby) {
        this.id = string;
        this.data = arrby;
        this.dataLength = arrby.length;
    }

    protected final void unpackFrame(byte[] arrby, int n) throws InvalidDataException {
        int n2 = this.unpackHeader(arrby, n);
        this.sanityCheckUnpackedHeader();
        this.data = BufferTools.copyBuffer(arrby, n2, this.dataLength);
    }

    protected int unpackHeader(byte[] arrby, int n) {
        this.id = BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, n + 0, 4);
        this.unpackDataLength(arrby, n);
        this.unpackFlags(arrby, n);
        return n + 10;
    }

    protected void unpackDataLength(byte[] arrby, int n) {
        this.dataLength = BufferTools.unpackInteger(arrby[n + 4], arrby[n + 4 + 1], arrby[n + 4 + 2], arrby[n + 4 + 3]);
    }

    private void unpackFlags(byte[] arrby, int n) {
        this.preserveTag = BufferTools.checkBit(arrby[n + 8], 6);
        this.preserveFile = BufferTools.checkBit(arrby[n + 8], 5);
        this.readOnly = BufferTools.checkBit(arrby[n + 8], 4);
        this.group = BufferTools.checkBit(arrby[n + 9], 6);
        this.compression = BufferTools.checkBit(arrby[n + 9], 3);
        this.encryption = BufferTools.checkBit(arrby[n + 9], 2);
        this.unsynchronisation = BufferTools.checkBit(arrby[n + 9], 1);
        this.dataLengthIndicator = BufferTools.checkBit(arrby[n + 9], 0);
    }

    protected void sanityCheckUnpackedHeader() throws InvalidDataException {
        for (int i = 0; i < this.id.length(); ++i) {
            if (this.id.charAt(i) >= 'A' && this.id.charAt(i) <= 'Z' || this.id.charAt(i) >= '0' && this.id.charAt(i) <= '9') continue;
            throw new InvalidDataException("Not a valid frame - invalid tag " + this.id);
        }
    }

    public byte[] toBytes() throws NotSupportedException {
        byte[] arrby = new byte[this.getLength()];
        this.packFrame(arrby, 0);
        return arrby;
    }

    public void toBytes(byte[] arrby, int n) throws NotSupportedException {
        this.packFrame(arrby, n);
    }

    public void packFrame(byte[] arrby, int n) throws NotSupportedException {
        this.packHeader(arrby, n);
        BufferTools.copyIntoByteBuffer(this.data, 0, this.data.length, arrby, n + 10);
    }

    private void packHeader(byte[] arrby, int n) {
        try {
            BufferTools.stringIntoByteBuffer(this.id, 0, this.id.length(), arrby, 0);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        BufferTools.copyIntoByteBuffer(this.packDataLength(), 0, 4, arrby, 4);
        BufferTools.copyIntoByteBuffer(this.packFlags(), 0, 2, arrby, 8);
    }

    protected byte[] packDataLength() {
        return BufferTools.packInteger(this.dataLength);
    }

    private byte[] packFlags() {
        byte[] arrby = new byte[2];
        arrby[0] = BufferTools.setBit(arrby[0], 6, this.preserveTag);
        arrby[0] = BufferTools.setBit(arrby[0], 5, this.preserveFile);
        arrby[0] = BufferTools.setBit(arrby[0], 4, this.readOnly);
        arrby[1] = BufferTools.setBit(arrby[1], 6, this.group);
        arrby[1] = BufferTools.setBit(arrby[1], 3, this.compression);
        arrby[1] = BufferTools.setBit(arrby[1], 2, this.encryption);
        arrby[1] = BufferTools.setBit(arrby[1], 1, this.unsynchronisation);
        arrby[1] = BufferTools.setBit(arrby[1], 0, this.dataLengthIndicator);
        return arrby;
    }

    public String getId() {
        return this.id;
    }

    public int getDataLength() {
        return this.dataLength;
    }

    public int getLength() {
        return this.dataLength + 10;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] arrby) {
        this.data = arrby;
        this.dataLength = arrby == null ? 0 : arrby.length;
    }

    public boolean hasDataLengthIndicator() {
        return this.dataLengthIndicator;
    }

    public boolean hasCompression() {
        return this.compression;
    }

    public boolean hasEncryption() {
        return this.encryption;
    }

    public boolean hasGroup() {
        return this.group;
    }

    public boolean hasPreserveFile() {
        return this.preserveFile;
    }

    public boolean hasPreserveTag() {
        return this.preserveTag;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public boolean hasUnsynchronisation() {
        return this.unsynchronisation;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + (this.compression ? 1231 : 1237);
        n = 31 * n + Arrays.hashCode(this.data);
        n = 31 * n + this.dataLength;
        n = 31 * n + (this.dataLengthIndicator ? 1231 : 1237);
        n = 31 * n + (this.encryption ? 1231 : 1237);
        n = 31 * n + (this.group ? 1231 : 1237);
        n = 31 * n + (this.id == null ? 0 : this.id.hashCode());
        n = 31 * n + (this.preserveFile ? 1231 : 1237);
        n = 31 * n + (this.preserveTag ? 1231 : 1237);
        n = 31 * n + (this.readOnly ? 1231 : 1237);
        n = 31 * n + (this.unsynchronisation ? 1231 : 1237);
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        ID3v2Frame iD3v2Frame = (ID3v2Frame)object;
        if (this.compression != iD3v2Frame.compression) {
            return false;
        }
        if (!Arrays.equals(this.data, iD3v2Frame.data)) {
            return false;
        }
        if (this.dataLength != iD3v2Frame.dataLength) {
            return false;
        }
        if (this.dataLengthIndicator != iD3v2Frame.dataLengthIndicator) {
            return false;
        }
        if (this.encryption != iD3v2Frame.encryption) {
            return false;
        }
        if (this.group != iD3v2Frame.group) {
            return false;
        }
        if (this.id == null ? iD3v2Frame.id != null : !this.id.equals(iD3v2Frame.id)) {
            return false;
        }
        if (this.preserveFile != iD3v2Frame.preserveFile) {
            return false;
        }
        if (this.preserveTag != iD3v2Frame.preserveTag) {
            return false;
        }
        if (this.readOnly != iD3v2Frame.readOnly) {
            return false;
        }
        if (this.unsynchronisation != iD3v2Frame.unsynchronisation) {
            return false;
        }
        return true;
    }
}

