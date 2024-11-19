package org.DB.dao;

import org.DB.mappers.AutoModelMapper;
import org.models.Auto_model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutoModelDao extends AbstractDao<Auto_model> {
    private AutoModelMapper mapper = new AutoModelMapper();
    //language=sql
    private final static String ADD_TO_DATABASE = "INSERT INTO auto(auto_brand_id, user_id, auto_model, year, price, mileage, city, description) VALUES(?,?,?,?,?,?,?,?)";
    @Override
    public boolean save(Auto_model auto) {
        int result = 0;
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
            throw new RuntimeException(e);
        }
        return result > 0;
    }
    //language=sql
    private final static String DELETE_BY_ID= "DELETE FROM auto WHERE auto_id = ?";
    @Override
    public boolean deleteById(int auto_id) {
        int result = 0;
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_BY_ID)){
            ps.setInt(1, auto_id);
            result = ps.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result > 0;
    }
    //language=sql
    private final static String GET_BY_ID= "SELECT * FROM auto WHERE auto_id = ?";
    @Override
    public Auto_model findById(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_BY_ID)){
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapper.mapRow(resultSet);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    //language=sql
    private final static String GET_ALL= "SELECT * FROM auto";
    @Override
    public List<Auto_model> findAll() {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Auto_model> autoModels = new ArrayList<>();
            while(resultSet.next()){
                autoModels.add(mapper.mapRow(resultSet));
            }
            return autoModels;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL_BY_IDS= "SELECT * FROM auto WHERE auto_id IN ";
    public List<Auto_model> getAllByIds(List<Integer> ids) {
        StringBuilder stringBuilder = new StringBuilder(GET_ALL_BY_IDS);
        for(int i = 0; i<ids.size(); i++){
            if(i == 0) stringBuilder.append("(");
            stringBuilder.append(ids.get(i));
            if(i != ids.size()-1) stringBuilder.append(",");
            else{
                stringBuilder.append(")");
            }
        }
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(stringBuilder.toString())) {
            List<Auto_model> autoModels = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                autoModels.add(mapper.mapRow(resultSet));
            }
            return autoModels;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //language=sql
    private final static String GET_ALL_BY_USER_ID= "SELECT * FROM auto WHERE user_id = ?";
    public List<Auto_model> findAll(int user_id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL_BY_USER_ID)){
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Auto_model> autoModels = new ArrayList<>();
            while(resultSet.next()){
                autoModels.add(mapper.mapRow(resultSet));
            }
            return autoModels;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    //language=sql
    private final static String UPDATE_BRAND= "UPDATE auto SET auto_brand_id = ? WHERE auto_id = ?";
    public  void updateAutoById_brand(int auto_id, int brand_id){
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_BRAND)){
            statement.setInt(1, brand_id);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    //language=sql
    private final static String UPDATE_MODEL= "UPDATE auto SET auto_model = ? WHERE auto_id = ?";
    public  void updateAutoById_model(int auto_id, String model){
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_MODEL)){
            statement.setString(1, model);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    //language=sql
    private final static String UPDATE_YEAR= "UPDATE auto SET year = ? WHERE auto_id = ?";
    public  void updateAutoById_year(int auto_id, int year){
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_BRAND)){
            statement.setInt(1, year);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    //language=sql
    private final static String UPDATE_PRICE= "UPDATE auto SET price = ? WHERE auto_id = ?";
    public  void updateAutoById_price(int auto_id, int price){
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_BRAND)){
            statement.setInt(1, price);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    //language=sql
    private final static String UPDATE_MILEAGE= "UPDATE auto SET mileage = ? WHERE auto_id = ?";
    public  void updateAutoById_mileage(int auto_id, int mileage){
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_BRAND)){
            statement.setInt(1, mileage);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    //language=sql
    private final static String UPDATE_CITY= "UPDATE auto SET city = ? WHERE auto_id = ?";
    public  void updateAutoById_city(int auto_id, String city){
        try(PreparedStatement statement = getConnection().prepareStatement(UPDATE_MODEL)){
            statement.setString(1, city);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
}
