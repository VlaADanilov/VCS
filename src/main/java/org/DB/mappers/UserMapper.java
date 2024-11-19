package org.DB.mappers;

import org.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getString("user_password"),
                rs.getString("user_status"),
                rs.getString("user_email")
        );
    }
}
