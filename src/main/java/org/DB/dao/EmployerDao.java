package org.DB.dao;

import org.DB.mappers.EmployeeMapper;
import org.models.Employee;
import org.models.User;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployerDao extends AbstractDao<Employee> {
    private EmployeeMapper mapper = new EmployeeMapper();

    //language=sql
    private final static String ADD_TO_DATABASE = "INSERT INTO employee(employee_name, employee_profession, employee_description, user_id) VALUES(?,?,?,?)";
    @Override
    public boolean save(Employee employee) {
        int result = 0;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(ADD_TO_DATABASE)){
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getProfession());
            preparedStatement.setString(3, employee.getDescription());
            preparedStatement.setInt(4, employee.getUser_id());
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return result > 0;
    }

    //language=sql
    private final static String DELETE_BY_ID= "DELETE FROM employee WHERE employee_id = ?";
    @Override
    public boolean deleteById(int id) {
        int result = 0;
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_BY_ID)){
            ps.setInt(1, id);
            result = ps.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result > 0;
    }

    //language=sql
    private final static String GET_BY_ID= "SELECT * FROM employee WHERE employee_id = ?";
    @Override
    public Employee findById(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL= "SELECT * FROM employee";
    @Override
    public List<Employee> findAll() {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while(resultSet.next()){
                employees.add(mapper.mapRow(resultSet));
            }
            return employees;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_BY_NAME= "SELECT * FROM employee WHERE employee_name = ?";
    public Employee findByName(String name) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String UPDATE_IMAGE_BY_ID = "UPDATE employee SET image = ? WHERE employee_id = ?";
    public boolean updateImage(InputStream is, int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_IMAGE_BY_ID)){
            preparedStatement.setBlob(1, is);
            preparedStatement.setInt(2, id);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
