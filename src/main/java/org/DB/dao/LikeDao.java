package org.DB.dao;

import org.DB.mappers.LikeMapper;
import org.models.Employee;
import org.models.Like;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LikeDao extends AbstractDao<Like> {
    private static final LikeMapper mapper = new LikeMapper();
    //language=sql
    private static final String ADD_TO_DB = "INSERT INTO likes (user_id, auto_id) VALUES (?, ?)";
    @Override
    public boolean save(Like obj) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(ADD_TO_DB)){
            preparedStatement.setInt(1, obj.getUserId());
            preparedStatement.setInt(2, obj.getAutoId());
            return preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String DELETE_FROM_DB = "DELETE FROM likes WHERE like_id = ?";
    @Override
    public boolean deleteById(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_FROM_DB)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String DELETE_FROM_DB_BY_UI_AI = "DELETE FROM likes WHERE user_id = ? AND auto_id = ?";

    public boolean delete(int user_id, int auto_id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_FROM_DB_BY_UI_AI)){
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, auto_id);
            return preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String GET_FROM_DB_BY_ID = "SELECT * FROM likes WHERE like_id = ?";
    @Override
    public Like findById(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_FROM_DB_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapper.mapRow(resultSet);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //language=sql
    private static final String GET_FROM_DB_BY_UI_AI = "SELECT * FROM likes WHERE user_id = ? AND auto_id = ?";
    public Like find(int user_id, int auto_id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_FROM_DB_BY_UI_AI)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, auto_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapper.mapRow(resultSet);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL= "SELECT * FROM likes";
    @Override
    public List<Like> findAll() {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Like> likes = new ArrayList<>();
            while(resultSet.next()){
                likes.add(mapper.mapRow(resultSet));
            }
            return likes;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    //language=sql
    private final static String GET_ALL_BY_USER_ID= "SELECT * FROM likes WHERE user_id = ?";
    public List<Like> findAll(int user_id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL_BY_USER_ID)){
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Like> likes = new ArrayList<>();
            while(resultSet.next()){
                likes.add(mapper.mapRow(resultSet));
            }
            return likes;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
