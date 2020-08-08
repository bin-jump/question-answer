package com.zhang.ddd.infrastructure.util;

import org.springframework.util.StringUtils;

public class NumberEncoder {

    private static char[] translationTable = buildTranslationTable();
    private static int maxLetters = 12;

    public static Long decode(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }

        final long base = translationTable.length;
        final int len = Math.min(s.length(), maxLetters);
        long l = 0L;
        for (int i = 0; i < len; ++i) {
            final char c = Character.toLowerCase(s.charAt(i));
            l *= base;
            if (c >= 'a' && c <= 'z') {
                l += 1 + (c - 'a');
            } else if (c >= '0' && c <= '9') {
                l += 27 + (c - '0');
            }
        }
        return l;
    }

    public static String encode(Long n) {
        if (n == null) {
            return null;
        }

        int i = 0;
        char[] nameCharacters = new char[maxLetters];
        final long base = translationTable.length;
        while (n != 0L) {
            final long ch = n % base;
            nameCharacters[maxLetters - ++i] = translationTable[(int) ch];
            n /= base;
        }
        return new String(nameCharacters, maxLetters - i, i);
    }

    private static char[] buildTranslationTable()
    {
        char[] table = new char[37];
        for (char c = 'a'; c <= 'z'; ++c) {
            table[1 + c - 'a'] = c;
        }
        for (int i = 0; i < 10; ++i) {
            table[i + 27] = (char) (i + '0');
        }
        return table;
    }
}
