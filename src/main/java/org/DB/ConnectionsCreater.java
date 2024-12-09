package org.DB;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

public class ConnectionsCreater {
    private static final String URL = "jdbc:mysql://localhost:3306/auto_base";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private final static Logger logger = LogManager.getLogger(ConnectionsCreater.class);
    private HikariConfig hikariConfig;
    private HikariDataSource dataSource;

    public ConnectionsCreater(){
        initHikariConfig();
        dataSource = new HikariDataSource(hikariConfig);
    }

    private void initHikariConfig() {
        hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setJdbcUrl(URL);
        hikariConfig.setUsername(USERNAME);
        hikariConfig.setPassword(PASSWORD);
        hikariConfig.setMaximumPoolSize(1000);
    }

    public synchronized Connection getConnection(){
        try{
            return dataSource.getConnection();
        }catch(SQLException e){
            logger.error(e);
            return null;
        }
    }

    public void closeAllConnections(){
        dataSource.close();
    }
}
