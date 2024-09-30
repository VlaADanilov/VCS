package org.DB;

import org.models.Auto_model;
import org.models.Brand;
import org.models.User;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQL_helper {
    private static String url = "jdbc:mysql://localhost:3306/auto_base";
    private static Connection dbConnection = null;

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(url, "root", "root");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addImageToThisAuto(InputStream is, int auto_id){

        //нужно сделать проверку, что существует такое auto_id
        try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO auto_images VALUES(NULL,?,?)")){
            if(is.available() == 0) return;
            statement.setInt(1, auto_id);
            statement.setBlob(2, is);
            statement.executeUpdate();
            System.out.println("Image added successfully");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static List<byte[]> getImageFromThisAuto(int auto_id){
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

    public static int getCountOfImageFromThisAuto(int auto_id){
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

    public static List<User> getAllUsers(){
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

    public static boolean checkUser(String username){
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

    public static User getUser(String username){
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

    public static boolean checkUsersPassword(String username, String password){
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

    public static void addUserToDatabase(User user){
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

    public static void addAutoToDatabase(Auto_model auto){
    try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO auto VALUES(NULL,?,?,?,?,?,?)")){
        statement.setInt(1, auto.getBrand());
        statement.setInt(2, auto.getUser_id());
        statement.setString(3, auto.getModel());
        statement.setInt(4, auto.getYear());
        statement.setInt(5, auto.getPrice());
        statement.setInt(6, auto.getMileage());
        statement.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void addBrandToDatabase(Brand brand){
        try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO brand VALUES(NULL,?,?)")){
            statement.setString(1, brand.getName());
            statement.setString(2, brand.getCountry());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Auto_model> getAllAuto(){
        List<Auto_model> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM auto");
            while(rs.next()){
                Auto_model auto = new Auto_model();
                auto.setId(rs.getInt("auto_id"));
                auto.setBrand(rs.getInt("auto_brand_id"));
                auto.setUser_id(rs.getInt("user_id"));
                auto.setModel(rs.getString("auto_model"));
                auto.setPrice(rs.getInt("price"));
                auto.setYear(rs.getInt("year"));
                auto.setMileage(rs.getInt("mileage"));
                list.add(auto);
            }
        }catch (Exception e){e.printStackTrace();}
        return list;
    }

    public static List<Auto_model> getAllAuto(String username){
        List<Auto_model> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()){
            int user_id = getUser(username).getId();
            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM auto WHERE user_id=?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Auto_model auto = new Auto_model();
                auto.setId(rs.getInt("auto_id"));
                auto.setBrand(rs.getInt("auto_brand_id"));
                auto.setUser_id(rs.getInt("user_id"));
                auto.setModel(rs.getString("auto_model"));
                auto.setPrice(rs.getInt("price"));
                auto.setYear(rs.getInt("year"));
                auto.setMileage(rs.getInt("mileage"));
                list.add(auto);
            }
        }catch (Exception e){e.printStackTrace();}
        return list;
    }

    public static List<Brand> getAllBrands(){
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

    public static Brand getBrandById(int id){
        for(Brand brand : getAllBrands()){
            if (brand.getId() == id){
                return brand;
            }
        }
        return null;
    }
    public static User getUserById(int id){
        for(User user : getAllUsers()){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public static Auto_model getAutoById(int id){
        for(Auto_model auto : getAllAuto()){
            if (auto.getId() == id){
                return auto;
            }
        }
        return null;
    }

    public static void changeStatusThisUser(int user_id, String status){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE user " +
                "SET user_status = ? WHERE user_id = ?")){
            statement.setString(1, status);
            statement.setInt(2, user_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public static void updateAutoById_brand(int auto_id, int brand_id){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET auto_brand_id = ? WHERE auto_id = ?")){
            statement.setInt(1, brand_id);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public static void updateAutoById_model(int auto_id, String model){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET auto_model = ? WHERE auto_id = ?")){
            statement.setString(1, model);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public static void updateAutoById_year(int auto_id, int year){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET year = ? WHERE auto_id = ?")){
            statement.setInt(1, year);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public static void updateAutoById_price(int auto_id, int price){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET price = ? WHERE auto_id = ?")){
            statement.setInt(1, price);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public static void updateAutoById_mileage(int auto_id, int mileage){
        try(PreparedStatement statement = dbConnection.prepareStatement("UPDATE auto " +
                "SET mileage = ? WHERE auto_id = ?")){
            statement.setInt(1, mileage);
            statement.setInt(2, auto_id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
}
