package org.DB.mappers;

import org.models.Auto_model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoModelMapper implements RowMapper<Auto_model> {


    @Override
    public Auto_model mapRow(ResultSet rs) throws SQLException {
        Auto_model autoModel = new Auto_model(
                rs.getInt("auto_brand_id"),
                rs.getInt("user_id"),
                rs.getString("auto_model"),
                rs.getInt("year"),
                rs.getInt("price"),
                rs.getInt("mileage"),
                rs.getString("city"),
                rs.getString("description")
        );
        autoModel.setId(rs.getInt("auto_id"));
        return autoModel;
    }
}
