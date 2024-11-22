package org.DB.dao;

import org.DB.mappers.ImageMapper;
import org.models.Image;
import org.models.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ImageDao extends AbstractDao<Image> {
    private static final ImageMapper mapper = new ImageMapper();

    //language=sql
    private final static String ADD_TO_DB = "INSERT INTO auto_images(auto_id, image) VALUES (?,?)";
    @Override
    public boolean save(Image obj) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(ADD_TO_DB)) {
            preparedStatement.setInt(1, obj.getAuto_id());
            preparedStatement.setBlob(2, obj.getImage());
            return preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String DELETE_BY_ID = "DELETE FROM auto_images WHERE image_id = ?";
    @Override
    public boolean deleteById(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String FIND_BY_ID = "SELECT * FROM auto_images WHERE auto_id = ?";
    @Override
    public Image findById(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapper.mapRow(resultSet);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL = "SELECT * FROM auto_images";
    @Override
    public List<Image> findAll() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Image> images = new ArrayList<>();
            while(resultSet.next()){
                images.add(mapper.mapRow(resultSet));
            }
            return images;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL_BY_AUTO_ID = "SELECT * FROM auto_images WHERE auto_id = ?";
    public List<Image> findAll(int auto_id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL_BY_AUTO_ID)){
            preparedStatement.setInt(1, auto_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Image> images = new ArrayList<>();
            while(resultSet.next()){
                images.add(mapper.mapRow(resultSet));
            }
            return images;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
