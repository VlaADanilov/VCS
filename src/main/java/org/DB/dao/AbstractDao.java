package org.DB.dao;

import org.DB.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDao<T> {
    protected Configuration config;

    public AbstractDao(Configuration config) {
        this.config = config;
    }

    protected Logger logger = LogManager.getLogger(AbstractDao.class);
    public abstract boolean save(T obj);

    public abstract boolean deleteById(int id);

    public abstract T findById(int id);

    public abstract List<T> findAll();

    protected Connection getConnection() {
        try{
            return config.getConnection();
        }catch(Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
