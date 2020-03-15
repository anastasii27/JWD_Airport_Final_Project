package by.epam.tr.dao.connectionpool;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;

public class PoolExistenceValidation {

    public boolean doesPoolExist(BlockingQueue <Connection> queue){
        if(queue ==null)
            return false;

        return true;
    }

}
