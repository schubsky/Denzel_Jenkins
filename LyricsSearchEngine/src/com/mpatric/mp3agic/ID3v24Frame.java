/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.ID3v2Frame;
import com.mpatric.mp3agic.InvalidDataException;

public class ID3v24Frame
extends ID3v2Frame {
    public ID3v24Frame(byte[] arrby, int n) throws InvalidDataException {
        super(arrby, n);
    }

    public ID3v24Frame(String string, byte[] arrby) {
        super(string, arrby);
    }

    @Override
    protected void unpackDataLength(byte[] arrby, int n) {
        this.dataLength = BufferTools.unpackSynchsafeInteger(arrby[n + 4], arrby[n + 4 + 1], arrby[n + 4 + 2], arrby[n + 4 + 3]);
    }

    @Override
    protected byte[] packDataLength() {
        return BufferTools.packSynchsafeInteger(this.dataLength);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ID3v24Frame)) {
            return false;
        }
        return super.equals(object);
    }
}

