//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.util;

public class NumberUtil
{
    public static long parseLong(String str) {
        if (str == null) {
            return 0L;
        }
        str = str.trim();
        int len = str.length();
        if (len <= 0) {
            return 0L;
        }
        switch (str.charAt(len - 1)) {
            case '.':
            case 'L':
            case 'l': {
                --len;
                break;
            }
        }
        if (len >= 3 && str.charAt(0) == '0') {
            switch (str.charAt(1)) {
                case 'B':
                case 'b': {
                    return parseLongBin(str, 2, len - 2);
                }
                case 'O':
                case 'o': {
                    return parseLongOct(str, 2, len - 2);
                }
                case 'X':
                case 'x': {
                    return parseLongHex(str, 2, len - 2);
                }
            }
        }
        return parseLongDec(str, 0, len);
    }
    
    public static long parseLongBin(final String str) {
        if (str == null) {
            return 0L;
        }
        return parseLongBin(str, 0, str.length());
    }
    
    public static long parseLongBin(final String str, int pos, final int len) {
        long ret = 0L;
        for (int i = 0; i < len; ++i) {
            ret *= 2L;
            final char c = str.charAt(pos++);
            switch (c) {
                case '0': {
                    break;
                }
                case '1': {
                    ++ret;
                    break;
                }
                default: {
                    throw new NumberFormatException(str.substring(pos, len));
                }
            }
        }
        return ret;
    }
    
    public static long parseLongOct(final String str) {
        if (str == null) {
            return 0L;
        }
        return parseLongOct(str, 0, str.length());
    }
    
    public static long parseLongOct(final String str, int pos, final int len) {
        long ret = 0L;
        for (int i = 0; i < len; ++i) {
            ret *= 8L;
            final char c = str.charAt(pos++);
            switch (c) {
                case '0': {
                    break;
                }
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7': {
                    ret += c - '0';
                    break;
                }
                default: {
                    throw new NumberFormatException(str.substring(pos, len));
                }
            }
        }
        return ret;
    }
    
    public static long parseLongDec(final String str) {
        if (str == null) {
            return 0L;
        }
        return parseLongDec(str, 0, str.length());
    }
    
    public static long parseLongDec(final String str, int pos, final int len) {
        long ret = 0L;
        for (int i = 0; i < len; ++i) {
            ret *= 10L;
            final char c = str.charAt(pos++);
            switch (c) {
                case '0': {
                    break;
                }
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9': {
                    ret += c - '0';
                    break;
                }
                default: {
                    throw new NumberFormatException(str.substring(pos, len));
                }
            }
        }
        return ret;
    }
    
    public static long parseLongHex(final String str) {
        if (str == null) {
            return 0L;
        }
        return parseLongHex(str, 0, str.length());
    }
    
    public static long parseLongHex(final String str, int pos, final int len) {
        long ret = 0L;
        for (int i = 0; i < len; ++i) {
            ret *= 16L;
            final char c = str.charAt(pos++);
            switch (c) {
                case '0': {
                    break;
                }
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9': {
                    ret += c - '0';
                    break;
                }
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f': {
                    ret += c - 'a' + 10;
                    break;
                }
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F': {
                    ret += c - 'A' + 10;
                    break;
                }
                default: {
                    throw new NumberFormatException(str.substring(pos, len));
                }
            }
        }
        return ret;
    }
}
