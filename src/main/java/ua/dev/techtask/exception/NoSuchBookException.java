package ua.dev.techtask.exception;

public class NoSuchBookException extends RuntimeException{
    
    public NoSuchBookException(){}

    public NoSuchBookException(String errorMessage){
        super(errorMessage);
    }

    public NoSuchBookException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }

}
