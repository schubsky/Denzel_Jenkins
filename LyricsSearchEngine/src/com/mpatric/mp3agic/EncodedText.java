/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import com.mpatric.mp3agic.BufferTools;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

public class EncodedText {
    public static final byte TEXT_ENCODING_ISO_8859_1 = 0;
    public static final byte TEXT_ENCODING_UTF_16 = 1;
    public static final byte TEXT_ENCODING_UTF_16BE = 2;
    public static final byte TEXT_ENCODING_UTF_8 = 3;
    public static final String CHARSET_ISO_8859_1 = "ISO-8859-1";
    public static final String CHARSET_UTF_16 = "UTF-16LE";
    public static final String CHARSET_UTF_16BE = "UTF-16BE";
    public static final String CHARSET_UTF_8 = "UTF-8";
    private static final String[] characterSets = new String[]{"ISO-8859-1", "UTF-16LE", "UTF-16BE", "UTF-8"};
    private static final byte[] textEncodingFallback = new byte[]{0, 2, 1, 3};
    private static final byte[][] boms = new byte[][]{new byte[0], {-1, -2}, {-2, -1}, new byte[0]};
    private static final byte[][] terminators = new byte[][]{{0}, {0, 0}, {0, 0}, {0}};
    private byte[] value;
    private byte textEncoding;

    public EncodedText(byte by, byte[] arrby) {
        this.textEncoding = by == 1 && EncodedText.textEncodingForBytesFromBOM(arrby) == 2 ? (byte)2 : by;
        this.value = arrby;
        this.stripBomAndTerminator();
    }

    public EncodedText(String string) throws IllegalArgumentException {
        byte[] arrby = textEncodingFallback;
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            byte by;
            this.textEncoding = by = arrby[i];
            this.value = EncodedText.stringToBytes(string, EncodedText.characterSetForTextEncoding(by));
            if (this.value == null || this.toString() == null) continue;
            this.stripBomAndTerminator();
            return;
        }
        throw new IllegalArgumentException("Invalid string, could not find appropriate encoding");
    }

    public EncodedText(String string, byte by) throws IllegalArgumentException, CharacterCodingException {
        this(string);
        this.setTextEncoding(by, true);
    }

    public EncodedText(byte by, String string) {
        this.textEncoding = by;
        this.value = EncodedText.stringToBytes(string, EncodedText.characterSetForTextEncoding(by));
        this.stripBomAndTerminator();
    }

    public EncodedText(byte[] arrby) {
        this(EncodedText.textEncodingForBytesFromBOM(arrby), arrby);
    }

    private static byte textEncodingForBytesFromBOM(byte[] arrby) {
        if (arrby.length >= 2 && arrby[0] == -1 && arrby[1] == -2) {
            return 1;
        }
        if (arrby.length >= 2 && arrby[0] == -2 && arrby[1] == -1) {
            return 2;
        }
        if (arrby.length >= 3 && arrby[0] == -17 && arrby[1] == -69 && arrby[2] == -65) {
            return 3;
        }
        return 0;
    }

    private static String characterSetForTextEncoding(byte by) {
        try {
            return characterSets[by];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new IllegalArgumentException("Invalid text encoding " + by);
        }
    }

    private void stripBomAndTerminator() {
        int n;
        int n2 = 0;
        if (this.value.length >= 2 && (this.value[0] == -2 && this.value[1] == -1 || this.value[0] == -1 && this.value[1] == -2)) {
            n2 = 2;
        } else if (this.value.length >= 3 && this.value[0] == -17 && this.value[1] == -69 && this.value[2] == -65) {
            n2 = 3;
        }
        int n3 = 0;
        byte[] arrby = terminators[this.textEncoding];
        if (this.value.length - n2 >= arrby.length) {
            n = 1;
            for (int i = 0; i < arrby.length; ++i) {
                if (this.value[this.value.length - arrby.length + i] == arrby[i]) continue;
                n = 0;
                break;
            }
            if (n != 0) {
                n3 = arrby.length;
            }
        }
        if (n2 + n3 > 0) {
            n = this.value.length - n2 - n3;
            byte[] arrby2 = new byte[n];
            if (n > 0) {
                System.arraycopy(this.value, n2, arrby2, 0, arrby2.length);
            }
            this.value = arrby2;
        }
    }

    public byte getTextEncoding() {
        return this.textEncoding;
    }

    public void setTextEncoding(byte by) throws CharacterCodingException {
        this.setTextEncoding(by, true);
    }

    public void setTextEncoding(byte by, boolean bl) throws CharacterCodingException {
        if (this.textEncoding != by) {
            CharBuffer charBuffer = EncodedText.bytesToCharBuffer(this.value, EncodedText.characterSetForTextEncoding(this.textEncoding));
            byte[] arrby = EncodedText.charBufferToBytes(charBuffer, EncodedText.characterSetForTextEncoding(by));
            this.textEncoding = by;
            this.value = arrby;
        }
    }

    public byte[] getTerminator() {
        return terminators[this.textEncoding];
    }

    public byte[] toBytes() {
        return this.toBytes(false, false);
    }

    public byte[] toBytes(boolean bl) {
        return this.toBytes(bl, false);
    }

    public byte[] toBytes(boolean bl, boolean bl2) {
        byte[] arrby;
        EncodedText.characterSetForTextEncoding(this.textEncoding);
        int n = this.value.length + (bl ? boms[this.textEncoding].length : 0) + (bl2 ? this.getTerminator().length : 0);
        if (n == this.value.length) {
            return this.value;
        }
        byte[] arrby2 = new byte[n];
        int n2 = 0;
        if (bl && (arrby = boms[this.textEncoding]).length > 0) {
            System.arraycopy(boms[this.textEncoding], 0, arrby2, n2, boms[this.textEncoding].length);
            n2 += boms[this.textEncoding].length;
        }
        if (this.value.length > 0) {
            System.arraycopy(this.value, 0, arrby2, n2, this.value.length);
            n2 += this.value.length;
        }
        if (bl2 && (arrby = this.getTerminator()).length > 0) {
            System.arraycopy(arrby, 0, arrby2, n2, arrby.length);
        }
        return arrby2;
    }

    public String toString() {
        try {
            return EncodedText.bytesToString(this.value, EncodedText.characterSetForTextEncoding(this.textEncoding));
        }
        catch (CharacterCodingException characterCodingException) {
            return null;
        }
    }

    public String getCharacterSet() {
        return EncodedText.characterSetForTextEncoding(this.textEncoding);
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.textEncoding;
        n = 31 * n + Arrays.hashCode(this.value);
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
        EncodedText encodedText = (EncodedText)object;
        if (this.textEncoding != encodedText.textEncoding) {
            return false;
        }
        if (!Arrays.equals(this.value, encodedText.value)) {
            return false;
        }
        return true;
    }

    private static String bytesToString(byte[] arrby, String string) throws CharacterCodingException {
        CharBuffer charBuffer = EncodedText.bytesToCharBuffer(arrby, string);
        String string2 = charBuffer.toString();
        int n = string2.indexOf(0);
        if (n == -1) {
            return string2;
        }
        return string2.substring(0, n);
    }

    protected static CharBuffer bytesToCharBuffer(byte[] arrby, String string) throws CharacterCodingException {
        Charset charset = Charset.forName(string);
        CharsetDecoder charsetDecoder = charset.newDecoder();
        return charsetDecoder.decode(ByteBuffer.wrap(arrby));
    }

    private static byte[] stringToBytes(String string, String string2) {
        try {
            return EncodedText.charBufferToBytes(CharBuffer.wrap(string), string2);
        }
        catch (CharacterCodingException characterCodingException) {
            return null;
        }
    }

    protected static byte[] charBufferToBytes(CharBuffer charBuffer, String string) throws CharacterCodingException {
        Charset charset = Charset.forName(string);
        CharsetEncoder charsetEncoder = charset.newEncoder();
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);
        return BufferTools.copyBuffer(byteBuffer.array(), 0, byteBuffer.limit());
    }
}

