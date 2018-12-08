/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.ID3v2Frame;
import java.util.ArrayList;
import java.util.List;

public class ID3v2FrameSet {
    private String id;
    private ArrayList<ID3v2Frame> frames;

    public ID3v2FrameSet(String string) {
        this.id = string;
        this.frames = new ArrayList();
    }

    public String getId() {
        return this.id;
    }

    public void clear() {
        this.frames.clear();
    }

    public void addFrame(ID3v2Frame iD3v2Frame) {
        this.frames.add(iD3v2Frame);
    }

    public List<ID3v2Frame> getFrames() {
        return this.frames;
    }

    public String toString() {
        return this.id + ": " + this.frames.size();
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + (this.frames == null ? 0 : this.frames.hashCode());
        n = 31 * n + (this.id == null ? 0 : this.id.hashCode());
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
        ID3v2FrameSet iD3v2FrameSet = (ID3v2FrameSet)object;
        if (this.frames == null ? iD3v2FrameSet.frames != null : !this.frames.equals(iD3v2FrameSet.frames)) {
            return false;
        }
        if (this.id == null ? iD3v2FrameSet.id != null : !this.id.equals(iD3v2FrameSet.id)) {
            return false;
        }
        return true;
    }
}

