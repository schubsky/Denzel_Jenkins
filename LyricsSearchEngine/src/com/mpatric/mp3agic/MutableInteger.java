/*
 * Decompiled with CFR 0_132.
 */
package com.mpatric.mp3agic;

public class MutableInteger {
    private int value;

    public MutableInteger(int n) {
        this.value = n;
    }

    public void increment() {
        ++this.value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int n) {
        this.value = n;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.value;
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
        MutableInteger mutableInteger = (MutableInteger)object;
        if (this.value != mutableInteger.value) {
            return false;
        }
        return true;
    }
}

