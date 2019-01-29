package wookey.wallet.builder;

import wookey.wallet.config.Config;
import wookey.wallet.crypto.Base58Check;
import wookey.wallet.crypto.Prf;
import wookey.wallet.crypto.Utils;
import wookey.wallet.crypto.ZenCurve25519Provider;
import org.apache.commons.lang3.StringUtils;

/**
 * ZAddressBuilder
 */
public class ZAddressBuilder {

    private static Config config = Config.getInstance();
    private static String net = config.getProperty("net");

    /**
     * Creates a Z secret key (a_sk)
     *
     * @param phrase (Password phrase)
     * @return Z secret key (a_sk)
     */
    public static String mkZSecretKey(String phrase) {
        String a_sk = AddressBuilder.mkPrivkey(phrase);
        byte[] bytes = Utils.HEX.decode(a_sk);
        bytes[0] &= 0x0f;
        return Utils.HEX.encode(bytes);
    }

    /**
     * Converts the secret key to a spending key
     *
     * @param a_sk      (secret key)
     * @param arguments (secret key hash,optional)
     * @return sk (spending key)
     */
    public static String zSecretKeyToSpendingKey(String a_sk, String... arguments) {
        String zcSpendingKeyHash = config.getProperty(net + ".zcSpendingKeyHash");
        if (arguments != null) {
            if (arguments.length > 0 && StringUtils.isNotEmpty(arguments[0])) {
                zcSpendingKeyHash = arguments[0];
            }
        }
        byte[] buf = Utils.HEX.decode(zcSpendingKeyHash + a_sk);
        return Base58Check.encode(buf);
    }

    /**
     * Converts a Z secret key to a paying key
     *
     * @param a_sk (secret key)
     * @return a_pk key (paying key)
     */
    public static String zSecretKeyToPayingKey(String a_sk) {
        byte[] buf = Utils.HEX.decode(a_sk);
        return Utils.HEX.encode(Prf.prfAddrAPk(buf));
    }

    /**
     * Converts a Z secret key to a transmission key
     *
     * @param a_sk (secret key)
     * @return pk_enc key (transmisison key)
     */
    public static String zSecretKeyToTransmissionKey(String a_sk) {
        byte[] sk_enc = Prf.prfAddrSkEnc(Utils.HEX.decode(a_sk));
        // Curve 25519 clamping
        sk_enc[0] &= 248;
        sk_enc[31] &= 127;
        sk_enc[31] |= 64;

        ZenCurve25519Provider zenCurve25519Provider = new ZenCurve25519Provider();
        return Utils.HEX.encode(zenCurve25519Provider.generatePublicKey(sk_enc));
    }

    /**
     * Makes a Z address given:
     *
     * @param payingKey       a_pk (paying key)
     * @param transmissionKey pk_enc key (transmission key)
     * @param arguments
     * @return Zaddress
     */
    public static String mkZAddress(String payingKey, String transmissionKey, String... arguments) {
        String zcPaymentAddressHash = config.getProperty(net + ".zcPaymentAddressHash");
        if (arguments != null) {
            if (arguments.length > 0 && StringUtils.isNotEmpty(arguments[0])) {
                zcPaymentAddressHash = arguments[0];
            }
        }
        byte[] buf = Utils.HEX.decode(zcPaymentAddressHash + payingKey + transmissionKey);
        return Base58Check.encode(buf);

    }
}
