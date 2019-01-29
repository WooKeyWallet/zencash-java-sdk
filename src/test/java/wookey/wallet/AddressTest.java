package wookey.wallet;

import wookey.wallet.builder.AddressBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AddressTest {

    @Test
    public void mkPrivateKey() {
        Assert.assertEquals("2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c",
                AddressBuilder.mkPrivkey("chris p. bacon, defender of the guardians"));
    }

    @Test
    public void privateKeyToWIFFormat() {
        Assert.assertEquals("91vPu8ScdEXt2DcLNfhybQVmMkbdrQMKkPD7adD53ejcXiCGb3V",
                AddressBuilder.privKeyToWIF("2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c"));
    }

    @Test
    public void privateKeyToPublicKey() {
        Assert.assertEquals("048a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2e4d234528ff87b83f971ab2b12cd2939ff33c7846716827a5b0e8233049d8aad",
                AddressBuilder.privKeyToPubKey("2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c"));

        Assert.assertEquals("038a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2",
                AddressBuilder.privKeyToPubKey("2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c", true));
    }

    @Test
    public void publicKeyToPublicAddress() {
        Assert.assertEquals("zto474rZriU66nJtNEGTa6r1yte7GcbB7c6",
                AddressBuilder.pubKeyToAddr("048a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2e4d234528ff87b83f971ab2b12cd2939ff33c7846716827a5b0e8233049d8aad"));

        Assert.assertEquals("ztposbcmamHgbPABoBJ9zNKpV463e31hu2k",
                AddressBuilder.pubKeyToAddr("038a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2"));
    }

    @Test
    public void mkMultiSigRedeemScript() {
        Assert.assertEquals("522103519842d08ea56a635bfa8dd617b8e33f0426530d8e201107dd9a6af9493bd4872102d3ac8c0cb7b99a26cd66269a312afe4e0a621579dfe8b33e29c597a32a6165442102696187262f522cf1fa2c30c5cd6853c4a6c51ad5ba418abb4e3898dbc5a93d2e53ae",
                AddressBuilder.mkMultiSigRedeemScript(Arrays.asList("03519842d08ea56a635bfa8dd617b8e33f0426530d8e201107dd9a6af9493bd487",
                        "02d3ac8c0cb7b99a26cd66269a312afe4e0a621579dfe8b33e29c597a32a616544", "02696187262f522cf1fa2c30c5cd6853c4a6c51ad5ba418abb4e3898dbc5a93d2e"), 2, 3));
    }

    @Test
    public void multiSigRSToAddress() {
        Assert.assertEquals("zrA5nrGw7h5M8BYH8a6gpToQ8B5q3fRhMHc",
                AddressBuilder.multiSigRSToAddress("522103519842d08ea56a635bfa8dd617b8e33f0426530d8e201107dd9a6af9493bd4872102d3ac8c0cb7b99a26cd66269a312afe4e0a621579dfe8b33e29c597a32a6165442102696187262f522cf1fa2c30c5cd6853c4a6c51ad5ba418abb4e3898dbc5a93d2e53ae"));
    }

}
