/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2FrameData;
import com.mpatric.mp3agic.ByteBufferUtils;
import com.mpatric.mp3agic.ID3v2Frame;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class ID3v2ChapterTOCFrameData
extends AbstractID3v2FrameData {
    protected boolean isRoot;
    protected boolean isOrdered;
    protected String id;
    protected String[] children;
    protected ArrayList<ID3v2Frame> subframes = new ArrayList();

    public ID3v2ChapterTOCFrameData(boolean bl) {
        super(bl);
    }

    public ID3v2ChapterTOCFrameData(boolean bl, boolean bl2, boolean bl3, String string, String[] arrstring) {
        super(bl);
        this.isRoot = bl2;
        this.isOrdered = bl3;
        this.id = string;
        this.children = arrstring;
    }

    public ID3v2ChapterTOCFrameData(boolean bl, byte[] arrby) throws InvalidDataException {
        super(bl);
        this.synchroniseAndUnpackFrameData(arrby);
    }

    @Override
    protected void unpackFrameData(byte[] arrby) throws InvalidDataException {
        ID3v2Frame iD3v2Frame;
        int n;
        ByteBuffer byteBuffer = ByteBuffer.wrap(arrby);
        this.id = ByteBufferUtils.extractNullTerminatedString(byteBuffer);
        byte by = byteBuffer.get();
        if ((by & 1) == 1) {
            this.isRoot = true;
        }
        if ((by & 2) == 2) {
            this.isOrdered = true;
        }
        int n2 = byteBuffer.get();
        this.children = new String[n2];
        for (n = 0; n < n2; ++n) {
            this.children[n] = ByteBufferUtils.extractNullTerminatedString(byteBuffer);
        }
        for (n = byteBuffer.position(); n < arrby.length; n += iD3v2Frame.getLength()) {
            iD3v2Frame = new ID3v2Frame(arrby, n);
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
        byteBuffer.put(this.getFlags());
        byteBuffer.put((byte)this.children.length);
        for (String string : this.children) {
            byteBuffer.put(string.getBytes());
            byteBuffer.put((byte)0);
        }
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

    private byte getFlags() {
        byte by = 0;
        if (this.isRoot) {
            by = (byte)(by | true ? 1 : 0);
        }
        if (this.isOrdered) {
            by = (byte)(by | 2);
        }
        return by;
    }

    public boolean isRoot() {
        return this.isRoot;
    }

    public void setRoot(boolean bl) {
        this.isRoot = bl;
    }

    public boolean isOrdered() {
        return this.isOrdered;
    }

    public void setOrdered(boolean bl) {
        this.isOrdered = bl;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String string) {
        this.id = string;
    }

    public String[] getChildren() {
        return this.children;
    }

    public void setChildren(String[] arrstring) {
        this.children = arrstring;
    }

    @Deprecated
    public String[] getChilds() {
        return this.children;
    }

    @Deprecated
    public void setChilds(String[] arrstring) {
        this.children = arrstring;
    }

    public ArrayList<ID3v2Frame> getSubframes() {
        return this.subframes;
    }

    public void setSubframes(ArrayList<ID3v2Frame> arrayList) {
        this.subframes = arrayList;
    }

    @Override
    protected int getLength() {
        int n = 3;
        if (this.id != null) {
            n += this.id.length();
        }
        if (this.children != null) {
            n += this.children.length;
            for (String string : this.children) {
                n += string.length();
            }
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
        stringBuilder.append("ID3v2ChapterTOCFrameData [isRoot=");
        stringBuilder.append(this.isRoot);
        stringBuilder.append(", isOrdered=");
        stringBuilder.append(this.isOrdered);
        stringBuilder.append(", id=");
        stringBuilder.append(this.id);
        stringBuilder.append(", children=");
        stringBuilder.append(Arrays.toString(this.children));
        stringBuilder.append(", subframes=");
        stringBuilder.append(this.subframes);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + Arrays.hashCode(this.children);
        n = 31 * n + (this.id == null ? 0 : this.id.hashCode());
        n = 31 * n + (this.isOrdered ? 1231 : 1237);
        n = 31 * n + (this.isRoot ? 1231 : 1237);
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
        ID3v2ChapterTOCFrameData iD3v2ChapterTOCFrameData = (ID3v2ChapterTOCFrameData)object;
        if (!Arrays.equals(this.children, iD3v2ChapterTOCFrameData.children)) {
            return false;
        }
        if (this.id == null ? iD3v2ChapterTOCFrameData.id != null : !this.id.equals(iD3v2ChapterTOCFrameData.id)) {
            return false;
        }
        if (this.isOrdered != iD3v2ChapterTOCFrameData.isOrdered) {
            return false;
        }
        if (this.isRoot != iD3v2ChapterTOCFrameData.isRoot) {
            return false;
        }
        if (this.subframes == null ? iD3v2ChapterTOCFrameData.subframes != null : !this.subframes.equals(iD3v2ChapterTOCFrameData.subframes)) {
            return false;
        }
        return true;
    }
}

