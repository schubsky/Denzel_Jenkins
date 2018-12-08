/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2FrameData;
import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.EncodedText;
import com.mpatric.mp3agic.InvalidDataException;
import java.io.UnsupportedEncodingException;

public class ID3v2CommentFrameData
extends AbstractID3v2FrameData {
    private static final String DEFAULT_LANGUAGE = "eng";
    private String language;
    private EncodedText description;
    private EncodedText comment;

    public ID3v2CommentFrameData(boolean bl) {
        super(bl);
    }

    public ID3v2CommentFrameData(boolean bl, String string, EncodedText encodedText, EncodedText encodedText2) {
        super(bl);
        if (encodedText != null && encodedText2 != null && encodedText.getTextEncoding() != encodedText2.getTextEncoding()) {
            throw new IllegalArgumentException("description and comment must have same text encoding");
        }
        this.language = string;
        this.description = encodedText;
        this.comment = encodedText2;
    }

    public ID3v2CommentFrameData(boolean bl, byte[] arrby) throws InvalidDataException {
        super(bl);
        this.synchroniseAndUnpackFrameData(arrby);
    }

    @Override
    protected void unpackFrameData(byte[] arrby) throws InvalidDataException {
        try {
            this.language = BufferTools.byteBufferToString(arrby, 1, 3);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.language = "";
        }
        int n = BufferTools.indexOfTerminatorForEncoding(arrby, 4, arrby[0]);
        if (n >= 4) {
            this.description = new EncodedText(arrby[0], BufferTools.copyBuffer(arrby, 4, n - 4));
            n += this.description.getTerminator().length;
        } else {
            this.description = new EncodedText(arrby[0], "");
            n = 4;
        }
        this.comment = new EncodedText(arrby[0], BufferTools.copyBuffer(arrby, n, arrby.length - n));
    }

    @Override
    protected byte[] packFrameData() {
        byte[] arrby;
        byte[] arrby2 = new byte[this.getLength()];
        arrby2[0] = this.comment != null ? this.comment.getTextEncoding() : (byte)0;
        String string = this.language == null ? DEFAULT_LANGUAGE : (this.language.length() > 3 ? this.language.substring(0, 3) : BufferTools.padStringRight(this.language, 3, '\u0000'));
        try {
            BufferTools.stringIntoByteBuffer(string, 0, 3, arrby2, 1);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        int n = 4;
        if (this.description != null) {
            arrby = this.description.toBytes(true, true);
            BufferTools.copyIntoByteBuffer(arrby, 0, arrby.length, arrby2, n);
            n += arrby.length;
        } else {
            byte[] arrby3;
            if (this.comment != null) {
                arrby3 = this.comment.getTerminator();
            } else {
                byte[] arrby4 = new byte[1];
                arrby3 = arrby4;
                arrby4[0] = 0;
            }
            arrby = arrby3;
            BufferTools.copyIntoByteBuffer(arrby, 0, arrby.length, arrby2, n);
            n += arrby.length;
        }
        if (this.comment != null) {
            arrby = this.comment.toBytes(true, false);
            BufferTools.copyIntoByteBuffer(arrby, 0, arrby.length, arrby2, n);
        }
        return arrby2;
    }

    @Override
    protected int getLength() {
        int n = 4;
        n = this.description != null ? (n += this.description.toBytes(true, true).length) : (n += this.comment != null ? this.comment.getTerminator().length : 1);
        if (this.comment != null) {
            n += this.comment.toBytes(true, false).length;
        }
        return n;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String string) {
        this.language = string;
    }

    public EncodedText getComment() {
        return this.comment;
    }

    public void setComment(EncodedText encodedText) {
        this.comment = encodedText;
    }

    public EncodedText getDescription() {
        return this.description;
    }

    public void setDescription(EncodedText encodedText) {
        this.description = encodedText;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + (this.comment == null ? 0 : this.comment.hashCode());
        n = 31 * n + (this.description == null ? 0 : this.description.hashCode());
        n = 31 * n + (this.language == null ? 0 : this.language.hashCode());
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
        ID3v2CommentFrameData iD3v2CommentFrameData = (ID3v2CommentFrameData)object;
        if (this.comment == null ? iD3v2CommentFrameData.comment != null : !this.comment.equals(iD3v2CommentFrameData.comment)) {
            return false;
        }
        if (this.description == null ? iD3v2CommentFrameData.description != null : !this.description.equals(iD3v2CommentFrameData.description)) {
            return false;
        }
        if (this.language == null ? iD3v2CommentFrameData.language != null : !this.language.equals(iD3v2CommentFrameData.language)) {
            return false;
        }
        return true;
    }
}

