package org.DB.dao;

import org.DB.mappers.UserMapper;
import org.models.Auto_model;
import org.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User> {
    private UserMapper mapper = new UserMapper();
    //language=sql
        private final static String ADD_TO_DATABASE = "INSERT INTO user(user_name, user_password, user_status, user_phone) VALUES(?,?,?,?)";
        @Override
        public boolean save(User user) {
            int result = 0;
            try(PreparedStatement preparedStatement = getConnection().prepareStatement(ADD_TO_DATABASE)){
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getStatus());
                preparedStatement.setString(4, user.getPhone());
                result = preparedStatement.executeUpdate();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
            return result > 0;
        }

    //language=sql
    private final static String DELETE_BY_ID= "DELETE FROM user WHERE user_id = ?";
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
    private final static String DELETE_BY_NAME= "DELETE FROM user WHERE user_name = ?";
    public boolean deleteByName(String name) {
        int result = 0;
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_BY_NAME)){
            ps.setString(1, name);
            result = ps.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result > 0;
    }

    //language=sql
    private final static String GET_BY_ID= "SELECT * FROM user WHERE user_id = ?";
    @Override
    public User findById(int id) {
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
    private final static String GET_ALL= "SELECT * FROM user";
    @Override
    public List<User> findAll() {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while(resultSet.next()){
                users.add(mapper.mapRow(resultSet));
            }
            return users;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL_LIKE= "SELECT * FROM user WHERE user_name LIKE ?";
    public List<User> findAllLike(String str) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL_LIKE)){
            preparedStatement.setString(1, "%"+str+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while(resultSet.next()){
                users.add(mapper.mapRow(resultSet));
            }
            return users;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_BY_NAME= "SELECT * FROM user WHERE user_name = ?";
    public User findByName(String username) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String UPDATE_STATUS = "UPDATE user SET user_status = ? WHERE user_id = ?";
    public boolean updateStatus(String status, int id) {
        int result = 0;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_STATUS)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result > 0;
    }

    //language=sql
    public boolean checkPassword(String username, String password) {
        User user = findByName(username);
        return user.getPassword().equals(password);
    }
}
