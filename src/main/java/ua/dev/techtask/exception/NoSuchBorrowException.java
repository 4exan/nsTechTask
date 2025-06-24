package ua.dev.techtask.exception;

public class NoSuchBorrowException extends RuntimeException {

  public NoSuchBorrowException() {
  }

  public NoSuchBorrowException(String errorMessage) {
    super(errorMessage);
  }

  public NoSuchBorrowException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }

}
