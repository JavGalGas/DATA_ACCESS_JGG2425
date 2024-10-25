package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:postgresql://localhost:5432/EmployeeDB";
    static final String USER = "postgres";
    static final String PASS = "postgres";
    static boolean isRunning = true;

    static int empNo;
    static String eName;
    static String job;
    static int deptNo;
    static String dName;
    static String loc;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try ( Connection conn = DriverManager.getConnection(URL, USER, PASS)
        ) {
            conn.setAutoCommit(false);
            try {
                //Set employee parameters
                System.out.println("Provide an employee number: ");
                empNo = scanner.nextInt();
                System.out.println("Provide an employee name: ");
                eName = scanner.next();
                System.out.println("Provide an employee job: ");
                job = scanner.next();
                //Check if deptno exists
                PreparedStatement checkDeptNo = conn.prepareStatement("SELECT 1 FROM dept WHERE deptno = ?");
                checkDeptNo.setInt(1, 40);
                ResultSet resultSet = checkDeptNo.executeQuery();
                boolean departmentExists = resultSet.next();
                resultSet.close();
                if (!departmentExists) {
                    //Set dept parameters besides deptno
                    System.out.println("Provide a department name: ");
                    dName = scanner.next();
                    System.out.println("Provide a location: ");
                    loc = scanner.next();
                    scanner.nextLine();
                    //Insert dept
                    PreparedStatement statement = conn.prepareStatement("INSERT INTO dept(deptno, dname, loc) VALUES(?,?,?)");
                    statement.setInt(1, deptNo);
                    statement.setString(2, dName);
                    statement.setString(3, loc);
                    statement.executeUpdate();
                }
                //Insert employee
                PreparedStatement statement2 = conn.prepareStatement("INSERT INTO employee(empno, ename, job, deptno) VALUES(?,?,?,?)");
                statement2.setString(1, "Computer Systems Administration");
                statement2.setString(2, "");
                statement2.setString(3, "");
                statement2.setInt(4, deptNo);
                statement2.executeUpdate();
                System.out.println("Transaction finished successfully!!");
                conn.commit();
            }
            catch( SQLException sqlException ) {
                System.err.println( sqlException.getMessage() );
                conn.rollback();
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}