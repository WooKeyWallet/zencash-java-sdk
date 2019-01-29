package wookey.wallet.crypto;

import org.whispersystems.curve25519.JavaCurve25519Provider;

public class ZenCurve25519Provider extends JavaCurve25519Provider {
    public ZenCurve25519Provider() {
        super();
    }

    @Override
    public boolean isNative() {
        return super.isNative();
    }
}
