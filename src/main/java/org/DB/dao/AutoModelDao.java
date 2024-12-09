package org.DB.dao;

import org.DB.Configuration;
import org.DB.mappers.AutoModelMapper;
import org.apache.logging.log4j.LogManager;
import org.models.AutoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutoModelDao extends AbstractDao<AutoModel> {


    public AutoModelDao(Configuration configuration) {
        super(configuration);
        logger = LogManager.getLogger(AutoModelDao.class);
        mapper = new AutoModelMapper();
    }

    private final AutoModelMapper mapper;
    //language=sql
    private final static String ADD_TO_DATABASE = "INSERT INTO auto(auto_brand_id, user_id, auto_model, year, price, mileage, city, description) VALUES(?,?,?,?,?,?,?,?)";
    @Override
    public boolean save(AutoModel auto) {
        int result = 0;
        logger.info("Saving " + auto + " to database" );
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_DATABASE)){
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
        try (Connection connection = getConnection();PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)){
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
    public AutoModel findById(int id) {
        logger.info("Finding auto model with id " + id );
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)){
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
    public List<AutoModel> findAll() {
        logger.info("Finding all auto models");
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AutoModel> autoModels = new ArrayList<>();
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
    public List<AutoModel> getAllByIds(List<Integer> ids) {
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
            return new ArrayList<AutoModel>();
        }
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString())) {
            List<AutoModel> autoModels = new ArrayList<>();
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
    public List<AutoModel> findAll(int user_id) {
        logger.info("Finding all auto models from user " + user_id);
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_USER_ID)){
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AutoModel> autoModels = new ArrayList<>();
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
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_BRAND)){
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
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_MODEL)){
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
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_YEAR)){
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
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRICE)){
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
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_MILEAGE)){
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
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CITY)){
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
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_DESC)){
            statement.setString(1,description);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
            logger.info("Successfully updated auto model with id " + auto_id );
        }catch (Exception e){logger.error(e);
            throw new RuntimeException(e);}
    }
    //language = sql
    private final static String GET_FILTER = "SELECT * FROM" + " (SELECT auto.auto_id, auto_brand_id, auto.user_id, auto_model, year, price, mileage, city" +
            " FROM auto JOIN likes USING(auto_id) WHERE likes.user_id = ?) as abcd " + "WHERE auto_model LIKE ? AND city LIKE ?";
    public List<AutoModel> getFilterAutoLike(String brand_id, String model, String sort, String user_id, int this_user_id, String city){
        String result = createStr(GET_FILTER, brand_id, model, sort, user_id);
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(result)) {
            preparedStatement.setInt(1, this_user_id);
            preparedStatement.setString(2, "%" + model + "%");
            preparedStatement.setString(3, "%" + city + "%");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<AutoModel> autoModels = new ArrayList<>();
            while (rs.next()) {
                autoModels.add(mapper.mapRow(rs));
            }
            return autoModels;
        }catch (Exception e){logger.error(e);
        throw new RuntimeException(e);}
    }
    //language = sql
    private final static String GET_FILTER_2 = "SELECT * FROM auto WHERE auto_model LIKE ? AND city LIKE ?";
    public  List<AutoModel> getFilterAuto(String brand_id, String model, String sort, String user_id, String city){
        String result = createStr(GET_FILTER_2, brand_id, model, sort, user_id);
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(result)) {
            preparedStatement.setString(1, "%" + model + "%");
            preparedStatement.setString(2, "%" + city + "%");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<AutoModel> autoModels = new ArrayList<>();
            while (rs.next()) {
                autoModels.add(mapper.mapRow(rs));
            }
            return autoModels;
        }catch (Exception e){logger.error(e);
            throw new RuntimeException(e);}
    }

    private  String createStr(String str1, String brand_id, String model, String sort, String user_id){
        String str = str1 + "";
        if (!brand_id.equals("0")){
            str += "AND auto_brand_id = " + brand_id + " ";
        }
        if (user_id != null && !user_id.isEmpty()){
            str += "AND user_id = " + user_id + " ";
        }
        switch (sort){
            case "priceUp":
                str += "ORDER BY price";
                break;
            case "priceDown":
                str += "ORDER BY price DESC";
                break;
            case "yearUp":
                str += "ORDER BY year";
                break;
            case "yearDown":
                str += "ORDER BY year DESC";
                break;
            case "mileageUp":
                str += "ORDER BY mileage";
                break;
            case "mileageDown":
                str += "ORDER BY mileage DESC";
                break;
            case "cityUp":
                str += "Order BY city";
                break;
            case "cityDown":
                str += "Order BY city DESC";
                break;
        }
        return str;
    }
}
