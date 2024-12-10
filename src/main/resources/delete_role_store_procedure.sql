--Execute manually in the h2 database
DROP ALIAS DELETE_ROLE_AND_ASSOCIATED_EMPLOYEES IF EXISTS;

CREATE ALIAS DELETE_ROLE_AND_ASSOCIATED_EMPLOYEES AS  $$
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@CODE
void deleteRoleAndAssociatedEmployees(final int roleId) throws SQLException, Exception
{
    try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:employee_database", "admin", "adminpwd")) {
        // Get default employee
        int defalutEmployeeId;
        String defaultEmployeeQuery = "select top 1 id from EMPLOYEE where role_id!=?)";
        try (PreparedStatement ps = conn.prepareStatement(defaultEmployeeQuery)) {
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                defalutEmployeeId = rs.getInt(1);
            } else {
                throw new Exception("No default employee to reassign the projects");
            }
        }
        // Reassign all projects associated to employees of the deleted role
        String reassignProjects = "update PROJECT set employee_id = ? where employee_id IN (select id from EMPLOYEE WHERE role_id = ?)";
        try (PreparedStatement ps = conn.prepareStatement(reassignProjects)) {
            ps.setInt(1, defalutEmployeeId);
            ps.setInt(2, roleId);
            ps.executeUpdate();
        }
        // Delete employees associated with the role
        String deleteEmployees = "delete from EMPLOYEE where role_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteEmployees)) {
            ps.setInt(1, roleId);
            ps.executeUpdate();
        }
        // Delete the role
        String deleteRole = "delete from ROLE where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteRole)) {
            ps.setInt(1, roleId);
            ps.executeUpdate();
        }
    }
}
$$;