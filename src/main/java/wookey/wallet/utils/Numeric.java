

package wookey.wallet.utils;

import wookey.wallet.exception.MessageDecodingException;
import wookey.wallet.exception.MessageEncodingException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;


public final class Numeric {
    private static final String HEX_PREFIX = "0x";

    private Numeric() {
    }

    public static String encodeQuantity(BigInteger value) {
        if (value.signum() != -1) {
            return "0x" + value.toString(16);
        } else {
            throw new MessageEncodingException("Negative values are not supported");
        }
    }

    public static BigInteger decodeQuantity(String value) {
        if (!isValidHexQuantity(value)) {
            throw new MessageDecodingException("Value must be in format 0x[1-9]+[0-9]* or 0x0");
        } else {
            try {
                return new BigInteger(value.substring(2), 16);
            } catch (NumberFormatException var2) {
                throw new MessageDecodingException("Negative ", var2);
            }
        }
    }

    private static boolean isValidHexQuantity(String value) {
        if (value == null) {
            return false;
        } else if (value.length() < 3) {
            return false;
        } else {
            return value.startsWith("0x");
        }
    }

    public static String cleanHexPrefix(String input) {
        return containsHexPrefix(input) ? input.substring(2) : input;
    }

    public static String prependHexPrefix(String input) {
        return !containsHexPrefix(input) ? "0x" + input : input;
    }

    public static boolean containsHexPrefix(String input) {
        return input.length() > 1 && input.charAt(0) == '0' && input.charAt(1) == 'x';
    }

    public static BigInteger toBigInt(byte[] value, int offset, int length) {
        return toBigInt(Arrays.copyOfRange(value, offset, offset + length));
    }

    public static BigInteger toBigInt(byte[] value) {
        return new BigInteger(1, value);
    }

    public static BigInteger toBigInt(String hexValue) {
        String cleanValue = cleanHexPrefix(hexValue);
        return toBigIntNoPrefix(cleanValue);
    }

    public static BigInteger toBigIntNoPrefix(String hexValue) {
        return new BigInteger(hexValue, 16);
    }

    public static String toHexStringWithPrefix(BigInteger value) {
        return "0x" + value.toString(16);
    }

    public static String toHexStringNoPrefix(BigInteger value) {
        return value.toString(16);
    }

    public static String toHexStringNoPrefix(byte[] input) {
        return toHexString(input, 0, input.length, false);
    }

    public static String toHexStringWithPrefixZeroPadded(BigInteger value, int size) {
        return toHexStringZeroPadded(value, size, true);
    }

    public static String toHexStringWithPrefixSafe(BigInteger value) {
        String result = toHexStringNoPrefix(value);
        if (result.length() < 2) {
            result = Strings.zeros(1) + result;
        }

        return "0x" + result;
    }

    public static String toHexStringNoPrefixZeroPadded(BigInteger value, int size) {
        return toHexStringZeroPadded(value, size, false);
    }

    private static String toHexStringZeroPadded(BigInteger value, int size, boolean withPrefix) {
        String result = toHexStringNoPrefix(value);
        int length = result.length();
        if (length > size) {
            throw new UnsupportedOperationException("Value " + result + "is larger then length " + size);
        } else if (value.signum() < 0) {
            throw new UnsupportedOperationException("Value cannot be negative");
        } else {
            if (length < size) {
                result = Strings.zeros(size - length) + result;
            }

            return withPrefix ? "0x" + result : result;
        }
    }

    public static byte[] toBytesPadded(BigInteger value, int length) {
        byte[] result = new byte[length];
        byte[] bytes = value.toByteArray();
        int bytesLength;
        byte srcOffset;
        if (bytes[0] == 0) {
            bytesLength = bytes.length - 1;
            srcOffset = 1;
        } else {
            bytesLength = bytes.length;
            srcOffset = 0;
        }

        if (bytesLength > length) {
            throw new RuntimeException("Input is too large to put in byte array of size " + length);
        } else {
            int destOffset = length - bytesLength;
            System.arraycopy(bytes, srcOffset, result, destOffset, bytesLength);
            return result;
        }
    }

    public static byte[] hexStringToByteArray(String input) {
        String cleanInput = cleanHexPrefix(input);
        int len = cleanInput.length();
        if (len == 0) {
            return new byte[0];
        } else {
            byte[] data;
            byte startIdx;
            if (len % 2 != 0) {
                data = new byte[len / 2 + 1];
                data[0] = (byte) Character.digit(cleanInput.charAt(0), 16);
                startIdx = 1;
            } else {
                data = new byte[len / 2];
                startIdx = 0;
            }

            for (int i = startIdx; i < len; i += 2) {
                data[(i + 1) / 2] = (byte) ((Character.digit(cleanInput.charAt(i), 16) << 4) + Character.digit(cleanInput.charAt(i + 1), 16));
            }

            return data;
        }
    }

    public static String toHexString(byte[] input, int offset, int length, boolean withPrefix) {
        StringBuilder stringBuilder = new StringBuilder();
        if (withPrefix) {
            stringBuilder.append("0x");
        }

        for (int i = offset; i < offset + length; ++i) {
            stringBuilder.append(String.format("%02x", input[i] & 255));
        }

        return stringBuilder.toString();
    }

    public static String toHexString(byte[] input) {
        return toHexString(input, 0, input.length, true);
    }

    public static byte asByte(int m, int n) {
        return (byte) (m << 4 | n);
    }

    public static boolean isIntegerValue(BigDecimal value) {
        return value.signum() == 0 || value.scale() <= 0 || value.stripTrailingZeros().scale() <= 0;
    }
}
