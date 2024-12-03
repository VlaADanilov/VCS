package org.DB.dao;

import org.DB.mappers.AutoModelMapper;
import org.apache.logging.log4j.LogManager;
import org.models.Auto_model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutoModelDao extends AbstractDao<Auto_model> {
    static{
        logger = LogManager.getLogger(AutoModelDao.class);
    }
    private static final AutoModelMapper mapper = new AutoModelMapper();
    //language=sql
    private final static String ADD_TO_DATABASE = "INSERT INTO auto(auto_brand_id, user_id, auto_model, year, price, mileage, city, description) VALUES(?,?,?,?,?,?,?,?)";
    @Override
    public boolean save(Auto_model auto) {
        int result = 0;
        logger.info("Saving " + auto + " to database" );
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(ADD_TO_DATABASE)){
            preparedStatement.setInt(1, auto.getBrand_id());
            preparedStatement.setInt(2, auto.getUser_id());
            preparedStatement.setString(3, auto.getModel());
            preparedStatement.setInt(4, auto.getYear());
            preparedStatement.setInt(5, auto.getPrice());
            preparedStatement.setInt(6, auto.getMileage());
            preparedStatement.setString(7, auto.getCity());
            preparedStatement.setString(8, auto.getDescription());

            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.info("Successfully saved " + auto + " to database");
        return result > 0;
    }
    //language=sql
    private final static String DELETE_BY_ID= "DELETE FROM auto WHERE auto_id = ?";
    @Override
    public boolean deleteById(int auto_id) {
        int result = 0;
        logger.info("Deleting auto model with id " + auto_id );
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_BY_ID)){
            ps.setInt(1, auto_id);
            result = ps.executeUpdate();
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.info("Successfully deleted auto model with id " + auto_id );
        return result > 0;
    }
    //language=sql
    private final static String GET_BY_ID= "SELECT * FROM auto WHERE auto_id = ?";
    @Override
    public Auto_model findById(int id) {
        logger.info("Finding auto model with id " + id );
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            logger.info("Successfully found auto model with id " + id );
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
    //language=sql
    private final static String GET_ALL= "SELECT * FROM auto";
    @Override
    public List<Auto_model> findAll() {
        logger.info("Finding all auto models");
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Auto_model> autoModels = new ArrayList<>();
            while(resultSet.next()){
                autoModels.add(mapper.mapRow(resultSet));
            }
            logger.info("Successfully found all auto models");
            return autoModels;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL_BY_IDS= "SELECT * FROM auto WHERE auto_id IN ";
    public List<Auto_model> getAllByIds(List<Integer> ids) {
        logger.info("Finding all auto models by ids");
        StringBuilder stringBuilder = new StringBuilder(GET_ALL_BY_IDS);
        for(int i = 0; i<ids.size(); i++){
            if(i == 0) stringBuilder.append("(");
            stringBuilder.append(ids.get(i));
            if(i != ids.size()-1) stringBuilder.append(",");
            else{
                stringBuilder.append(")");
            }
        }
        if (stringBuilder.toString().equals(GET_ALL_BY_IDS)){
            logger.info("Successfully found all auto models by ids");
            return new ArrayList<Auto_model>();
        }
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(stringBuilder.toString())) {
            List<Auto_model> autoModels = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                autoModels.add(mapper.mapRow(resultSet));
            }
            logger.info("Successfully found all auto models by ids");
            return autoModels;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL_BY_USER_ID= "SELECT * FROM auto WHERE user_id = ?";
    public List<Auto_model> findAll(int user_id) {
        logger.info("Finding all auto models from user " + user_id);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL_BY_USER_ID)){
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Auto_model> autoModels = new ArrayList<>();
            while(resultSet.next()){
                autoModels.add(mapper.mapRow(resultSet));
            }
            logger.info("Successfully found all auto models from user " + user_id);
            return autoModels;
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
    //language=sql
    private final static String UPDATE_BRAND= "UPDATE auto SET auto_brand_id = ? WHERE auto_id = ?";
    public  void updateAutoById_brand(int auto_id, int brand_id){
        logger.info("Updating auto model with id " + auto_id );
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_BRAND)){
            statement.setInt(1, brand_id);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
            logger.info("Successfully updated auto model with id " + auto_id );
        }catch (Exception e){logger.error(e);
            throw new RuntimeException(e);}
    }
    //language=sql
    private final static String UPDATE_MODEL= "UPDATE auto SET auto_model = ? WHERE auto_id = ?";
    public  void updateAutoById_model(int auto_id, String model){
        logger.info("Updating auto model with id " + auto_id );
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_MODEL)){
            statement.setString(1, model);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
            logger.info("Successfully updated auto model with id " + auto_id );
        }catch (Exception e){logger.error(e);
            throw new RuntimeException(e);}
    }

    //language=sql
    private final static String UPDATE_YEAR= "UPDATE auto SET year = ? WHERE auto_id = ?";
    public  void updateAutoById_year(int auto_id, int year){
        logger.info("Updating auto model with id " + auto_id );
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_YEAR)){
            statement.setInt(1, year);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
            logger.info("Successfully updated auto model with id " + auto_id );
        }catch (Exception e){logger.error(e);
            throw new RuntimeException(e);}
    }

    //language=sql
    private final static String UPDATE_PRICE= "UPDATE auto SET price = ? WHERE auto_id = ?";
    public  void updateAutoById_price(int auto_id, int price){
        logger.info("Updating auto model with id " + auto_id );
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_PRICE)){
            statement.setInt(1, price);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
            logger.info("Successfully updated auto model with id " + auto_id );
        }catch (Exception e){logger.error(e);
            throw new RuntimeException(e);}
    }

    //language=sql
    private final static String UPDATE_MILEAGE= "UPDATE auto SET mileage = ? WHERE auto_id = ?";
    public  void updateAutoById_mileage(int auto_id, int mileage){
        logger.info("Updating auto model with id " + auto_id );
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_MILEAGE)){
            statement.setInt(1, mileage);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
            logger.info("Successfully updated auto model with id " + auto_id );
        }catch (Exception e){logger.error(e);
            throw new RuntimeException(e);}
    }

    //language=sql
    private final static String UPDATE_CITY= "UPDATE auto SET city = ? WHERE auto_id = ?";
    public  void updateAutoById_city(int auto_id, String city){
        logger.info("Updating auto model with id " + auto_id );
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_CITY)){
            statement.setString(1, city);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
            logger.info("Successfully updated auto model with id " + auto_id );
        }catch (Exception e){logger.error(e);
            throw new RuntimeException(e);}
    }
    //language=sql
    private final static String UPDATE_DESC= "UPDATE auto SET description = ? WHERE auto_id = ?";
    public void updateAutoById_description(int auto_id, String description) {
        logger.info("Updating auto model with id " + auto_id );
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_DESC)){
            statement.setString(1,description);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
            logger.info("Successfully updated auto model with id " + auto_id );
        }catch (Exception e){logger.error(e);
            throw new RuntimeException(e);}
    }
}
