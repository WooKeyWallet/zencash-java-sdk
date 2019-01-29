package wookey.wallet.crypto;

public class Hex {

    /**
     * Array of lowercase characters used to create the output of hexadecimal characters
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * An array of uppercase characters used to create the output of hexadecimal characters
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    /**
     * Converting strings to hexadecimal strings
     *
     * @param str ASCII string to be converted
     * @return String Spaces are separated between each Byte, such as: [61 6C 6B]
     */
    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * Hexadecimal Conversion String
     *
     * @param hexStr Byte string (there is no separator between Bytes such as: [616C6B])
     * @return String The corresponding string
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * Converting bytes to hexadecimal strings
     *
     * @param b byte array
     * @return String Spacing between each Byte value
     */
    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
//			sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * Converting bytes strings to Byte values
     *
     * @param src Byte string, with no separator between each Byte
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = Byte.decode("0x" + src.substring(i * 2, m) + src.substring(m, n));
        }
        return ret;
    }

    /**
     * String str covert to unicode String
     *
     * @param strText Full-angle string
     * @return String There is no separator between each Unicode
     * @throws Exception
     */
    public static String strToUnicode(String strText) throws Exception {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128) {
                str.append("\\u" + strHex);
            }else {
                // Low position in front of complement00
                str.append("\\u00" + strHex);
            }
        }
        return str.toString();
    }

    /**
     * unicode String covert to String str
     *
     * @param hex Hexadecimal value string (a Unicode of 2 byte)
     * @return String Full-angle string
     */
    public static String unicodeToString(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // High positions need to be replaced by 00
            String s1 = s.substring(2, 4) + "00";
            // Low direct turn
            String s2 = s.substring(4);
            // Convert the hexadecimal string to int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // Convert int to character
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }


    /**
     * Converting byte arrays to hexadecimal character arrays
     *
     * @param data byte[]
     * @return hexadecimal char[]
     */
    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    /**
     * Converting byte arrays to hexadecimal character arrays
     *
     * @param data        byte[]
     * @param toLowerCase <code>true</code> Convert to lowercase format ， <code>false</code>
     * @return hexadecimal char[]
     */
    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * Converting byte arrays to hexadecimal character arrays
     *
     * @param data     byte[]
     * @param toDigits Used to control output char[]
     * @return hexadecimal char[]
     */
    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * Converting byte arrays to hexadecimal strings
     *
     * @param data byte[]
     * @return hexadecimal String
     */
    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    /**
     * Converting byte arrays to hexadecimal strings
     *
     * @param data        byte[]
     * @param toLowerCase <code>true</code> Convert to lowercase format ， <code>false</code>
     * @return hexadecimal String
     */
    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * Converting byte arrays to hexadecimal strings
     *
     * @param data     byte[]
     * @param toDigits Used to control output char[]
     * @return hexadecimalString
     */
    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    /**
     * Converting hexadecimal character arrays to byte arrays
     *
     * @param data Hexadecimal char []
     * @return byte[]
     * @throws RuntimeException If the source hexadecimal character array is of a strange length, a runtime exception is thrown
     */
    public static byte[] decodeHex(char[] data) {

        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * Converting hexadecimal characters to an integer
     *
     * @param ch    Hexadecimal char
     * @param index The position of hexadecimal characters in character array
     * @return An integer
     * @throws RuntimeException When ch is not a legitimate hexadecimal character, a runtime exception is thrown
     */
    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }

}