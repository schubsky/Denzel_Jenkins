/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2FrameData;
import com.mpatric.mp3agic.ByteBufferUtils;
import com.mpatric.mp3agic.ID3v2Frame;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ID3v2ChapterFrameData
extends AbstractID3v2FrameData {
    protected String id;
    protected int startTime;
    protected int endTime;
    protected int startOffset;
    protected int endOffset;
    protected ArrayList<ID3v2Frame> subframes = new ArrayList();

    public ID3v2ChapterFrameData(boolean bl) {
        super(bl);
    }

    public ID3v2ChapterFrameData(boolean bl, String string, int n, int n2, int n3, int n4) {
        super(bl);
        this.id = string;
        this.startTime = n;
        this.endTime = n2;
        this.startOffset = n3;
        this.endOffset = n4;
    }

    public ID3v2ChapterFrameData(boolean bl, byte[] arrby) throws InvalidDataException {
        super(bl);
        this.synchroniseAndUnpackFrameData(arrby);
    }

    @Override
    protected void unpackFrameData(byte[] arrby) throws InvalidDataException {
        ID3v2Frame iD3v2Frame;
        ByteBuffer byteBuffer = ByteBuffer.wrap(arrby);
        this.id = ByteBufferUtils.extractNullTerminatedString(byteBuffer);
        byteBuffer.position(this.id.length() + 1);
        this.startTime = byteBuffer.getInt();
        this.endTime = byteBuffer.getInt();
        this.startOffset = byteBuffer.getInt();
        this.endOffset = byteBuffer.getInt();
        for (int i = byteBuffer.position(); i < arrby.length; i += iD3v2Frame.getLength()) {
            iD3v2Frame = new ID3v2Frame(arrby, i);
            this.subframes.add(iD3v2Frame);
        }
    }

    public void addSubframe(String string, AbstractID3v2FrameData abstractID3v2FrameData) {
        this.subframes.add(new ID3v2Frame(string, abstractID3v2FrameData.toBytes()));
    }

    @Override
    protected byte[] packFrameData() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.getLength());
        byteBuffer.put(this.id.getBytes());
        byteBuffer.put((byte)0);
        byteBuffer.putInt(this.startTime);
        byteBuffer.putInt(this.endTime);
        byteBuffer.putInt(this.startOffset);
        byteBuffer.putInt(this.endOffset);
        for (ID3v2Frame iD3v2Frame : this.subframes) {
            try {
                byteBuffer.put(iD3v2Frame.toBytes());
            }
            catch (NotSupportedException notSupportedException) {
                notSupportedException.printStackTrace();
            }
        }
        return byteBuffer.array();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String string) {
        this.id = string;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int n) {
        this.startTime = n;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setEndTime(int n) {
        this.endTime = n;
    }

    public int getStartOffset() {
        return this.startOffset;
    }

    public void setStartOffset(int n) {
        this.startOffset = n;
    }

    public int getEndOffset() {
        return this.endOffset;
    }

    public void setEndOffset(int n) {
        this.endOffset = n;
    }

    public ArrayList<ID3v2Frame> getSubframes() {
        return this.subframes;
    }

    public void setSubframes(ArrayList<ID3v2Frame> arrayList) {
        this.subframes = arrayList;
    }

    @Override
    protected int getLength() {
        int n = 1;
        n += 16;
        if (this.id != null) {
            n += this.id.length();
        }
        if (this.subframes != null) {
            for (ID3v2Frame iD3v2Frame : this.subframes) {
                n += iD3v2Frame.getLength();
            }
        }
        return n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID3v2ChapterFrameData [id=");
        stringBuilder.append(this.id);
        stringBuilder.append(", startTime=");
        stringBuilder.append(this.startTime);
        stringBuilder.append(", endTime=");
        stringBuilder.append(this.endTime);
        stringBuilder.append(", startOffset=");
        stringBuilder.append(this.startOffset);
        stringBuilder.append(", endOffset=");
        stringBuilder.append(this.endOffset);
        stringBuilder.append(", subframes=");
        stringBuilder.append(this.subframes);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = 31 * n + this.endOffset;
        n = 31 * n + this.endTime;
        n = 31 * n + (this.id == null ? 0 : this.id.hashCode());
        n = 31 * n + this.startOffset;
        n = 31 * n + this.startTime;
        n = 31 * n + (this.subframes == null ? 0 : this.subframes.hashCode());
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
        ID3v2ChapterFrameData iD3v2ChapterFrameData = (ID3v2ChapterFrameData)object;
        if (this.endOffset != iD3v2ChapterFrameData.endOffset) {
            return false;
        }
        if (this.endTime != iD3v2ChapterFrameData.endTime) {
            return false;
        }
        if (this.id == null ? iD3v2ChapterFrameData.id != null : !this.id.equals(iD3v2ChapterFrameData.id)) {
            return false;
        }
        if (this.startOffset != iD3v2ChapterFrameData.startOffset) {
            return false;
        }
        if (this.startTime != iD3v2ChapterFrameData.startTime) {
            return false;
        }
        if (this.subframes == null ? iD3v2ChapterFrameData.subframes != null : !this.subframes.equals(iD3v2ChapterFrameData.subframes)) {
            return false;
        }
        return true;
    }
}

