package org.models;

public class Employee {
    private int id;
    private String name;
    private String profession;
    private String description;
    private String phone;
    private int user_id;

    public Employee(String name, String profession, String description, int user_id) {
        this.name = name;
        this.profession = profession;
        this.description = description;
        this.user_id = user_id;
    }

    public Employee(int id, String name, String profession, String description, String phone, int user_id) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.description = description;
        this.phone = phone;
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
