package org.DB.dao;

import org.apache.logging.log4j.LogManager;
import org.models.Like;
import org.DB.mappers.LikeMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LikeDao extends AbstractDao<Like> {
    static {
        logger = LogManager.getLogger(LikeDao.class);
    }
    private static final LikeMapper mapper = new LikeMapper();
    //language=sql
    private static final String ADD_TO_DB = "INSERT INTO likes (user_id, auto_id) VALUES (?, ?)";
    @Override
    public boolean save(Like obj) {
        logger.info("Saving like: " + obj);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_DB)){
            preparedStatement.setInt(1, obj.getUserId());
            preparedStatement.setInt(2, obj.getAutoId());
            int result = preparedStatement.executeUpdate();
            logger.info("Successfully saved like: " + obj);
            return result > 0;
        }catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String DELETE_FROM_DB = "DELETE FROM likes WHERE like_id = ?";
    @Override
    public boolean deleteById(int id) {
        logger.info("Deleting like: " + id);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_DB)){
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            logger.info("Successfully deleted like: " + id);
            return result > 0;
        }catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String DELETE_FROM_DB_BY_UI_AI = "DELETE FROM likes WHERE user_id = ? AND auto_id = ?";

    public boolean delete(int user_id, int auto_id) {
        logger.info("Deleting like: " + user_id + " " + auto_id);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_DB_BY_UI_AI)){
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, auto_id);
            int result = preparedStatement.executeUpdate();
            logger.info("Successfully deleted like: " + user_id + " " + auto_id);
            return result > 0;
        }catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String GET_FROM_DB_BY_ID = "SELECT * FROM likes WHERE like_id = ?";
    @Override
    public Like findById(int id) {
        logger.info("Finding like: " + id);
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_FROM_DB_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("Successfully found like: " + id);
            return mapper.mapRow(resultSet);
        }catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
    //language=sql
    private static final String GET_FROM_DB_BY_UI_AI = "SELECT * FROM likes WHERE user_id = ? AND auto_id = ?";
    public Like find(int user_id, int auto_id) {
        logger.info("Finding like: " + user_id + " " + auto_id);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(GET_FROM_DB_BY_UI_AI)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, auto_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("Successfully found like: " + user_id + " " + auto_id);
            return mapper.mapRow(resultSet);
        }catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL= "SELECT * FROM likes";
    @Override
    public List<Like> findAll() {
        logger.info("Finding all likes");
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Like> likes = new ArrayList<>();
            while(resultSet.next()){
                likes.add(mapper.mapRow(resultSet));
            }
            logger.info("Successfully found all likes");
            return likes;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
    //language=sql
    private final static String GET_ALL_BY_USER_ID= "SELECT * FROM likes WHERE user_id = ?";
    public List<Like> findAll(int user_id) {
        logger.info("Finding all likes by user_id: " + user_id);
        try(Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_USER_ID)){
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Like> likes = new ArrayList<>();
            while(resultSet.next()){
                likes.add(mapper.mapRow(resultSet));
            }
            logger.info("Successfully found all likes");
            return likes;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
