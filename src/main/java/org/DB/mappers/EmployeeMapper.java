package org.DB.mappers;

import org.models.Employee;
import org.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("employee_id"),
                rs.getString("employee_name"),
                rs.getString("employee_profession"),
                rs.getString("employee_description"),
                rs.getInt("user_id")
        );
    }
}
