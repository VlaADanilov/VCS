package org.DB.mappers;

import org.models.Image;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageMapper implements RowMapper<Image> {

    @Override
    public Image mapRow(ResultSet rs) throws SQLException {
        return new Image(
                rs.getInt("image_id"),
                rs.getInt("auto_id"),
                rs.getString("image")
        );
    }
}
