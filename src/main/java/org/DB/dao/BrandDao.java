package org.DB.dao;

import org.DB.mappers.BrandMapper;
import org.apache.logging.log4j.LogManager;
import org.models.Brand;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BrandDao extends AbstractDao<Brand> {
    static{
        logger = LogManager.getLogger(BrandDao.class);
    }
    private static final BrandMapper mapper = new BrandMapper();

    //language=sql
    private static final String ADD_TO_DB = "INSERT INTO brand(auto_brand_name, auto_brand_country) VALUES (?,?)";
    @Override
    public boolean save(Brand obj) {
        logger.info("Saving " + obj.toString());
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(ADD_TO_DB)) {
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getCountry());
            int rezult = preparedStatement.executeUpdate();
            logger.info("Successfully saved " + obj.toString());
            return rezult > 0;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String DELETE_FROM_DB = "DELETE FROM brand WHERE auto_brand_id = ?";
    @Override
    public boolean deleteById(int id) {
        logger.info("Deleting brand " + id);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_FROM_DB)) {
            preparedStatement.setInt(1, id);
            int rezult = preparedStatement.executeUpdate();
            logger.info("Successfully brand deleted " + id);
            return rezult > 0;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String FIND_BY_ID = "SELECT * FROM brand WHERE auto_brand_id = ?";
    @Override
    public Brand findById(int id) {
        logger.info("Finding brand " + id);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("Successfully found brand" + id);
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
    //language=sql
    private static final String FIND_BY_NAME = "SELECT * FROM brand WHERE auto_brand_name = ?";
    public Brand findByName(String name) {
        logger.info("Finding brand " + name);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_BY_NAME)){
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("Successfully found brand" + name);
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private static final String GET_ALL = "SELECT * FROM brand";
    @Override
    public List<Brand> findAll() {
        logger.info("Finding brands");
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Brand> brands = new ArrayList<>();
            while(resultSet.next()){
                brands.add(mapper.mapRow(resultSet));
            }
            logger.info("Successfully found " + brands.size() + " brands");
            return brands;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
