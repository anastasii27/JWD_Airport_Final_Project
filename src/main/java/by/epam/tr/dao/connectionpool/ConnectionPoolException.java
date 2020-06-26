package by.epam.tr.dao.connectionpool;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(){}

    public ConnectionPoolException(String message){
        super(message);
    }

    public ConnectionPoolException(String message, Throwable throwable){
        super(message, throwable);
    }

    public ConnectionPoolException(Throwable throwable){
        super(throwable);
    }

}
