package by.epam.tr.dao;

public class DaoException extends Exception {

    public DaoException(){}

    public DaoException(String message, Throwable throwable){
        super(message, throwable);
    }

    public DaoException(String message){
        super(message);
    }

    public DaoException(Throwable throwable){
        super(throwable);
    }

}
