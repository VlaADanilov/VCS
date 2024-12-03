package org.DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayDeque;
import java.util.Deque;

public class ConnectionsCreater {
    private static final String URL = "jdbc:mysql://localhost:3306/auto_base";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private Deque<Connection> connections;
    private final static Logger logger = LogManager.getLogger(ConnectionsCreater.class);

    public ConnectionsCreater(){
        connections = new ArrayDeque<>();
        initConnections(10);
    }

    private void initConnections(int i){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            for(int j = 0; j < i; j++){
                connections.add(DriverManager.getConnection(URL, USERNAME, PASSWORD));
            }
        }catch (Exception e){
            logger.error(e);
        }
    }

    public Connection getConnection(){
        try{
            if(connections.isEmpty()){
                initConnections(1);
            }
            Connection connection = connections.pop();
            if(connection.isValid(0)){
                return connection;
            }
            else{
                initConnections(1);
                return getConnection();
            }
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public void releaseConnection(Connection connection){
        try{
            if(connection.isValid(0)){
                connections.add(connection);
            }
        }catch (Exception e){
            logger.error(e);
        }
    }

    public void closeAllConnections(){
        while (!connections.isEmpty()){
            try{
                Connection connection = connections.pop();
                connection.close();
            }catch (Exception e){
                continue;
            }

        }
    }
}
