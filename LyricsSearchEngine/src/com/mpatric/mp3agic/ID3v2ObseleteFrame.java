/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.ID3v2Frame;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;

public class ID3v2ObseleteFrame
extends ID3v2Frame {
    private static final int HEADER_LENGTH = 6;
    private static final int ID_OFFSET = 0;
    private static final int ID_LENGTH = 3;
    protected static final int DATA_LENGTH_OFFSET = 3;

    public ID3v2ObseleteFrame(byte[] arrby, int n) throws InvalidDataException {
        super(arrby, n);
    }

    public ID3v2ObseleteFrame(String string, byte[] arrby) {
        super(string, arrby);
    }

    @Override
    protected int unpackHeader(byte[] arrby, int n) {
        this.id = BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, n + 0, 3);
        this.unpackDataLength(arrby, n);
        return n + 6;
    }

    @Override
    protected void unpackDataLength(byte[] arrby, int n) {
        this.dataLength = BufferTools.unpackInteger((byte)0, arrby[n + 3], arrby[n + 3 + 1], arrby[n + 3 + 2]);
    }

    @Override
    public void packFrame(byte[] arrby, int n) throws NotSupportedException {
        throw new NotSupportedException("Packing Obselete frames is not supported");
    }

    @Override
    public int getLength() {
        return this.dataLength + 6;
    }
}

