/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2FrameData;
import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.EncodedText;
import com.mpatric.mp3agic.InvalidDataException;

public class ID3v2TextFrameData
extends AbstractID3v2FrameData {
    protected EncodedText text;

    public ID3v2TextFrameData(boolean bl) {
        super(bl);
    }

    public ID3v2TextFrameData(boolean bl, EncodedText encodedText) {
        super(bl);
        this.text = encodedText;
    }

    public ID3v2TextFrameData(boolean bl, byte[] arrby) throws InvalidDataException {
        super(bl);
        this.synchroniseAndUnpackFrameData(arrby);
    }

    @Override
    protected void unpackFrameData(byte[] arrby) throws InvalidDataException {
        this.text = new EncodedText(arrby[0], BufferTools.copyBuffer(arrby, 1, arrby.length - 1));
    }

    @Override
    protected byte[] packFrameData() {
        byte[] arrby = new byte[this.getLength()];
        if (this.text != null) {
            arrby[0] = this.text.getTextEncoding();
            byte[] arrby2 = this.text.toBytes(true, false);
            if (arrby2.length > 0) {
                BufferTools.copyIntoByteBuffer(arrby2, 0, arrby2.length, arrby, 1);
            }
        }
        return arrby;
    }

    @Override
    protected int getLength() {
        int n = 1;
        if (this.text != null) {
            n += this.text.toBytes(true, false).length;
        }
        return n;
    }

    public EncodedText getText() {
        return this.text;
    }

    public void setText(EncodedText encodedText) {
        this.text = encodedText;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + (this.text == null ? 0 : this.text.hashCode());
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
        ID3v2TextFrameData iD3v2TextFrameData = (ID3v2TextFrameData)object;
        if (this.text == null ? iD3v2TextFrameData.text != null : !this.text.equals(iD3v2TextFrameData.text)) {
            return false;
        }
        return true;
    }
}

