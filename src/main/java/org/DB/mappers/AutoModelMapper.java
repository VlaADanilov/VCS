package org.DB.mappers;

import org.models.AutoModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoModelMapper implements RowMapper<AutoModel> {


    @Override
    public AutoModel mapRow(ResultSet rs) throws SQLException {
        AutoModel autoModel = new AutoModel(
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
