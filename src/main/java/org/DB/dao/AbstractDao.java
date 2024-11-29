package org.DB.dao;

import org.DB.Configuration;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDao<T> {

    public abstract boolean save(T obj);

    public abstract boolean deleteById(int id);

    public abstract T findById(int id);

    public abstract List<T> findAll();

    protected Connection getConnection() {
        try{
            return Configuration.getConnection();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
