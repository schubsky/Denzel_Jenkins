/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2Tag;
import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.ID3v22Tag;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.ID3v2FrameSet;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NoSuchTagException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.util.Map;

public class ID3v2TagFactory {
    public static AbstractID3v2Tag createTag(byte[] arrby) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        ID3v2TagFactory.sanityCheckTag(arrby);
        byte by = arrby[3];
        switch (by) {
            case 2: {
                return ID3v2TagFactory.createID3v22Tag(arrby);
            }
            case 3: {
                return new ID3v23Tag(arrby);
            }
            case 4: {
                return new ID3v24Tag(arrby);
            }
        }
        throw new UnsupportedTagException("Tag version not supported");
    }

    private static AbstractID3v2Tag createID3v22Tag(byte[] arrby) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        ID3v22Tag iD3v22Tag = new ID3v22Tag(arrby);
        if (iD3v22Tag.getFrameSets().isEmpty()) {
            iD3v22Tag = new ID3v22Tag(arrby, true);
        }
        return iD3v22Tag;
    }

    public static void sanityCheckTag(byte[] arrby) throws NoSuchTagException, UnsupportedTagException {
        if (arrby.length < 10) {
            throw new NoSuchTagException("Buffer too short");
        }
        if (!"ID3".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, 0, "ID3".length()))) {
            throw new NoSuchTagException();
        }
        byte by = arrby[3];
        if (by != 2 && by != 3 && by != 4) {
            byte by2 = arrby[4];
            throw new UnsupportedTagException("Unsupported version 2." + by + "." + by2);
        }
    }
}

