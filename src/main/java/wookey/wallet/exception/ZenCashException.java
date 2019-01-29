package wookey.wallet.exception;

public class ZenCashException extends Exception {
  public Exception previous;

  public ZenCashException(String s) {
    super(s);
    previous = null;
  }

  public ZenCashException(String s, Exception e) {
    super(s);
    previous = e;
  }
}