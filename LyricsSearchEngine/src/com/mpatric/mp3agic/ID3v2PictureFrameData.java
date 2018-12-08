/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2FrameData;
import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.EncodedText;
import com.mpatric.mp3agic.InvalidDataException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ID3v2PictureFrameData
extends AbstractID3v2FrameData {
    protected String mimeType;
    protected byte pictureType;
    protected EncodedText description;
    protected byte[] imageData;

    public ID3v2PictureFrameData(boolean bl) {
        super(bl);
    }

    public ID3v2PictureFrameData(boolean bl, String string, byte by, EncodedText encodedText, byte[] arrby) {
        super(bl);
        this.mimeType = string;
        this.pictureType = by;
        this.description = encodedText;
        this.imageData = arrby;
    }

    public ID3v2PictureFrameData(boolean bl, byte[] arrby) throws InvalidDataException {
        super(bl);
        this.synchroniseAndUnpackFrameData(arrby);
    }

    @Override
    protected void unpackFrameData(byte[] arrby) throws InvalidDataException {
        int n = BufferTools.indexOfTerminator(arrby, 1, 1);
        if (n >= 0) {
            try {
                this.mimeType = BufferTools.byteBufferToString(arrby, 1, n - 1);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                this.mimeType = "image/unknown";
            }
        } else {
            this.mimeType = "image/unknown";
        }
        this.pictureType = arrby[n + 1];
        int n2 = BufferTools.indexOfTerminatorForEncoding(arrby, n += 2, arrby[0]);
        if (n2 >= 0) {
            this.description = new EncodedText(arrby[0], BufferTools.copyBuffer(arrby, n, n2 - n));
            n2 += this.description.getTerminator().length;
        } else {
            this.description = new EncodedText(arrby[0], "");
            n2 = n;
        }
        this.imageData = BufferTools.copyBuffer(arrby, n2, arrby.length - n2);
    }

    @Override
    protected byte[] packFrameData() {
        byte[] arrby = new byte[this.getLength()];
        arrby[0] = this.description != null ? this.description.getTextEncoding() : (byte)0;
        int n = 0;
        if (this.mimeType != null && this.mimeType.length() > 0) {
            n = this.mimeType.length();
            try {
                BufferTools.stringIntoByteBuffer(this.mimeType, 0, n, arrby, 1);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        int n2 = n + 1;
        arrby[n2++] = 0;
        arrby[n2++] = this.pictureType;
        if (this.description != null && this.description.toBytes().length > 0) {
            byte[] arrby2 = this.description.toBytes(true, true);
            BufferTools.copyIntoByteBuffer(arrby2, 0, arrby2.length, arrby, n2);
            n2 += arrby2.length;
        } else {
            arrby[n2++] = 0;
        }
        if (this.imageData != null && this.imageData.length > 0) {
            BufferTools.copyIntoByteBuffer(this.imageData, 0, this.imageData.length, arrby, n2);
        }
        return arrby;
    }

    @Override
    protected int getLength() {
        int n = 3;
        if (this.mimeType != null) {
            n += this.mimeType.length();
        }
        n = this.description != null ? (n += this.description.toBytes(true, true).length) : ++n;
        if (this.imageData != null) {
            n += this.imageData.length;
        }
        return n;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String string) {
        this.mimeType = string;
    }

    public byte getPictureType() {
        return this.pictureType;
    }

    public void setPictureType(byte by) {
        this.pictureType = by;
    }

    public EncodedText getDescription() {
        return this.description;
    }

    public void setDescription(EncodedText encodedText) {
        this.description = encodedText;
    }

    public byte[] getImageData() {
        return this.imageData;
    }

    public void setImageData(byte[] arrby) {
        this.imageData = arrby;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + (this.description == null ? 0 : this.description.hashCode());
        n = 31 * n + Arrays.hashCode(this.imageData);
        n = 31 * n + (this.mimeType == null ? 0 : this.mimeType.hashCode());
        n = 31 * n + this.pictureType;
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!super.equals(object)) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        ID3v2PictureFrameData iD3v2PictureFrameData = (ID3v2PictureFrameData)object;
        if (this.description == null ? iD3v2PictureFrameData.description != null : !this.description.equals(iD3v2PictureFrameData.description)) {
            return false;
        }
        if (!Arrays.equals(this.imageData, iD3v2PictureFrameData.imageData)) {
            return false;
        }
        if (this.mimeType == null ? iD3v2PictureFrameData.mimeType != null : !this.mimeType.equals(iD3v2PictureFrameData.mimeType)) {
            return false;
        }
        if (this.pictureType != iD3v2PictureFrameData.pictureType) {
            return false;
        }
        return true;
    }
}

