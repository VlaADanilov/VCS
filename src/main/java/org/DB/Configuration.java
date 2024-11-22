package org.DB;


import org.DB.dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class Configuration {
    private static final String URL = "jdbc:mysql://localhost:3306/auto_base";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection CON;

    public static ImageDao getImageDao() {
        return new ImageDao();
    }

    public static AutoModelDao getAutoModelDao() {
        return new AutoModelDao();
    }

    public static UserDao getUserDao() {
        return new UserDao();
    }

    public static EmployerDao getEmployerDao() {return new EmployerDao();}

    public static LikeDao getLikeDao() {return new LikeDao();}

    public static BrandDao getBrandDao() {return new BrandDao();}

    public static ReportDao getReportDao() {return new ReportDao();}

    public static Connection getConnection() throws SQLException {
        if (CON == null || CON.isClosed()) {
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            CON = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return CON;
    }
}
