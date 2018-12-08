/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Genres;
import com.mpatric.mp3agic.NoSuchTagException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ID3v1Tag
implements ID3v1 {
    public static final int TAG_LENGTH = 128;
    private static final String VERSION_0 = "0";
    private static final String VERSION_1 = "1";
    private static final String TAG = "TAG";
    private static final int TITLE_OFFSET = 3;
    private static final int TITLE_LENGTH = 30;
    private static final int ARTIST_OFFSET = 33;
    private static final int ARTIST_LENGTH = 30;
    private static final int ALBUM_OFFSET = 63;
    private static final int ALBUM_LENGTH = 30;
    private static final int YEAR_OFFSET = 93;
    private static final int YEAR_LENGTH = 4;
    private static final int COMMENT_OFFSET = 97;
    private static final int COMMENT_LENGTH_V1_0 = 30;
    private static final int COMMENT_LENGTH_V1_1 = 28;
    private static final int TRACK_MARKER_OFFSET = 125;
    private static final int TRACK_OFFSET = 126;
    private static final int GENRE_OFFSET = 127;
    private String track = null;
    private String artist = null;
    private String title = null;
    private String album = null;
    private String year = null;
    private int genre = -1;
    private String comment = null;

    public ID3v1Tag() {
    }

    public ID3v1Tag(byte[] arrby) throws NoSuchTagException {
        this.unpackTag(arrby);
    }

    private void unpackTag(byte[] arrby) throws NoSuchTagException {
        this.sanityCheckTag(arrby);
        this.title = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, 3, 30));
        this.artist = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, 33, 30));
        this.album = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, 63, 30));
        this.year = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, 93, 4));
        this.genre = arrby[127] & 255;
        if (this.genre == 255) {
            this.genre = -1;
        }
        if (arrby[125] != 0) {
            this.comment = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, 97, 30));
            this.track = null;
        } else {
            this.comment = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, 97, 28));
            byte by = arrby[126];
            this.track = by == 0 ? "" : Integer.toString(by);
        }
    }

    private void sanityCheckTag(byte[] arrby) throws NoSuchTagException {
        if (arrby.length != 128) {
            throw new NoSuchTagException("Buffer length wrong");
        }
        if (!TAG.equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(arrby, 0, TAG.length()))) {
            throw new NoSuchTagException();
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] arrby = new byte[128];
        this.packTag(arrby);
        return arrby;
    }

    public void toBytes(byte[] arrby) {
        this.packTag(arrby);
    }

    public void packTag(byte[] arrby) {
        Arrays.fill(arrby, (byte)0);
        try {
            BufferTools.stringIntoByteBuffer(TAG, 0, 3, arrby, 0);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        this.packField(arrby, this.title, 30, 3);
        this.packField(arrby, this.artist, 30, 33);
        this.packField(arrby, this.album, 30, 63);
        this.packField(arrby, this.year, 4, 93);
        arrby[127] = this.genre < 128 ? (byte)this.genre : (byte)(this.genre - 256);
        if (this.track == null) {
            this.packField(arrby, this.comment, 30, 97);
        } else {
            this.packField(arrby, this.comment, 28, 97);
            String string = this.numericsOnly(this.track);
            if (string.length() > 0) {
                int n = Integer.parseInt(string);
                arrby[126] = n < 128 ? (byte)n : (byte)(n - 256);
            }
        }
    }

    private void packField(byte[] arrby, String string, int n, int n2) {
        if (string != null) {
            try {
                BufferTools.stringIntoByteBuffer(string, 0, Math.min(string.length(), n), arrby, n2);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
    }

    private String numericsOnly(String string) {
        char c;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length() && (c = string.charAt(i)) >= '0' && c <= '9'; ++i) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    @Override
    public String getVersion() {
        if (this.track == null) {
            return VERSION_0;
        }
        return VERSION_1;
    }

    @Override
    public String getTrack() {
        return this.track;
    }

    @Override
    public void setTrack(String string) {
        this.track = string;
    }

    @Override
    public String getArtist() {
        return this.artist;
    }

    @Override
    public void setArtist(String string) {
        this.artist = string;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String string) {
        this.title = string;
    }

    @Override
    public String getAlbum() {
        return this.album;
    }

    @Override
    public void setAlbum(String string) {
        this.album = string;
    }

    @Override
    public String getYear() {
        return this.year;
    }

    @Override
    public void setYear(String string) {
        this.year = string;
    }

    @Override
    public int getGenre() {
        return this.genre;
    }

    @Override
    public void setGenre(int n) {
        this.genre = n;
    }

    @Override
    public String getGenreDescription() {
        try {
            return ID3v1Genres.GENRES[this.genre];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return "Unknown";
        }
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public void setComment(String string) {
        this.comment = string;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + (this.album == null ? 0 : this.album.hashCode());
        n = 31 * n + (this.artist == null ? 0 : this.artist.hashCode());
        n = 31 * n + (this.comment == null ? 0 : this.comment.hashCode());
        n = 31 * n + this.genre;
        n = 31 * n + (this.title == null ? 0 : this.title.hashCode());
        n = 31 * n + (this.track == null ? 0 : this.track.hashCode());
        n = 31 * n + (this.year == null ? 0 : this.year.hashCode());
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
        ID3v1Tag iD3v1Tag = (ID3v1Tag)object;
        if (this.album == null ? iD3v1Tag.album != null : !this.album.equals(iD3v1Tag.album)) {
            return false;
        }
        if (this.artist == null ? iD3v1Tag.artist != null : !this.artist.equals(iD3v1Tag.artist)) {
            return false;
        }
        if (this.comment == null ? iD3v1Tag.comment != null : !this.comment.equals(iD3v1Tag.comment)) {
            return false;
        }
        if (this.genre != iD3v1Tag.genre) {
            return false;
        }
        if (this.title == null ? iD3v1Tag.title != null : !this.title.equals(iD3v1Tag.title)) {
            return false;
        }
        if (this.track == null ? iD3v1Tag.track != null : !this.track.equals(iD3v1Tag.track)) {
            return false;
        }
        if (this.year == null ? iD3v1Tag.year != null : !this.year.equals(iD3v1Tag.year)) {
            return false;
        }
        return true;
    }
}

