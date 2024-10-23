package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Main {
    static final String URL = "jdbc:postgresql://localhost:5432/EmployeeDB";
    static final String USER = "postgres";
    static final String PASS = "postgres";
    static boolean isRunning = true;
    public static void main(String[] args) {
        try ( Connection conn = DriverManager.getConnection(URL, USER, PASS)
        ) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement checkDeptNo = conn.prepareStatement("SELECT 1 FROM dept WHERE deptno = ?");
                checkDeptNo.setInt(1, 40);
                ResultSet resultSet = checkDeptNo.executeQuery();
                boolean departmentExists = resultSet.next();
                if (!departmentExists)
                PreparedStatement deptNoStatement = conn.prepareStatement("SELECT deptno FROM dept ORDER BY deptno DESC LIMIT 1;");
                resultSet = deptNoStatement.executeQuery();
                int newDeptNo = resultSet.getInt(1) + 10;
                PreparedStatement statement = conn.prepareStatement("INSERT INTO dept(deptno, dname, loc) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, newDeptNo);
                statement.setString(2, "");
                statement.setString(3, "");
                statement.executeUpdate();
                ResultSet keys = statement.getGeneratedKeys();
                keys.next();
                int courseCode = keys.getInt(1);
                PreparedStatement statement2 = conn.prepareStatement("INSERT INTO employee(empno, ename, job, deptno) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement2.setString(1, "Computer Systems Administration");
                statement2.setString(2, "");
                statement2.setString(3, "");
                statement2.setInt(4,courseCode);
                statement2.executeUpdate();
                System.out.println("Transaction finished successfully!!");
                conn.commit();
            }
            catch( SQLException ex ) {
                System.err.println( ex.getMessage() );
                conn.rollback();
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}