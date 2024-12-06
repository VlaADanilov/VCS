package org.DB;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.models.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySQLHelper implements DBHelper {
    private final static Logger logger = LogManager.getLogger(MySQLHelper.class);
    public boolean deleteUserByName(String name){
        return Configuration.getUserDao().deleteByName(name);
    }

    public  boolean addImageToThisAuto(InputStream inputStream,String is, int auto_id){
        int i = 0;
        File file = new File(path + "\\" + is + i);
        while(file.exists()){
            i++;
            file = new File(path + "\\" + is + i);
        }
        try{
            FileOutputStream fis = new FileOutputStream(file);
            fis.write(inputStream.readAllBytes());
            fis.close();
        }catch (Exception e){
            logger.error(e);
            throw new RuntimeException(e);
        }
        return Configuration.getImageDao().save(new Image(is + i, auto_id));
    }

    public  int getEmpIdByName(String name){
        return Configuration.getEmployerDao().findByName(name).getId();
    }

    public Employee getEmpById(int emp_id){
        return Configuration.getEmployerDao().findById(emp_id);
    }

    public boolean addImageToThisEmp(InputStream is, int emp_id){
        return Configuration.getEmployerDao().updateImage(is, emp_id);
    }

    public boolean deleteImageById(int image_id){
        try{
            Image image = Configuration.getImageDao().findById(image_id);
            File file = new File(path+"\\"+image.getImage());
            System.out.println(file.getAbsolutePath() + " " + file.exists());
            file.delete();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
            throw new RuntimeException(e);
        }
        return Configuration.getImageDao().deleteById(image_id);
    }

    public  int getImageIdFromThisAutoWithNumber(int auto_id, int number){

        int id = Configuration.getImageDao().findAll(auto_id).get(number-1).getId();
        return id;
    }


    private final static String path = "C:\\КФУ\\ОРИС\\1 семестровка\\картинки автомобилей";
    public  List<byte[]> getImageFromThisAuto(int auto_id){
        List<Image> list = Configuration.getImageDao().findAll(auto_id);
        List<byte[]> result = new ArrayList<>();
        for(Image image : list){
            try {
                File file = new File(path + "\\" + image.getImage());
                FileInputStream fis = new FileInputStream(file);
                result.add(fis.readAllBytes());
                fis.close();
            } catch (IOException e) {
                logger.error(e);
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

    public  List<AutoModel> getFilterAutoLike(String brand_id, String model, String sort, String user_id, int this_user_id, String city){
        return Configuration.getAutoModelDao().getFilterAutoLike(brand_id, model, sort, user_id, this_user_id, city);
    }

    public  List<AutoModel> getFilterAuto(String brand_id, String model, String sort, String user_id, String city){
        return Configuration.getAutoModelDao().getFilterAuto(brand_id, model, sort, user_id, city);
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
            logger.error(e);
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

    public boolean addAutoToDatabase(AutoModel auto){
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

    public  List<AutoModel> getAllAuto(){
        return Configuration.getAutoModelDao().findAll();
    }

    public  List<AutoModel> getAutoByThisIds(List<Integer> ints){
        return Configuration.getAutoModelDao().getAllByIds(ints);
    }

    public  List<AutoModel> getAllAuto(String username){
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

    public AutoModel getAutoById(int id){
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