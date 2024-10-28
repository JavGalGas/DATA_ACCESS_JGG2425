package org.example;

import java.sql.*;

public class InsertTranHandler {
    static final String URL = "jdbc:postgresql://localhost:5432/EmployeeDB";
    static final String USER = "postgres";
    static final String PASS = "postgres";

    static int maxDNameLength = 14;
    static int maxLocLength = 13;
    static int maxENameLength = 10;
    static int maxJobLength = 9;

    public static void runTran(){
        try ( Connection conn = DriverManager.getConnection(URL, USER, PASS)
        ) {
            conn.setAutoCommit(false);
            try {
                System.out.println("~~New employee~~");
                Employee employee = new Employee();
                //Set employee parameters

                PreparedStatement setEmployeeNumber = conn.prepareStatement("SELECT MAX(empno) FROM employee");
                ResultSet resultSet = setEmployeeNumber.executeQuery();
                resultSet.next();
                int maxEmpNo = resultSet.getInt(1);
                employee.setNumber(maxEmpNo + 1);
                String eName = UI.getStringParameter(0);
                employee.setName(eName.substring(0, Math.min(eName.length(), maxENameLength)));
                String job = UI.getStringParameter(1);
                employee.setJob(job.substring(0, Math.min(job.length(), maxJobLength)));
                employee.setDepartmentNumber(UI.getIntParameter(0));

                //Check if deptno exists
                PreparedStatement checkDeptNo = conn.prepareStatement("SELECT 1 FROM dept WHERE deptno = ?");
                checkDeptNo.setInt(1, employee.getDepartmentNumber());
                resultSet = checkDeptNo.executeQuery();
                boolean departmentExists = resultSet.next();
                resultSet.close();
                if (!departmentExists) {
                    System.out.println("~~New department~~");
                    Department department = new Department();
                    department.setNumber(employee.getDepartmentNumber());
                    //Set dept parameters besides deptno
                    String dName = UI.getStringParameter(2);
                    department.setName(dName.substring(0, Math.min(dName.length(), maxDNameLength)));
                    String loc = UI.getStringParameter(3);
                    department.setLocation(loc.substring(0, Math.min(loc.length(), maxLocLength)));
                    //Insert dept
                    PreparedStatement statement = conn.prepareStatement(
                            "INSERT INTO dept(deptno, dname,loc) VALUES(?,?,?)");
                    statement.setInt(1, department.getNumber());
                    statement.setString(2, department.getName());
                    statement.setString(3, department.getLocation());
                    statement.executeUpdate();
                }
                //Insert employee
                PreparedStatement statement2 = conn.prepareStatement(
                        "INSERT INTO employee(empno, ename, job, deptno) VALUES(?,?,?,?)");
                statement2.setInt(1, employee.getNumber());
                statement2.setString(2, employee.getName());
                statement2.setString(3, employee.getJob());
                statement2.setInt(4, employee.getDepartmentNumber());
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
