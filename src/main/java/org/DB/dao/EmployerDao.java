package org.DB.dao;

import org.DB.mappers.EmployeeMapper;
import org.apache.logging.log4j.LogManager;
import org.models.Employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployerDao extends AbstractDao<Employee> {
    static{
        logger = LogManager.getLogger(EmployerDao.class);
    }
    private static final EmployeeMapper mapper = new EmployeeMapper();

    //language=sql
    private final static String ADD_TO_DATABASE = "INSERT INTO employee(employee_name, employee_profession, employee_description, user_id) VALUES(?,?,?,?)";
    @Override
    public boolean save(Employee employee) {
        logger.info("Save employee " + employee.toString());
        int result = 0;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(ADD_TO_DATABASE)){
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getProfession());
            preparedStatement.setString(3, employee.getDescription());
            preparedStatement.setInt(4, employee.getUser_id());
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.info("Employee " + employee.toString() + " saved");
        return result > 0;
    }

    //language=sql
    private final static String DELETE_BY_ID= "DELETE FROM employee WHERE employee_id = ?";
    @Override
    public boolean deleteById(int id) {
        logger.info("Delete employee " + id);
        String string = getImagePath(id);
        File file = new File(path + "\\" +string);
        if(file.exists()){
            file.delete();
        }
        int result = 0;
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_BY_ID)){
            ps.setInt(1, id);
            result = ps.executeUpdate();
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.info("Delete employee " + id + " deleted");
        return result > 0;
    }

    //language=sql
    private final static String GET_BY_ID= "SELECT * FROM employee WHERE employee_id = ?";
    @Override
    public Employee findById(int id) {
        logger.info("Find employee " + id);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("Employee " + id + " found");
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL= "SELECT * FROM employee";
    @Override
    public List<Employee> findAll() {
        logger.info("Find all employees");
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while(resultSet.next()){
                employees.add(mapper.mapRow(resultSet));
            }
            logger.info("Employees found");
            return employees;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_BY_NAME= "SELECT * FROM employee WHERE employee_name = ?";
    public Employee findByName(String name) {
        logger.info("Find employee " + name);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("Employee " + name + " found");
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String UPDATE_IMAGE_BY_ID = "UPDATE employee SET image = ? WHERE employee_id = ?";
    public boolean updateImage(InputStream is, int id) {
        logger.info("Update image employee" + id);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_IMAGE_BY_ID)){
            String string = generateStr();
            int i = 0;
            File file = new File(path + "\\" + string + i);
            while(file.exists()){
                i++;
                file = new File(path + "\\" + string + i);
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(is.readAllBytes());
            fos.close();
            preparedStatement.setString(1, string + i);
            preparedStatement.setInt(2, id);
            int result = preparedStatement.executeUpdate();
            logger.info("Employee " + id + " updated");
            return result > 0;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private String generateStr(){
        Random random = new Random();
        int length = random.nextInt(11) + 5; // Случайная длина от 5 до 15 символов
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int charType = random.nextInt(2); // 0 - буква, 1 - цифра, 2 - специальный символ
            switch (charType) {
                case 0:
                    sb.append((char) (random.nextInt(26) + 'a')); // Случайная буква в нижнем регистре
                    break;
                case 1:
                    sb.append(random.nextInt(10)); // Случайная цифра
                    break;
            }
        }

        return sb.toString();
    }
    private static final String path = "C:\\КФУ\\ОРИС\\1 семестровка\\картинки сотрудников";
    public byte[] getImage(int id) {
        logger.info("Get image employee" + id);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String string = resultSet.getString("image");
            File file = new File(path + "\\" + string);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] ret = fileInputStream.readAllBytes();
            fileInputStream.close();
            logger.info("Employee " + id + " image found");
            return ret;
        }catch(Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public String getImagePath(int id) {
        logger.info("Get image path employee" + id);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("Employee " + id + " image found");
            return resultSet.getString("image");
        }catch(Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
