package by.epam.airport_system.service;

public class  ServiceException extends Exception{

    public ServiceException(){}

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Throwable throwable){
        super(message, throwable);
    }

    public ServiceException(Throwable throwable){
        super(throwable);
    }
}
