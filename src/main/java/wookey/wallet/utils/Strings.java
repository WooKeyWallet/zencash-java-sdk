
package wookey.wallet.utils;

import java.util.List;

public class Strings {
    private Strings() {
    }

    public static String toCsv(List<String> src) {
        return join(src, ", ");
    }

    public static String join(List<String> src, String delimiter) {
        return src == null ? null : String.join(delimiter, (CharSequence[]) src.toArray(new String[0]));
    }

    public static String capitaliseFirstLetter(String string) {
        return string != null && string.length() != 0 ? string.substring(0, 1).toUpperCase() + string.substring(1) : string;
    }

    public static String lowercaseFirstLetter(String string) {
        return string != null && string.length() != 0 ? string.substring(0, 1).toLowerCase() + string.substring(1) : string;
    }

    public static String zeros(int n) {
        return repeat('0', n);
    }

    public static String repeat(char value, int n) {
        return (new String(new char[n])).replace("\u0000", String.valueOf(value));
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
