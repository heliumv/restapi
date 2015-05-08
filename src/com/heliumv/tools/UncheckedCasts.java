package com.heliumv.tools;

public class UncheckedCasts {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
