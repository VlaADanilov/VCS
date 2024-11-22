package org.DB.dao;

import org.DB.mappers.ReportMapper;
import org.models.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDao extends AbstractDao<Report> {
    private static final ReportMapper mapper = new ReportMapper();

    //language=sql
    private static final String ADD_TO_DB = "INSERT INTO report(auto_id, comment, user_id) VALUES (?,?,?)";
    @Override
    public boolean save(Report obj) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(ADD_TO_DB)){
            preparedStatement.setInt(1, obj.getAuto_id());
            preparedStatement.setString(2, obj.getComment());
            preparedStatement.setInt(3, obj.getUser_id());
            return preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String DELETE_BY_ID = "DELETE FROM report WHERE report_id = ?";
    @Override
    public boolean deleteById(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_BY_ID)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String FIND_BY_ID = "SELECT * FROM report WHERE report_id = ?";
    @Override
    public Report findById(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String GET_ALL = "SELECT * FROM report";
    @Override
    public List<Report> findAll() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Report> reports = new ArrayList<>();
            while(resultSet.next()){
                reports.add(mapper.mapRow(resultSet));
            }
            return reports;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
