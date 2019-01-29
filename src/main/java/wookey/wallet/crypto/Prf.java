package wookey.wallet.crypto;

public class Prf {

    public static byte[] prf(int a, int b, int c, int d, byte[] x, byte[] y) {
        byte[] blob = new byte[64];
        System.arraycopy(x, 0, blob, 0, x.length);
        System.arraycopy(y, 0, blob, 32, y.length);

        blob[0] &= 0x0F;
        blob[0] |= (a == 1 ? 1 << 7 : 0) | (b == 1 ? 1 << 6 : 0) | (c == 1 ? 1 << 5 : 0) | (d == 1 ? 1 << 4 : 0);

        SHA256Compress sha256Compress = new SHA256Compress();
        sha256Compress.update(blob, 0, blob.length);
        return sha256Compress.getEncodedState();
    }

    public static byte[] prfAddr(byte[] aSk, int t) {
        byte[] y = new byte[32];
        y[0] = (byte) t;
        return prf(1, 1, 0, 0, aSk, y);
    }

    public static byte[] prfAddrAPk(byte[] aSk) {
        return prfAddr(aSk, 0);
    }

    public static byte[] prfAddrSkEnc(byte[] aSk) {
        return prfAddr(aSk, 1);
    }

    public static byte[] prfNf(byte[] aSk, byte[] rho) {
        return prf(1, 1, 1, 0, aSk, rho);
    }

    public static byte[] prfPk(byte[] aSk, int i0, byte[] hSig) {
        if (i0 != 0 && i0 != 1) {
            throw new Error("PRF_pk invoked with index out of bounds");
        }

        return prf(0, i0, 0, 0, aSk, hSig);
    }

    public static byte[] prfRho(byte[] phi, int i0, byte[] hSig) {
        if (i0 != 0 && i0 != 1) {
            throw new Error("PRF_rho invoked with index out of bounds");
        }

        return prf(0, i0, 1, 0, phi, hSig);
    }
}
