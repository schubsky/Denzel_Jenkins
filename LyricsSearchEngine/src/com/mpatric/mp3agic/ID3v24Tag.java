/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.AbstractID3v2Tag;
import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.EncodedText;
import com.mpatric.mp3agic.ID3v24Frame;
import com.mpatric.mp3agic.ID3v2Frame;
import com.mpatric.mp3agic.ID3v2FrameSet;
import com.mpatric.mp3agic.ID3v2TextFrameData;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NoSuchTagException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.util.Map;

public class ID3v24Tag
extends AbstractID3v2Tag {
    public static final String VERSION = "4.0";
    public static final String ID_RECTIME = "TDRC";

    public ID3v24Tag() {
        this.version = VERSION;
    }

    public ID3v24Tag(byte[] arrby) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        super(arrby);
    }

    @Override
    protected void unpackFlags(byte[] arrby) {
        this.unsynchronisation = BufferTools.checkBit(arrby[5], 7);
        this.extendedHeader = BufferTools.checkBit(arrby[5], 6);
        this.experimental = BufferTools.checkBit(arrby[5], 5);
        this.footer = BufferTools.checkBit(arrby[5], 4);
    }

    @Override
    protected void packFlags(byte[] arrby, int n) {
        arrby[n + 5] = BufferTools.setBit(arrby[n + 5], 7, this.unsynchronisation);
        arrby[n + 5] = BufferTools.setBit(arrby[n + 5], 6, this.extendedHeader);
        arrby[n + 5] = BufferTools.setBit(arrby[n + 5], 5, this.experimental);
        arrby[n + 5] = BufferTools.setBit(arrby[n + 5], 4, this.footer);
    }

    @Override
    protected boolean useFrameUnsynchronisation() {
        return this.unsynchronisation;
    }

    @Override
    protected ID3v2Frame createFrame(byte[] arrby, int n) throws InvalidDataException {
        return new ID3v24Frame(arrby, n);
    }

    @Override
    protected ID3v2Frame createFrame(String string, byte[] arrby) {
        return new ID3v24Frame(string, arrby);
    }

    @Override
    public void setGenreDescription(String string) {
        ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
        ID3v2FrameSet iD3v2FrameSet = this.getFrameSets().get("TCON");
        if (iD3v2FrameSet == null) {
            iD3v2FrameSet = new ID3v2FrameSet("TCON");
            this.getFrameSets().put("TCON", iD3v2FrameSet);
        }
        iD3v2FrameSet.clear();
        iD3v2FrameSet.addFrame(this.createFrame("TCON", iD3v2TextFrameData.toBytes()));
    }

    public String getRecordingTime() {
        ID3v2TextFrameData iD3v2TextFrameData = this.extractTextFrameData(ID_RECTIME);
        if (iD3v2TextFrameData != null && iD3v2TextFrameData.getText() != null) {
            return iD3v2TextFrameData.getText().toString();
        }
        return null;
    }

    public void setRecordingTime(String string) {
        if (string != null && string.length() > 0) {
            this.invalidateDataLength();
            ID3v2TextFrameData iD3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(string));
            this.addFrame(this.createFrame(ID_RECTIME, iD3v2TextFrameData.toBytes()), true);
        }
    }
}

