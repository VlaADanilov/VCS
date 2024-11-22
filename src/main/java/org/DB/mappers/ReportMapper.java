package org.DB.mappers;

import org.models.Report;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMapper implements RowMapper<Report> {

    @Override
    public Report mapRow(ResultSet rs) throws SQLException {
        Report report = new Report(
                rs.getInt("auto_id"),
                rs.getString("comment"),
                rs.getInt("user_id")
        );
        report.setId(rs.getInt("report_id"));
        return report;
    }
}
