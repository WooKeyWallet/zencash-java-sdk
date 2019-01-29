package wookey.wallet;

import wookey.wallet.builder.ZAddressBuilder;
import org.junit.Assert;
import org.junit.Test;

public class ZAddressTest {

    @Test
    public void mkZSecretKey() {
        Assert.assertEquals("0c10a61a669bc4a51000c4c74ff58c151912889891089f7bae5e4994a73af7a8",
                ZAddressBuilder.mkZSecretKey("Z pigs likes to snooze. ZZZZ"));
    }

    @Test
    public void zSecretToSpendingKey() {
        Assert.assertEquals("ST16cMa3MkgDYBDPLX72tuxxoEVfAq2hBzPenGcjdFiYWYDPegNV",
                ZAddressBuilder.zSecretKeyToSpendingKey("0c10a61a669bc4a51000c4c74ff58c151912889891089f7bae5e4994a73af7a8"));
    }

    @Test
    public void zSecretToPayingKey() {
        Assert.assertEquals("927a3819627247c0dd39102ec5449fc6fc952e242aad08615df9f26718912e27",
                ZAddressBuilder.zSecretKeyToPayingKey("0c10a61a669bc4a51000c4c74ff58c151912889891089f7bae5e4994a73af7a8"));
    }

    @Test
    public void zSecretToTransmissionKey() {
        Assert.assertEquals("22d666c34ababacf6a9a4a752cc7870b505b64e85638aa45d23ac32992397960",
                ZAddressBuilder.zSecretKeyToTransmissionKey("0c10a61a669bc4a51000c4c74ff58c151912889891089f7bae5e4994a73af7a8"));
    }

    @Test
    public void mkZAddress() {
        Assert.assertEquals("ztdAiEutq8s2aHAH2SDtJ1jGggotNyUSEqZfowpiq3AdMT3Lm2cBBbFeCUDUdzto758AeaarVjPmrqyoa2V5WabkuJAekfw",
                ZAddressBuilder.mkZAddress("927a3819627247c0dd39102ec5449fc6fc952e242aad08615df9f26718912e27",
                        "22d666c34ababacf6a9a4a752cc7870b505b64e85638aa45d23ac32992397960"));
    }


}
