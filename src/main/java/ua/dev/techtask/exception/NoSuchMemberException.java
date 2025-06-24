package ua.dev.techtask.exception;

public class NoSuchMemberException extends RuntimeException{

    public NoSuchMemberException(){}

    public NoSuchMemberException(String errorMessage){
        super(errorMessage);
    }

    public NoSuchMemberException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
