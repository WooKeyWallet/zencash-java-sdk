package wookey.wallet.enums;

/**
 * HashCodes
 */
public enum SigHashCodes {
    SIGHASH_ALL (1),
    SIGHASH_NONE (2),
    SIGHASH_SINGLE (3),
    SIGHASH_ANYONECANPAY (0x80);

    private int value;

    private SigHashCodes(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
