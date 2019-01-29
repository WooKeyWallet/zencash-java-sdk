package wookey.wallet.crypto;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;

public class Base58Check {

    public static String encode(byte[] payload) {
        byte[] checksum = Sha256Hash.hashTwice(payload);
        return Base58.encode(Utils.byteMerger(payload, checksum, payload.length + 4));
    }

    public static byte[] decode(String input) {
        byte[] buffer = Base58.decode(input);
        byte[] payload = decodeRaw(buffer);
        if (payload == null || payload.length == 0) {
            throw new Error("Invalid checksum");
        }
        return payload;
    }

    private static byte[] decodeRaw(byte[] bytes) {
        byte[] payload = Utils.subarray(bytes, 0, bytes.length - 4);
        byte[] checksum = Utils.subarray(bytes, bytes.length - 4, bytes.length);
        byte[] newChecksum = Sha256Hash.hashTwice(payload);

        if ((checksum[0] ^ newChecksum[0] |
                checksum[1] ^ newChecksum[1] |
                checksum[2] ^ newChecksum[2] |
                checksum[3] ^ newChecksum[3]) != 1) {
            return payload;
        }
        return null;
    }

}
