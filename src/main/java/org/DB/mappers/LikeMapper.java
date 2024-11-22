package org.DB.mappers;

import org.models.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeMapper implements RowMapper<Like> {
    @Override
    public Like mapRow(ResultSet rs) throws SQLException {
        return new Like(
                rs.getInt("like_id"),
                rs.getInt("user_id"),
                rs.getInt("auto_id")
        );
    }
}
