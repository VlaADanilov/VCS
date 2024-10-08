package org.DB;

import org.models.*;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQL_helper implements DB_helper{
    private final String url = "jdbc:mysql://localhost:3306/auto_base";
    private Connection dbConnection = null;

    
    public MySQL_helper(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            dbConnection = DriverManager.getConnection(url, "root", "root");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public  boolean addImageToThisAuto(InputStream is, int auto_id){

        //нужно сделать проверку, что существует такое auto_id
        try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO auto_images VALUES(NULL,?,?)")){
            if(is.available() == 0) return false;
            statement.setInt(1, auto_id);
            statement.setBlob(2, is);
            statement.executeUpdate();
            System.out.println("Image added successfully");
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public  int getEmpIdByName(String name){
        try{
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM employee");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("employee_name").equals(name)){
                    return rs.getInt("employee_id");
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return -1;
    }

    public  Employee getEmpById(int emp_id){
        try{
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM employee WHERE employee_id=?");
            ps.setInt(1, emp_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Employee emp = new Employee();
                emp.setId(rs.getInt("employee_id"));
                emp.setName(rs.getString("employee_name"));
                emp.setDescription(rs.getString("employee_description"));
                emp.setProfession(rs.getString("employee_profession"));
                emp.setUser_id(rs.getInt("user_id"));
                return emp;
            }
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    public  void addImageToThisEmp(InputStream is, int emp_id){

        //нужно сделать проверку, что существует такое auto_id
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE employee SET image = ? WHERE employee_id = ?")){
            if(is.available() == 0) return;
            statement.setInt(2, emp_id);
            statement.setBlob(1, is);
            statement.executeUpdate();
            System.out.println("Image added successfully");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public  void deleteImageById(int image_id){
        try (PreparedStatement ps = dbConnection.prepareStatement("DELETE FROM auto_images WHERE image_id = ?")){
            ps.setInt(1, image_id);
            ps.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public  int getImageIdFromThisAutoWithNumber(int auto_id, int number){
        try(Statement statement = dbConnection.createStatement()){
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM auto_images WHERE auto_id=?");
            ps.setInt(1, auto_id);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while(rs.next()){
                count += 1;
                if (count == number) return rs.getInt("image_id");
            }
        }catch (Exception e){e.printStackTrace();}
        return -1;
    }

    public  List<byte[]> getImageFromThisAuto(int auto_id){
        List<byte[]> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()){
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM auto_images WHERE auto_id=?");
            ps.setInt(1, auto_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Blob blob = rs.getBlob("image");
                list.add(blob.getBytes(1, (int)blob.length()));
            }
        }catch (Exception e){e.printStackTrace();}
        return list;
    }

    public  byte[] getImageFromThisEmp(int emp_id){
        byte[] list = null;
        try(Statement statement = dbConnection.createStatement()){
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM employee WHERE employee_id=?");
            ps.setInt(1, emp_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Blob blob = rs.getBlob("image");
                return blob.getBytes(1, (int)blob.length());
            }
        }catch (Exception e){e.printStackTrace();}
        return list;
    }

    public  int getCountOfImageFromThisAuto(int auto_id){
        int count = 0;
        try(Statement statement = dbConnection.createStatement()){
            PreparedStatement ps = dbConnection.prepareStatement("SELECT COUNT(*) FROM auto_images WHERE auto_id=?");
            ps.setInt(1, auto_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }
        }catch (Exception e){e.printStackTrace();}
        return count;
    }

    public  List<User> getAllUsers(){
        List<User> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM user");
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                user.setPassword(rs.getString("user_password"));
                user.setStatus(rs.getString("user_status"));
                user.setPhone(rs.getString("user_phone"));
                list.add(user);
            }
        }catch (Exception e){e.printStackTrace();}
        return list;
    }

    public  List<Auto_model> getFilterAutoLike(String brand_id, String model, String sort, String user_id, int this_user_id, String city){
        List<Auto_model> list = new ArrayList<>();
        try{
            String str = "SELECT * FROM" + " (SELECT auto.auto_id, auto_brand_id, auto.user_id, auto_model, year, price, mileage, city" +
                    " FROM auto JOIN likes USING(auto_id) WHERE likes.user_id = ?) as abcd " + "WHERE auto_model LIKE ? AND city LIKE ?";
            String result = createStr(str, brand_id, model, sort, user_id);
            System.out.println(result);
            PreparedStatement pst = dbConnection.prepareStatement(result);
            pst.setInt(1, this_user_id);
            pst.setString(2, "%" + model + "%");
            pst.setString(3, "%" + city + "%");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Auto_model auto = new Auto_model();
                auto.setId(rs.getInt("auto_id"));
                auto.setBrand(getBrandById(rs.getInt("auto_brand_id")).getName());
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

            PreparedStatement pst = dbConnection.prepareStatement(result);
            pst.setString(1, "%" + model + "%");
            pst.setString(2, "%" + city + "%");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Auto_model auto = new Auto_model();
                auto.setId(rs.getInt("auto_id"));
                auto.setBrand(getBrandById(rs.getInt("auto_brand_id")).getName());
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

    public  void addEmployee(Employee employee){
        try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO employee VALUES(NULL,?,?,?,?,NULL)")){
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getProfession());
            statement.setString(3, employee.getDescription());
            statement.setInt(4, employee.getUser_id());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public  List<Employee> getAllEmployees(){
        List<Employee> list = new ArrayList<>();
        try{
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM employee JOIN user USING(user_id)");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (rs.getBlob("image") == null || rs.getBlob("image").length() == 0){
                    deleteEmpById(rs.getInt("employee_id"));
                    continue;
                }
                Employee employee = new Employee();
                employee.setId(rs.getInt("employee_id"));
                employee.setName(rs.getString("employee_name"));
                employee.setProfession(rs.getString("employee_profession"));
                employee.setDescription(rs.getString("employee_description"));
                employee.setPhone(rs.getString("user_phone"));
                employee.setUser_id(rs.getInt("user_id"));
                list.add(employee);
            }
        }catch (Exception e){e.printStackTrace();}
        return list;

    }

    public  List<User> getAllUsers(String str){
        List<User> list = new ArrayList<>();
        try{
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM user WHERE user_name LIKE ?");
            ps.setString(1, "%"+str+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                user.setPassword(rs.getString("user_password"));
                user.setStatus(rs.getString("user_status"));
                user.setPhone(rs.getString("user_phone"));
                list.add(user);
            }
        }catch (Exception e){e.printStackTrace();}
        return list;

    }

    public  boolean checkUser(String username){
        try(Statement statement = dbConnection.createStatement()){
            PreparedStatement ps = dbConnection.prepareStatement("SELECT user_password FROM user WHERE user_name=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return false;
            }
        }catch (Exception e){e.printStackTrace();}
        return true;
    }

    public  User getUser(String username){
        try(Statement statement = dbConnection.createStatement()){
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM user WHERE user_name=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User user = new User(username, rs.getString("user_password"), rs.getString("user_status"), rs.getString("user_phone"));
                user.setId(rs.getInt("user_id"));
                return user;
            }
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    @Override
    public boolean checkPermission(String username, int auto_id) {
        try{
            PreparedStatement ps = dbConnection.prepareStatement("SELECT user_name, auto_id FROM auto JOIN user USING (user_id) " +
                    "WHERE user_name = ? AND auto_id = ?");
            ps.setString(1, username);
            ps.setInt(2, auto_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getLastAutoFromThisUser(String username) {
        try (PreparedStatement ps = dbConnection.prepareStatement("SELECT auto_id FROM auto JOIN user " +
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
    public void addReport(Report report) {
        try(PreparedStatement ps = dbConnection.prepareStatement("INSERT INTO report VALUES (NULL, ?,?,?)")) {
            ps.setInt(1, report.getAuto_id());
            ps.setInt(3, report.getUser_id());
            ps.setString(2, report.getComment());
            ps.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public List<Report> getAllReports() {
        List<Report> list = new ArrayList<>();
        try(PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM report")){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Report report = new Report();
                report.setId(rs.getInt("report_id"));
                report.setAuto_id(rs.getInt("auto_id"));
                report.setUser_id(rs.getInt("user_id"));
                report.setComment(rs.getString("comment"));
                list.add(report);
            }
        }catch (Exception e){e.printStackTrace();}
        return list;
    }

    @Override
    public void deleteReport(int rep_id) {
        try(PreparedStatement ps = dbConnection.prepareStatement("DELETE FROM report WHERE report_id = ?")){
            ps.setInt(1, rep_id);
            ps.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public  boolean checkUsersPassword(String username, String password){
        try(Statement statement = dbConnection.createStatement()){
            PreparedStatement ps = dbConnection.prepareStatement("SELECT user_password FROM user WHERE user_name=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return password.equals(rs.getString("user_password"));
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }

    public  void addUserToDatabase(User user){
        try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO user VALUES(NULL,?,?,?,?)")){
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getStatus());
            statement.setString(4, user.getPhone());
            statement.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public  void deleteEmpById(int emp_id){
        try (PreparedStatement ps = dbConnection.prepareStatement("DELETE FROM employee WHERE employee_id = ?")){
            ps.setInt(1, emp_id);
            changeStatusThisUser(getEmpById(emp_id).getUser_id(), "default");
            ps.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }



    public  void deleteAutoById(int auto_id){
        try (PreparedStatement ps = dbConnection.prepareStatement("DELETE FROM auto WHERE auto_id = ?")){
            ps.setInt(1, auto_id);
            ps.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public  void addAutoToDatabase(Auto_model auto){
    try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO auto VALUES(NULL,?,?,?,?,?,?,?)")){
        statement.setInt(1, getBrandByName(auto.getBrand()).getId() );
        statement.setInt(2, auto.getUser_id());
        statement.setString(3, auto.getModel());
        statement.setInt(4, auto.getYear());
        statement.setInt(5, auto.getPrice());
        statement.setInt(6, auto.getMileage());
        statement.setString(7, auto.getCity());
        statement.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Brand getBrandByName(String brand_name){
        try(Statement statement = dbConnection.createStatement()){
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM brand WHERE auto_brand_name = ?");
            ps.setString(1, brand_name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Brand brand = new Brand();
                brand.setName(rs.getString("auto_brand_name"));
                brand.setCountry(rs.getString("auto_brand_country"));
                brand.setId(rs.getInt("auto_brand_id"));
                return brand;
            }
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    public  void addLikeToDatabase(int user_id, int auto_id){
        try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO likes VALUES(NULL,?,?)")){
            statement.setInt(1, user_id);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public  List<Integer> getAllLikes(int user_id){
        List<Integer> list = new ArrayList<>();
        try(PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM likes WHERE user_id = ?")){
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("auto_id"));
            }
        }catch (Exception e){e.printStackTrace();}
        return list;
    }

    public  void deleteLike(int user_id, int auto_id){
        try(PreparedStatement statement = dbConnection.prepareStatement("DELETE FROM likes WHERE user_id = ? AND auto_id = ?")){
            statement.setInt(1, user_id);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public  boolean checkLike(int user_id, int auto_id){
        try(PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM likes WHERE user_id = ? AND auto_id = ?")){
            statement.setInt(1, user_id);
            statement.setInt(2, auto_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                return true;
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }

    public  void addBrandToDatabase(Brand brand){
        try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO brand VALUES(NULL,?,?)")){
            statement.setString(1, brand.getName());
            statement.setString(2, brand.getCountry());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public  List<Auto_model> getAllAuto(){
        List<Auto_model> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM auto");
            while(rs.next()){
                Auto_model auto = new Auto_model();
                auto.setId(rs.getInt("auto_id"));
                auto.setBrand(getBrandById(rs.getInt("auto_brand_id")).getName());
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

    public  List<Auto_model> getAutoByThisIds(List<Integer> ints){
        List<Auto_model> list = new ArrayList<>();
        if (ints.size() == 0) return list;
        StringBuilder str = new StringBuilder("(");
        for (int i = 0; i < ints.size(); i++){
            str.append(ints.get(i));
            if(i != ints.size()-1){
                str.append(",");
            }
            else{
                str.append(")");
            }
        }
        try(PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM auto WHERE auto_id IN " + str.toString())){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Auto_model auto = new Auto_model();
                auto.setId(rs.getInt("auto_id"));
                auto.setBrand(getBrandById(rs.getInt("auto_brand_id")).getName());
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

    public  List<Auto_model> getAllAuto(String username){
        List<Auto_model> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()){
            int user_id = getUser(username).getId();
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM auto WHERE user_id=?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Auto_model auto = new Auto_model();
                auto.setId(rs.getInt("auto_id"));
                auto.setBrand(getBrandById(rs.getInt("auto_brand_id")).getName());
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

    public  List<Brand> getAllBrands(){
        List<Brand> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM brand");
            while(rs.next()){
                Brand brand = new Brand();
                brand.setName(rs.getString("auto_brand_name"));
                brand.setCountry(rs.getString("auto_brand_country"));
                brand.setId(rs.getInt("auto_brand_id"));
                list.add(brand);
            }
        }catch (Exception e){e.printStackTrace();}
        return list;
    }

    public  Brand getBrandById(int id){
        for(Brand brand : getAllBrands()){
            if (brand.getId() == id){
                return brand;
            }
        }
        return null;
    }
    public  User getUserById(int id){
        for(User user : getAllUsers()){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public  Auto_model getAutoById(int id){
        for(Auto_model auto : getAllAuto()){
            if (auto.getId() == id){
                return auto;
            }
        }
        return null;
    }

    public  void changeStatusThisUser(int user_id, String status){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE user " +
                "SET user_status = ? WHERE user_id = ?")){
            statement.setString(1, status);
            statement.setInt(2, user_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public  void updateAutoById_brand(int auto_id, int brand_id){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET auto_brand_id = ? WHERE auto_id = ?")){
            statement.setInt(1, brand_id);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public  void updateAutoById_model(int auto_id, String model){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET auto_model = ? WHERE auto_id = ?")){
            statement.setString(1, model);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public  void updateAutoById_year(int auto_id, int year){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET year = ? WHERE auto_id = ?")){
            statement.setInt(1, year);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public  void updateAutoById_price(int auto_id, int price){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET price = ? WHERE auto_id = ?")){
            statement.setInt(1, price);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public  void updateAutoById_mileage(int auto_id, int mileage){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET mileage = ? WHERE auto_id = ?")){
            statement.setInt(1, mileage);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public  void updateAutoById_city(int auto_id, String city){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET city = ? WHERE auto_id = ?")){
            statement.setString(1, city);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

}
