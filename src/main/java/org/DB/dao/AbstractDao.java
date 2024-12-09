package org.DB.dao;

import org.DB.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDao<T> {
    protected static Logger logger = LogManager.getLogger(AbstractDao.class);
    public abstract boolean save(T obj);

    public abstract boolean deleteById(int id);

    public abstract T findById(int id);

    public abstract List<T> findAll();

    protected Connection getConnection() {
        try{
            return Configuration.getConnection();
        }catch(Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
