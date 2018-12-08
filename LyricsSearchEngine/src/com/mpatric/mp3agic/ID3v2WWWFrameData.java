/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2FrameData;
import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.InvalidDataException;
import java.io.UnsupportedEncodingException;

public class ID3v2WWWFrameData
extends AbstractID3v2FrameData {
    protected String url;

    public ID3v2WWWFrameData(boolean bl) {
        super(bl);
    }

    public ID3v2WWWFrameData(boolean bl, String string) {
        super(bl);
        this.url = string;
    }

    public ID3v2WWWFrameData(boolean bl, byte[] arrby) throws InvalidDataException {
        super(bl);
        this.synchroniseAndUnpackFrameData(arrby);
    }

    @Override
    protected void unpackFrameData(byte[] arrby) throws InvalidDataException {
        try {
            this.url = BufferTools.byteBufferToString(arrby, 0, arrby.length);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.url = "";
        }
    }

    @Override
    protected byte[] packFrameData() {
        byte[] arrby = new byte[this.getLength()];
        if (this.url != null && this.url.length() > 0) {
            try {
                BufferTools.stringIntoByteBuffer(this.url, 0, this.url.length(), arrby, 0);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        return arrby;
    }

    @Override
    protected int getLength() {
        int n = 0;
        if (this.url != null) {
            n = this.url.length();
        }
        return n;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String string) {
        this.url = string;
    }
}

