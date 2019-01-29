
package wookey.wallet.crypto;

import wookey.wallet.utils.Numeric;
import org.bouncycastle.jcajce.provider.digest.Keccak.Digest256;
import org.bouncycastle.jcajce.provider.digest.Keccak.DigestKeccak;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Hash {
    private Hash() {
    }

    public static String sha3(String hexInput) {
        byte[] bytes = Numeric.hexStringToByteArray(hexInput);
        byte[] result = sha3(bytes);
        return Numeric.toHexString(result);
    }

    public static byte[] sha3(byte[] input, int offset, int length) {
        DigestKeccak kecc = new Digest256();
        kecc.update(input, offset, length);
        return kecc.digest();
    }

    public static byte[] sha3(byte[] input) {
        return sha3(input, 0, input.length);
    }

    public static String sha3String(String utf8String) {
        return Numeric.toHexString(sha3(utf8String.getBytes(StandardCharsets.UTF_8)));
    }

    public static byte[] sha256(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException var2) {
            throw new RuntimeException("Couldn't find a SHA-256 provider", var2);
        }
    }
}
