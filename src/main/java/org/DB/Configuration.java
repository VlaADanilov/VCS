package org.DB;


import org.DB.dao.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Configuration {
    private static ConnectionsCreater con;

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
        return con.getConnection();
    }
    public static void createConnections(){
        con = new ConnectionsCreater();
    }
    public static void closeConnections(){
        con.closeAllConnections();
    }
}
