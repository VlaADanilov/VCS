package org.DB;


import org.DB.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Configuration {

    private final Logger log = LoggerFactory.getLogger(Configuration.class);
    private ConnectionsCreater con;

    public ImageDao getImageDao() {
        return new ImageDao(this);
    }

    public AutoModelDao getAutoModelDao() {
        return new AutoModelDao(this);
    }

    public UserDao getUserDao() {
        return new UserDao(this);
    }

    public EmployerDao getEmployerDao() {return new EmployerDao(this);}

    public LikeDao getLikeDao() {return new LikeDao(this);}

    public BrandDao getBrandDao() {return new BrandDao(this);}

    public ReportDao getReportDao() {return new ReportDao(this);}

    public Connection getConnection(){
        try{
            return con.getConnection();
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
    public void createConnections(){
        con = new ConnectionsCreater();
    }
    public void closeConnections(){
        con.closeAllConnections();
    }
}
