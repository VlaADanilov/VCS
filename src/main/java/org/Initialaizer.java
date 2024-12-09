package org;

import org.DB.MySQLHelper;
import org.DB.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initialaizer implements ServletContextListener {
    private final Logger logger = LogManager.getLogger(Initialaizer.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Starting server");
        logger.info("Creating connections to DB...");
        Configuration config = new Configuration();
        config.createConnections();
        sce.getServletContext().setAttribute("database", new MySQLHelper(config));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MySQLHelper database = (MySQLHelper) sce.getServletContext().getAttribute("database");
        database.getConfiguration().closeConnections();
        logger.info("Stopping server");
    }
}
