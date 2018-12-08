/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2Tag;
import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NoSuchTagException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class ID3v23Tag
extends AbstractID3v2Tag {
    public static final String VERSION = "3.0";

    public ID3v23Tag() {
        this.version = VERSION;
    }

    public ID3v23Tag(byte[] arrby) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        super(arrby);
    }

    @Override
    protected void unpackFlags(byte[] arrby) {
        this.unsynchronisation = BufferTools.checkBit(arrby[5], 7);
        this.extendedHeader = BufferTools.checkBit(arrby[5], 6);
        this.experimental = BufferTools.checkBit(arrby[5], 5);
    }

    @Override
    protected void packFlags(byte[] arrby, int n) {
        arrby[n + 5] = BufferTools.setBit(arrby[n + 5], 7, this.unsynchronisation);
        arrby[n + 5] = BufferTools.setBit(arrby[n + 5], 6, this.extendedHeader);
        arrby[n + 5] = BufferTools.setBit(arrby[n + 5], 5, this.experimental);
    }
}

