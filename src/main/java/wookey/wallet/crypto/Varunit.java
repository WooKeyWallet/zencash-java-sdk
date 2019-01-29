package wookey.wallet.crypto;

public class Varunit {

    private final static long MAX_SAFE_INTEGER = 9007199254740991l;

    private static void checkUInt53(long n) {
        if (n < 0 || n > MAX_SAFE_INTEGER || n % 1 != 0) throw new Error("value out of range");
    }

    private static int encodingLength(long number) {
        checkUInt53(number);

        return (
                number < 0xfd ? 1
                        : number <= 0xffff ? 3
                        : number <= 0xffffffff ? 5
                        : 9
        );
    }

    public static byte[] encode(long number, byte[] buffer, int offset) {
        checkUInt53(number);

        if (buffer == null) {
            buffer = new byte[encodingLength(number)];
        }

        // 8 bit
        if (number < 0xfd) {

            // 16 bit
        } else if (number <= 0xffff) {
            // 32 bit
        } else if (number <= 0xffffffff) {

            // 64 bit
        } else {

        }
        return buffer;
    }

    public static long decode(byte[] buffer, int offset) {
        long first = Utils.readInt8(buffer, offset);
        // 8 bit
        if (first < 0xfd) {
            return first;

            // 16 bit
        } else if (first == 0xfd) {
            return 0;

            // 32 bit
        } else if (first == 0xfe) {
            return 0;

            // 64 bit
        } else {
            return 0;
        }
    }

}
