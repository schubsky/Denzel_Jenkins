/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import java.io.UnsupportedEncodingException;

public class BufferTools {
    protected static final String defaultCharsetName = "ISO-8859-1";

    public static String byteBufferToStringIgnoringEncodingIssues(byte[] arrby, int n, int n2) {
        try {
            return BufferTools.byteBufferToString(arrby, n, n2, defaultCharsetName);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    public static String byteBufferToString(byte[] arrby, int n, int n2) throws UnsupportedEncodingException {
        return BufferTools.byteBufferToString(arrby, n, n2, defaultCharsetName);
    }

    public static String byteBufferToString(byte[] arrby, int n, int n2, String string) throws UnsupportedEncodingException {
        if (n2 < 1) {
            return "";
        }
        return new String(arrby, n, n2, string);
    }

    public static byte[] stringToByteBufferIgnoringEncodingIssues(String string, int n, int n2) {
        try {
            return BufferTools.stringToByteBuffer(string, n, n2);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    public static byte[] stringToByteBuffer(String string, int n, int n2) throws UnsupportedEncodingException {
        return BufferTools.stringToByteBuffer(string, n, n2, defaultCharsetName);
    }

    public static byte[] stringToByteBuffer(String string, int n, int n2, String string2) throws UnsupportedEncodingException {
        String string3 = string.substring(n, n + n2);
        byte[] arrby = string3.getBytes(string2);
        return arrby;
    }

    public static void stringIntoByteBuffer(String string, int n, int n2, byte[] arrby, int n3) throws UnsupportedEncodingException {
        BufferTools.stringIntoByteBuffer(string, n, n2, arrby, n3, defaultCharsetName);
    }

    public static void stringIntoByteBuffer(String string, int n, int n2, byte[] arrby, int n3, String string2) throws UnsupportedEncodingException {
        String string3 = string.substring(n, n + n2);
        byte[] arrby2 = string3.getBytes(string2);
        if (arrby2.length > 0) {
            System.arraycopy(arrby2, 0, arrby, n3, arrby2.length);
        }
    }

    public static String trimStringRight(String string) {
        int n;
        char c;
        for (n = string.length() - 1; n >= 0 && (c = string.charAt(n)) <= ' '; --n) {
        }
        if (n == string.length() - 1) {
            return string;
        }
        if (n < 0) {
            return "";
        }
        return string.substring(0, n + 1);
    }

    public static String padStringRight(String string, int n, char c) {
        if (string.length() >= n) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length() < n) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static boolean checkBit(byte by, int n) {
        return (by & 1 << n) != 0;
    }

    public static byte setBit(byte by, int n, boolean bl) {
        byte by2 = bl ? (byte)(by | 1 << n) : (byte)(by & ~ (1 << n));
        return by2;
    }

    public static int shiftByte(byte by, int n) {
        int n2 = by & 255;
        if (n < 0) {
            return n2 << - n;
        }
        if (n > 0) {
            return n2 >> n;
        }
        return n2;
    }

    public static int unpackInteger(byte by, byte by2, byte by3, byte by4) {
        int n = by4 & 255;
        n += BufferTools.shiftByte(by3, -8);
        n += BufferTools.shiftByte(by2, -16);
        return n += BufferTools.shiftByte(by, -24);
    }

    public static byte[] packInteger(int n) {
        byte[] arrby = new byte[4];
        arrby[3] = (byte)(n & 255);
        arrby[2] = (byte)(n >> 8 & 255);
        arrby[1] = (byte)(n >> 16 & 255);
        arrby[0] = (byte)(n >> 24 & 255);
        return arrby;
    }

    public static int unpackSynchsafeInteger(byte by, byte by2, byte by3, byte by4) {
        int n = by4 & 127;
        n += BufferTools.shiftByte((byte)(by3 & 127), -7);
        n += BufferTools.shiftByte((byte)(by2 & 127), -14);
        return n += BufferTools.shiftByte((byte)(by & 127), -21);
    }

    public static byte[] packSynchsafeInteger(int n) {
        byte[] arrby = new byte[4];
        BufferTools.packSynchsafeInteger(n, arrby, 0);
        return arrby;
    }

    public static void packSynchsafeInteger(int n, byte[] arrby, int n2) {
        arrby[n2 + 3] = (byte)(n & 127);
        arrby[n2 + 2] = (byte)(n >> 7 & 127);
        arrby[n2 + 1] = (byte)(n >> 14 & 127);
        arrby[n2 + 0] = (byte)(n >> 21 & 127);
    }

    public static byte[] copyBuffer(byte[] arrby, int n, int n2) {
        byte[] arrby2 = new byte[n2];
        if (n2 > 0) {
            System.arraycopy(arrby, n, arrby2, 0, n2);
        }
        return arrby2;
    }

    public static void copyIntoByteBuffer(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        if (n2 > 0) {
            System.arraycopy(arrby, n, arrby2, n3, n2);
        }
    }

    public static int sizeUnsynchronisationWouldAdd(byte[] arrby) {
        int n = 0;
        for (int i = 0; i < arrby.length - 1; ++i) {
            if (arrby[i] != -1 || (arrby[i + 1] & -32) != -32 && arrby[i + 1] != 0) continue;
            ++n;
        }
        if (arrby.length > 0 && arrby[arrby.length - 1] == -1) {
            ++n;
        }
        return n;
    }

    public static byte[] unsynchroniseBuffer(byte[] arrby) {
        int n = BufferTools.sizeUnsynchronisationWouldAdd(arrby);
        if (n == 0) {
            return arrby;
        }
        byte[] arrby2 = new byte[arrby.length + n];
        int n2 = 0;
        for (int i = 0; i < arrby.length - 1; ++i) {
            arrby2[n2++] = arrby[i];
            if (arrby[i] != -1 || (arrby[i + 1] & -32) != -32 && arrby[i + 1] != 0) continue;
            arrby2[n2++] = 0;
        }
        arrby2[n2++] = arrby[arrby.length - 1];
        if (arrby[arrby.length - 1] == -1) {
            arrby2[n2++] = 0;
        }
        return arrby2;
    }

    public static int sizeSynchronisationWouldSubtract(byte[] arrby) {
        int n = 0;
        for (int i = 0; i < arrby.length - 2; ++i) {
            if (arrby[i] != -1 || arrby[i + 1] != 0 || (arrby[i + 2] & -32) != -32 && arrby[i + 2] != 0) continue;
            ++n;
        }
        if (arrby.length > 1 && arrby[arrby.length - 2] == -1 && arrby[arrby.length - 1] == 0) {
            ++n;
        }
        return n;
    }

    public static byte[] synchroniseBuffer(byte[] arrby) {
        int n = BufferTools.sizeSynchronisationWouldSubtract(arrby);
        if (n == 0) {
            return arrby;
        }
        byte[] arrby2 = new byte[arrby.length - n];
        int n2 = 0;
        for (int i = 0; i < arrby2.length - 1; ++i) {
            arrby2[i] = arrby[n2];
            if (arrby[n2] == -1 && arrby[n2 + 1] == 0 && ((arrby[n2 + 2] & -32) == -32 || arrby[n2 + 2] == 0)) {
                ++n2;
            }
            ++n2;
        }
        arrby2[arrby2.length - 1] = arrby[n2];
        return arrby2;
    }

    public static String substitute(String string, String string2, String string3) {
        if (string2.length() < 1 || !string.contains(string2)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        int n2 = 0;
        while ((n2 = string.indexOf(string2, n2)) >= 0) {
            if (n2 > n) {
                stringBuilder.append(string.substring(n, n2));
            }
            if (string3 != null) {
                stringBuilder.append(string3);
            }
            n = n2 + string2.length();
            ++n2;
        }
        if (n < string.length()) {
            stringBuilder.append(string.substring(n));
        }
        return stringBuilder.toString();
    }

    public static String asciiOnly(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c < ' ' || c > '~') {
                stringBuilder.append('?');
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static int indexOfTerminator(byte[] arrby) {
        return BufferTools.indexOfTerminator(arrby, 0);
    }

    public static int indexOfTerminator(byte[] arrby, int n) {
        return BufferTools.indexOfTerminator(arrby, 0, 1);
    }

    public static int indexOfTerminator(byte[] arrby, int n, int n2) {
        int n3 = -1;
        for (int i = n; i <= arrby.length - n2; ++i) {
            int n4;
            if ((i - n) % n2 != 0) continue;
            for (n4 = 0; n4 < n2 && arrby[i + n4] == 0; ++n4) {
            }
            if (n4 != n2) continue;
            n3 = i;
            break;
        }
        return n3;
    }

    public static int indexOfTerminatorForEncoding(byte[] arrby, int n, int n2) {
        int n3 = n2 == 1 || n2 == 2 ? 2 : 1;
        return BufferTools.indexOfTerminator(arrby, n, n3);
    }
}

