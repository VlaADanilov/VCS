package org.DB;


import org.DB.dao.AutoModelDao;
import org.DB.dao.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class Configuration {
    private static final String URL = "jdbc:mysql://localhost:3306/auto_base";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static AutoModelDao getAutoModelDao() {
        return new AutoModelDao();
    }

    public static UserDao getUserDao() {
        return new UserDao();
    }

    public static Connection getConnection() throws SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
