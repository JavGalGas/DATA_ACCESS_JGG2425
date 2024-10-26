package org.example;

import java.sql.*;

public class InsertTranHandler {

    static final String URL = "jdbc:postgresql://localhost:5432/EmployeeDB";
    static final String USER = "postgres";
    static final String PASS = "postgres";

    static int empNo;
    static String eName;
    static String job;
    static int deptNo;
    static String dName;
    static String loc;

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
                //Set employee parameters

                PreparedStatement setEmployeeNumber = conn.prepareStatement("SELECT MAX(empno) FROM employee");
                ResultSet resultSet = setEmployeeNumber.executeQuery();
                resultSet.next();
                int maxEmpNo = resultSet.getInt(1);
                empNo = maxEmpNo + 1;
                eName = UI.getStringParameter(0);
                job = UI.getStringParameter(1);
                deptNo = UI.getIntParameter(0);

                //Check if deptno exists
                PreparedStatement checkDeptNo = conn.prepareStatement("SELECT 1 FROM dept WHERE deptno = ?");
                checkDeptNo.setInt(1, 40);
                resultSet = checkDeptNo.executeQuery();
                boolean departmentExists = resultSet.next();
                resultSet.close();
                if (!departmentExists) {
                    System.out.println("~~New department~~");
                    //Set dept parameters besides deptno
                    dName = UI.getStringParameter(2);
                    loc = UI.getStringParameter(3);
                    //Insert dept
                    PreparedStatement statement = conn.prepareStatement(
                            "INSERT INTO dept(deptno, SUBSTRING[dname, 1, " + maxDNameLength + "], " +
                                    "SUBSTRING[loc, 1, " + maxLocLength + "]) VALUES(?,?,?)");
                    statement.setInt(1, deptNo);
                    statement.setString(2, dName);
                    statement.setString(3, loc);
                    statement.executeUpdate();
                }
                //Insert employee
                PreparedStatement statement2 = conn.prepareStatement(
                        "INSERT INTO employee(empno, SUBSTRING[ename, 1, " + maxENameLength + "], " +
                                "SUBSTRING[job, 1, " + maxJobLength + "], deptno) VALUES(?,?,?,?)");
                statement2.setInt(1, empNo);
                statement2.setString(2, eName);
                statement2.setString(3, job);
                statement2.setInt(4, deptNo);
                statement2.executeUpdate();
                System.out.println("Transaction finished successfully!!");
                conn.commit();
            }
            catch( SQLException sqlException ) {
                System.err.println( sqlException.getMessage() );
                conn.rollback();
            }
            finally {
                UI.close();
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
