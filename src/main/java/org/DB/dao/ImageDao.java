package org.DB.dao;

import org.DB.mappers.ImageMapper;
import org.apache.logging.log4j.LogManager;
import org.models.Image;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ImageDao extends AbstractDao<Image> {
    static{
        logger = LogManager.getLogger(ImageDao.class);
    }
    private static final ImageMapper mapper = new ImageMapper();

    //language=sql
    private final static String ADD_TO_DB = "INSERT INTO auto_images(auto_id, image) VALUES (?,?)";
    @Override
    public boolean save(Image obj) {
        logger.info("saving autoimage");
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(ADD_TO_DB)) {
            preparedStatement.setInt(1, obj.getAuto_id());
            preparedStatement.setString(2, obj.getImage());
            int result = preparedStatement.executeUpdate();
            logger.info("autoimage saved to database");
            return result > 0;
        }catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String DELETE_BY_ID = "DELETE FROM auto_images WHERE image_id = ?";
    @Override
    public boolean deleteById(int id) {
        logger.info("deleting autoimage");
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            logger.info("deleted autoimage");
            return result > 0;
        }catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String FIND_BY_ID = "SELECT * FROM auto_images WHERE image_id = ?";
    @Override
    public Image findById(int id) {
        logger.info("finding autoimage");
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("found autoimage");
            return mapper.mapRow(resultSet);
        }catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL = "SELECT * FROM auto_images";
    @Override
    public List<Image> findAll() {
        logger.info("finding all autoimages");
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Image> images = new ArrayList<>();
            while(resultSet.next()){
                images.add(mapper.mapRow(resultSet));
            }
            logger.info("found all autoimages");
            return images;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL_BY_AUTO_ID = "SELECT * FROM auto_images WHERE auto_id = ?";
    public List<Image> findAll(int auto_id) {
        logger.info("finding all autoimages by auto_id");
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL_BY_AUTO_ID)){
            preparedStatement.setInt(1, auto_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Image> images = new ArrayList<>();
            while(resultSet.next()){
                images.add(mapper.mapRow(resultSet));
            }
            logger.info("found all autoimages by auto_id");
            return images;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
