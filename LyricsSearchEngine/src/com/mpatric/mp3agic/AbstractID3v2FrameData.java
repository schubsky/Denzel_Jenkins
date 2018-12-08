/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.InvalidDataException;

public abstract class AbstractID3v2FrameData {
    boolean unsynchronisation;

    public AbstractID3v2FrameData(boolean bl) {
        this.unsynchronisation = bl;
    }

    protected final void synchroniseAndUnpackFrameData(byte[] arrby) throws InvalidDataException {
        if (this.unsynchronisation && BufferTools.sizeSynchronisationWouldSubtract(arrby) > 0) {
            byte[] arrby2 = BufferTools.synchroniseBuffer(arrby);
            this.unpackFrameData(arrby2);
        } else {
            this.unpackFrameData(arrby);
        }
    }

    protected byte[] packAndUnsynchroniseFrameData() {
        byte[] arrby = this.packFrameData();
        if (this.unsynchronisation && BufferTools.sizeUnsynchronisationWouldAdd(arrby) > 0) {
            return BufferTools.unsynchroniseBuffer(arrby);
        }
        return arrby;
    }

    protected byte[] toBytes() {
        return this.packAndUnsynchroniseFrameData();
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + (this.unsynchronisation ? 1231 : 1237);
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        AbstractID3v2FrameData abstractID3v2FrameData = (AbstractID3v2FrameData)object;
        if (this.unsynchronisation != abstractID3v2FrameData.unsynchronisation) {
            return false;
        }
        return true;
    }

    protected abstract void unpackFrameData(byte[] var1) throws InvalidDataException;

    protected abstract byte[] packFrameData();

    protected abstract int getLength();
}

