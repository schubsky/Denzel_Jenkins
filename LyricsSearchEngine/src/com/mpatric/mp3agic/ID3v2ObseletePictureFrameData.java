/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.EncodedText;
import com.mpatric.mp3agic.ID3v2PictureFrameData;
import com.mpatric.mp3agic.InvalidDataException;
import java.io.UnsupportedEncodingException;

public class ID3v2ObseletePictureFrameData
extends ID3v2PictureFrameData {
    public ID3v2ObseletePictureFrameData(boolean bl) {
        super(bl);
    }

    public ID3v2ObseletePictureFrameData(boolean bl, String string, byte by, EncodedText encodedText, byte[] arrby) {
        super(bl, string, by, encodedText, arrby);
    }

    public ID3v2ObseletePictureFrameData(boolean bl, byte[] arrby) throws InvalidDataException {
        super(bl, arrby);
    }

    @Override
    protected void unpackFrameData(byte[] arrby) throws InvalidDataException {
        String string;
        try {
            string = BufferTools.byteBufferToString(arrby, 1, 3);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            string = "unknown";
        }
        this.mimeType = "image/" + string.toLowerCase();
        this.pictureType = arrby[4];
        int n = BufferTools.indexOfTerminatorForEncoding(arrby, 5, arrby[0]);
        if (n >= 0) {
            this.description = new EncodedText(arrby[0], BufferTools.copyBuffer(arrby, 5, n - 5));
            n += this.description.getTerminator().length;
        } else {
            this.description = new EncodedText(arrby[0], "");
            n = 1;
        }
        this.imageData = BufferTools.copyBuffer(arrby, n, arrby.length - n);
    }
}

