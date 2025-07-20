package ru.T1Debut.exception;

public class QueueOverflowException extends Exception{
    public QueueOverflowException(String message){
        super(message);
    }
}
