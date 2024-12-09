package org.DB.dao;

import org.DB.Configuration;
import org.DB.mappers.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.mindrot.jbcrypt.BCrypt;
import org.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User> {

    public UserDao(Configuration configuration) {
        super(configuration);
        mapper = new UserMapper();
        logger = LogManager.getLogger(UserDao.class);
    }

    private final UserMapper mapper;
    //language=sql
        private final static String ADD_TO_DATABASE = "INSERT INTO user(user_name, user_password, user_status, user_phone) VALUES(?,?,?,?)";
        @Override
        public boolean save(User user) {
            logger.info("Saving user: " + user);
            int result = 0;
            try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_DATABASE)){
                preparedStatement.setString(1, user.getName());
                String hash_password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
                preparedStatement.setString(2, hash_password);
                preparedStatement.setString(3, user.getStatus());
                preparedStatement.setString(4, user.getPhone());
                result = preparedStatement.executeUpdate();
            }catch (SQLException e){
                logger.error(e);
            throw new RuntimeException(e);
            }
            logger.info("User saved: " + user);
            return result > 0;
        }

    //language=sql
    private final static String DELETE_BY_ID= "DELETE FROM user WHERE user_id = ?";
    @Override
    public boolean deleteById(int id) {
        logger.info("Deleting user: " + id);
        int result = 0;
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_BY_ID)){
            ps.setInt(1, id);
            result = ps.executeUpdate();
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.info("User deleted: " + id);
        return result > 0;
    }

    //language=sql
    private final static String DELETE_BY_NAME= "DELETE FROM user WHERE user_name = ?";
    public boolean deleteByName(String name) {
        logger.info("Deleting user: " + name);
        int result = 0;
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_BY_NAME)){
            ps.setString(1, name);
            result = ps.executeUpdate();
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.info("User deleted: " + name);
        return result > 0;
    }

    //language=sql
    private final static String GET_BY_ID= "SELECT * FROM user WHERE user_id = ?";
    @Override
    public User findById(int id) {
        logger.info("Finding user: " + id);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("User found: " + id);
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL= "SELECT * FROM user";
    @Override
    public List<User> findAll() {
        logger.info("Finding all users");
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while(resultSet.next()){
                users.add(mapper.mapRow(resultSet));
            }
            logger.info("Users found: " + users.size());
            return users;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL_LIKE= "SELECT * FROM user WHERE user_name LIKE ?";
    public List<User> findAllLike(String str) {
        logger.info("Finding all users like " + str);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LIKE)){
            preparedStatement.setString(1, "%"+str+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while(resultSet.next()){
                users.add(mapper.mapRow(resultSet));
            }
            logger.info("Users found: " + users.size());
            return users;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_BY_NAME= "SELECT * FROM user WHERE user_name = ?";
    public User findByName(String username) {
        logger.info("Finding user: " + username);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("User found: " + username);
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String UPDATE_STATUS = "UPDATE user SET user_status = ? WHERE user_id = ?";
    public boolean updateStatus(String status, int id) {
        logger.info("Updating user " + id + " status: " + status);
        int result = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.info("User updated: " + id);
        return result > 0;
    }

    //language=sql
    public boolean checkPassword(String username, String password) {
        User user = findByName(username);
        return BCrypt.checkpw(password, user.getPassword());
    }
}
