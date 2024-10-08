package org.DB;

import org.models.*;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.List;

public interface DB_helper {
    boolean addImageToThisAuto(InputStream is, int auto_id);

    int getEmpIdByName(String name);

    Employee getEmpById(int emp_id);

    void addImageToThisEmp(InputStream is, int emp_id);

    void deleteImageById(int image_id);

    int getImageIdFromThisAutoWithNumber(int auto_id, int number);

    List<byte[]> getImageFromThisAuto(int auto_id);

    byte[] getImageFromThisEmp(int emp_id);

    int getCountOfImageFromThisAuto(int auto_id);

    List<User> getAllUsers();

    List<Auto_model> getFilterAutoLike(String brand_id, String model, String sort, String user_id, int this_user_id, String city);

    List<Auto_model> getFilterAuto(String brand_id, String model, String sort, String user_id, String city);

    void updateAutoById_city(int auto_id, String city);

    void addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    List<User> getAllUsers(String str);

    boolean checkUser(String username);

    User getUser(String username);

    boolean checkPermission(String username, int auto_id);

    int getLastAutoFromThisUser(String username);

    void addReport(Report report);

    List<Report> getAllReports();

    void deleteReport(int rep_id);

    boolean checkUsersPassword(String username, String password);

    void addUserToDatabase(User user);

    void deleteEmpById(int emp_id);

    void deleteAutoById(int auto_id);

    void addAutoToDatabase(Auto_model auto);

    void addLikeToDatabase(int user_id, int auto_id);

    List<Integer> getAllLikes(int user_id);

    void deleteLike(int user_id, int auto_id);

    boolean checkLike(int user_id, int auto_id);

    void addBrandToDatabase(Brand brand);

    List<Auto_model> getAllAuto();

    List<Auto_model> getAutoByThisIds(List<Integer> ints);

    List<Auto_model> getAllAuto(String username);

    List<Brand> getAllBrands();

    Brand getBrandById(int id);

    User getUserById(int id);

    Auto_model getAutoById(int id);

    void changeStatusThisUser(int user_id, String status);

    void updateAutoById_brand(int auto_id, int brand_id);

    void updateAutoById_model(int auto_id, String model);

    void updateAutoById_year(int auto_id, int year);

    void updateAutoById_price(int auto_id, int price);

    void updateAutoById_mileage(int auto_id, int mileage);

    Brand getBrandByName(String brand_name);
}
