/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class ByteBufferUtils {
    public static String extractNullTerminatedString(ByteBuffer byteBuffer) {
        int n = byteBuffer.position();
        byte[] arrby = new byte[byteBuffer.remaining()];
        byteBuffer.get(arrby);
        String string = new String(arrby);
        int n2 = string.indexOf(0);
        string = string.substring(0, n2);
        byteBuffer.position(n + string.length() + 1);
        return string;
    }
}

