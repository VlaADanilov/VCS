package org.DB.mappers;

import org.models.Brand;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandMapper implements RowMapper<Brand> {

    @Override
    public Brand mapRow(ResultSet rs) throws SQLException {
        return new Brand(
                rs.getInt("auto_brand_id"),
                rs.getString("auto_brand_name"),
                rs.getString("auto_brand_country")
        );
    }
}
