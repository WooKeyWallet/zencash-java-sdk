

package wookey.wallet.utils;

public class Assertions {
    public Assertions() {
    }

    public static void verifyPrecondition(boolean assertionResult, String errorMessage) {
        if (!assertionResult) {
            throw new RuntimeException(errorMessage);
        }
    }
}
