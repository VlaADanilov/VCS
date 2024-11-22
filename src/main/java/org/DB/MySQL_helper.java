package org.DB;

import org.models.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySQL_helper implements DB_helper{
    public boolean deleteUserByName(String name){
        return Configuration.getUserDao().deleteByName(name);
    }

    public  boolean addImageToThisAuto(InputStream is, int auto_id){

        return Configuration.getImageDao().save(new Image(is, auto_id));
    }

    public  int getEmpIdByName(String name){
        return Configuration.getEmployerDao().findByName(name).getId();
    }

    public  Employee getEmpById(int emp_id){
        return Configuration.getEmployerDao().findById(emp_id);
    }

    public boolean addImageToThisEmp(InputStream is, int emp_id){
        return Configuration.getEmployerDao().updateImage(is, emp_id);
    }

    public boolean deleteImageById(int image_id){
        return Configuration.getImageDao().deleteById(image_id);
    }

    public  int getImageIdFromThisAutoWithNumber(int auto_id, int number){
//        try(Statement statement = dbConnection.createStatement()){
//            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM auto_images WHERE auto_id=?");
//            ps.setInt(1, auto_id);
//            ResultSet rs = ps.executeQuery();
//            int count = 0;
//            while(rs.next()){
//                count += 1;
//                if (count == number) return rs.getInt("image_id");
//            }
//        }catch (Exception e){e.printStackTrace();}
//        return -1;
        return Configuration.getImageDao().findAll(auto_id).get(number-1).getId();
    }

    public  List<byte[]> getImageFromThisAuto(int auto_id){
        List<Image> list = Configuration.getImageDao().findAll(auto_id);
        List<byte[]> result = new ArrayList<>();
        for(Image image : list){
            try {
                result.add(image.getImage().readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public  byte[] getImageFromThisEmp(int emp_id){
        return Configuration.getEmployerDao().getImage(emp_id);
    }

    public  int getCountOfImageFromThisAuto(int auto_id){
        return Configuration.getImageDao().findAll(auto_id).size();
    }

    public  List<User> getAllUsers(){
        return Configuration.getUserDao().findAll();
    }

    public  List<Auto_model> getFilterAutoLike(String brand_id, String model, String sort, String user_id, int this_user_id, String city){
        List<Auto_model> list = new ArrayList<>();
        try{
            String str = "SELECT * FROM" + " (SELECT auto.auto_id, auto_brand_id, auto.user_id, auto_model, year, price, mileage, city" +
                    " FROM auto JOIN likes USING(auto_id) WHERE likes.user_id = ?) as abcd " + "WHERE auto_model LIKE ? AND city LIKE ?";
            String result = createStr(str, brand_id, model, sort, user_id);
            PreparedStatement pst = Configuration.getConnection().prepareStatement(result);
            pst.setInt(1, this_user_id);
            pst.setString(2, "%" + model + "%");
            pst.setString(3, "%" + city + "%");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Auto_model auto = new Auto_model();
                auto.setId(rs.getInt("auto_id"));
                auto.setBrand_id(rs.getInt("auto_brand_id"));
                auto.setUser_id(rs.getInt("user_id"));
                auto.setModel(rs.getString("auto_model"));
                auto.setPrice(rs.getInt("price"));
                auto.setYear(rs.getInt("year"));
                auto.setMileage(rs.getInt("mileage"));
                auto.setCity(rs.getString("city"));
                list.add(auto);
            }
        }catch (Exception e){e.printStackTrace();}
        return list;
    }

    public  List<Auto_model> getFilterAuto(String brand_id, String model, String sort, String user_id, String city){
        List<Auto_model> list = new ArrayList<>();
        try{
            String str = "SELECT * FROM auto WHERE auto_model LIKE ? AND city LIKE ?";
            String result = createStr(str, brand_id, model, sort, user_id);

            PreparedStatement pst = Configuration.getConnection().prepareStatement(result);
            pst.setString(1, "%" + model + "%");
            pst.setString(2, "%" + city + "%");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Auto_model auto = new Auto_model();
                auto.setId(rs.getInt("auto_id"));
                auto.setBrand_id(rs.getInt("auto_brand_id"));
                auto.setUser_id(rs.getInt("user_id"));
                auto.setModel(rs.getString("auto_model"));
                auto.setPrice(rs.getInt("price"));
                auto.setYear(rs.getInt("year"));
                auto.setMileage(rs.getInt("mileage"));
                auto.setCity(rs.getString("city"));
                list.add(auto);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return list;
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

    public boolean addEmployee(Employee employee){
        return Configuration.getEmployerDao().save(employee);
    }


    public  List<Employee> getAllEmployees(){
        List<Employee> list = Configuration.getEmployerDao().findAll();
        list.forEach((employee -> employee.setPhone(getUserById(employee.getUser_id()).getPhone())));
        return list;
    }

    public  List<User> getAllUsers(String str){
        return Configuration.getUserDao().findAllLike(str);
    }

    public  boolean checkUser(String username){
        try{
            Configuration.getUserDao().findById(getUser(username).getId());
            return true;
        }catch (Exception e){return false;}
    }

    public  User getUser(String username){
        try{
            return Configuration.getUserDao().findByName(username);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean checkPermission(String username, int auto_id) {
        try(PreparedStatement ps = Configuration.getConnection().prepareStatement("SELECT user_name, auto_id FROM auto JOIN user USING (user_id) " +
                "WHERE user_name = ? AND auto_id = ?");){
            ps.setString(1, username);
            ps.setInt(2, auto_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return true;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public int getLastAutoFromThisUser(String username) {
        try (PreparedStatement ps = Configuration.getConnection().prepareStatement("SELECT auto_id FROM auto JOIN user " +
                "USING (user_id) WHERE user_name = ? ORDER BY 1 DESC")){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("auto_id");
            }
        }catch (Exception e){e.printStackTrace();}
        return -1;
    }

    @Override
    public boolean addReport(Report report) {
        return Configuration.getReportDao().save(report);
    }

    @Override
    public List<Report> getAllReports() {
        return Configuration.getReportDao().findAll();
    }

    @Override
    public boolean deleteReport(int rep_id) {
        return Configuration.getReportDao().deleteById(rep_id);
    }

    public  boolean checkUsersPassword(String username, String password){
        if(!checkUser(username)){
            return false;
        }
        return Configuration.getUserDao().checkPassword(username, password);
    }

    public boolean addUserToDatabase(User user){
        return Configuration.getUserDao().save(user);
    }

    public boolean deleteEmpById(int emp_id){
        return Configuration.getEmployerDao().deleteById(emp_id);
    }



    public boolean deleteAutoById(int auto_id){
        return Configuration.getAutoModelDao().deleteById(auto_id);
    }

    public boolean addAutoToDatabase(Auto_model auto){
        return Configuration.getAutoModelDao().save(auto);
    }

    public Brand getBrandByName(String brand_name){
        return Configuration.getBrandDao().findByName(brand_name);
    }

    public boolean addLikeToDatabase(int user_id, int auto_id){
        return Configuration.getLikeDao().save(new Like(user_id,auto_id));
    }

    public  List<Integer> getAllLikes(int user_id){
        return Configuration.getLikeDao().findAll(user_id).stream().map(Like::getAutoId).collect(Collectors.toList());
    }

    public boolean deleteLike(int user_id, int auto_id){
        return Configuration.getLikeDao().delete(user_id,auto_id);
    }

    public  boolean checkLike(int user_id, int auto_id){
        try{
            Configuration.getLikeDao().find(user_id, auto_id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean addBrandToDatabase(Brand brand){
        return Configuration.getBrandDao().save(brand);
    }

    public  List<Auto_model> getAllAuto(){
        return Configuration.getAutoModelDao().findAll();
    }

    public  List<Auto_model> getAutoByThisIds(List<Integer> ints){
        return Configuration.getAutoModelDao().getAllByIds(ints);
    }

    public  List<Auto_model> getAllAuto(String username){
        int user_id = getUser(username).getId();
        return Configuration.getAutoModelDao().findAll(user_id);
    }

    public  List<Brand> getAllBrands(){
        return Configuration.getBrandDao().findAll();
    }

    public  Brand getBrandById(int id){
        return Configuration.getBrandDao().findById(id);
    }
    public  User getUserById(int id){
        return Configuration.getUserDao().findById(id);
    }

    public  Auto_model getAutoById(int id){
        return Configuration.getAutoModelDao().findById(id);
    }

    public boolean changeStatusThisUser(int user_id, String status){
        return Configuration.getUserDao().updateStatus(status, user_id);
    }

    public  void updateAutoById_brand(int auto_id, int brand_id){
        Configuration.getAutoModelDao().updateAutoById_brand(auto_id, brand_id);
    }
    public  void updateAutoById_model(int auto_id, String model){
        Configuration.getAutoModelDao().updateAutoById_model(auto_id, model);
    }
    public  void updateAutoById_year(int auto_id, int year){
        Configuration.getAutoModelDao().updateAutoById_year(auto_id, year);
    }
    public  void updateAutoById_price(int auto_id, int price){
        Configuration.getAutoModelDao().updateAutoById_price(auto_id, price);
    }
    public  void updateAutoById_mileage(int auto_id, int mileage){
        Configuration.getAutoModelDao().updateAutoById_mileage(auto_id, mileage);
    }
    public  void updateAutoById_city(int auto_id, String city){
        Configuration.getAutoModelDao().updateAutoById_city(auto_id, city);
    }

    @Override
    public void updateAutoById_description(int auto_id, String description) {
        Configuration.getAutoModelDao().updateAutoById_description(auto_id, description);
    }
}
