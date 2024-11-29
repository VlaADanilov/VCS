package org;

import org.DB.MySQL_helper;
import org.DB.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initialaizer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Configuration.createConnections();
        sce.getServletContext().setAttribute("database", new MySQL_helper());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Configuration.closeConnections();
    }
}
