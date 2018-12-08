/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2FrameData;
import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.EncodedText;
import com.mpatric.mp3agic.InvalidDataException;
import java.io.UnsupportedEncodingException;

public class ID3v2UrlFrameData
extends AbstractID3v2FrameData {
    protected String url;
    protected EncodedText description;

    public ID3v2UrlFrameData(boolean bl) {
        super(bl);
    }

    public ID3v2UrlFrameData(boolean bl, EncodedText encodedText, String string) {
        super(bl);
        this.description = encodedText;
        this.url = string;
    }

    public ID3v2UrlFrameData(boolean bl, byte[] arrby) throws InvalidDataException {
        super(bl);
        this.synchroniseAndUnpackFrameData(arrby);
    }

    @Override
    protected void unpackFrameData(byte[] arrby) throws InvalidDataException {
        int n = BufferTools.indexOfTerminatorForEncoding(arrby, 1, arrby[0]);
        if (n >= 0) {
            this.description = new EncodedText(arrby[0], BufferTools.copyBuffer(arrby, 1, n - 1));
            n += this.description.getTerminator().length;
        } else {
            this.description = new EncodedText(arrby[0], "");
            n = 1;
        }
        try {
            this.url = BufferTools.byteBufferToString(arrby, n, arrby.length - n);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.url = "";
        }
    }

    @Override
    protected byte[] packFrameData() {
        byte[] arrby = new byte[this.getLength()];
        arrby[0] = this.description != null ? this.description.getTextEncoding() : (byte)0;
        int n = 1;
        if (this.description != null) {
            byte[] arrby2 = this.description.toBytes(true, true);
            BufferTools.copyIntoByteBuffer(arrby2, 0, arrby2.length, arrby, n);
            n += arrby2.length;
        } else {
            arrby[n++] = 0;
        }
        if (this.url != null && this.url.length() > 0) {
            try {
                BufferTools.stringIntoByteBuffer(this.url, 0, this.url.length(), arrby, n);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        return arrby;
    }

    @Override
    protected int getLength() {
        int n = 1;
        n = this.description != null ? (n += this.description.toBytes(true, true).length) : ++n;
        if (this.url != null) {
            n += this.url.length();
        }
        return n;
    }

    public EncodedText getDescription() {
        return this.description;
    }

    public void setDescription(EncodedText encodedText) {
        this.description = encodedText;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String string) {
        this.url = string;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + (this.description == null ? 0 : this.description.hashCode());
        n = 31 * n + (this.url == null ? 0 : this.url.hashCode());
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
        ID3v2UrlFrameData iD3v2UrlFrameData = (ID3v2UrlFrameData)object;
        if (this.description == null ? iD3v2UrlFrameData.description != null : !this.description.equals(iD3v2UrlFrameData.description)) {
            return false;
        }
        if (this.url == null ? iD3v2UrlFrameData.url != null : !this.url.equals(iD3v2UrlFrameData.url)) {
            return false;
        }
        return true;
    }
}

