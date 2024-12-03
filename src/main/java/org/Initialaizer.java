package org;

import org.DB.MySQL_helper;
import org.DB.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initialaizer implements ServletContextListener {
    private final static Logger logger = LogManager.getLogger(Initialaizer.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Starting server");
        logger.info("Creating connections to DB...");
        Configuration.createConnections();
        sce.getServletContext().setAttribute("database", new MySQL_helper());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Configuration.closeConnections();
        logger.info("Stopping server");
    }
}
