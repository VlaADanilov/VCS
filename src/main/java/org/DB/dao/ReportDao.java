package org.DB.dao;

import org.DB.mappers.ReportMapper;
import org.apache.logging.log4j.LogManager;
import org.models.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDao extends AbstractDao<Report> {
    static {
        logger = LogManager.getLogger(ReportDao.class);
    }
    private static final ReportMapper mapper = new ReportMapper();

    //language=sql
    private static final String ADD_TO_DB = "INSERT INTO report(auto_id, comment, user_id) VALUES (?,?,?)";
    @Override
    public boolean save(Report obj) {
        logger.info("Saving report: " + obj.toString());
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_DB)){
            preparedStatement.setInt(1, obj.getAuto_id());
            preparedStatement.setString(2, obj.getComment());
            preparedStatement.setInt(3, obj.getUser_id());
            int result = preparedStatement.executeUpdate();
            logger.info("Report saved to database: " + obj.toString());
            return result > 0;
        }catch (Exception e) {
            logger.error(e);
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String DELETE_BY_ID = "DELETE FROM report WHERE report_id = ?";
    @Override
    public boolean deleteById(int id) {
        logger.info("Deleting report: " + id);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)){
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            logger.info("Report deleted to database: " + id);
            return result > 0;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String FIND_BY_ID = "SELECT * FROM report WHERE report_id = ?";
    @Override
    public Report findById(int id) {
        logger.info("Finding report: " + id);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("Report found to database: " + id);
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String GET_ALL = "SELECT * FROM report";
    @Override
    public List<Report> findAll() {
        logger.info("Finding all reports");
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Report> reports = new ArrayList<>();
            while(resultSet.next()){
                reports.add(mapper.mapRow(resultSet));
            }
            logger.info("Report found to database: " + reports.size());
            return reports;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
