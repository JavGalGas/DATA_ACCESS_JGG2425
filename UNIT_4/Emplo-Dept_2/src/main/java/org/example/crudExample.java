package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.management.Query;
import java.util.List;

public class crudExample {
    public static SessionFactory sessionFactory = null;
    public static Session openSession() throws Exception {
        if ( sessionFactory == null )
            sessionFactory =
                    new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        if (session == null) {
            throw new Exception("Error opening session!");
        }
        return session;
    }
    public static void updateEmployee( int employeeNumber ) {
        DeptEntity deptEntity;
        try ( Session session = openSession() ) {
            EmployeeEntity employee =
                    (EmployeeEntity)session.get( EmployeeEntity.class,
                            employeeNumber );
            if ( employee != null ) {
                employee.setJob("BAKER");
                session.merge(employee);
            }
            else
                System.out.println("Employee not found");
        }
        catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }

    public static void insertDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(“Department name?: “);
        String dname = scanner.nextline();
        System.out.print(“Department location?: “);
        String dloc = scanner.nextline();
        Transaction transaction;
        try ( Session session = openSession() ) {
            transaction = session.beginTransaction();
            DeptEntity department = new DeptEntity();
            department.setDeptname( dname );
            department.setLoc( dloc );
            session.save( department ); // alternatively, session.persist
            transaction.commit(); // End of transaction
        }
        catch( Exception e ) {
            transaction.rollback();
            System.out.println( e.getMessage() );
        }
    }

    public static void deleteEmployee( int employeeNumber ) {
        try ( Session session = openSession() ) {
            EmployeeEntity employee = session.get( EmployeeEntity.class,
                    employeeNumber );
            Transaction transaction = null;
            if ( employee != null ) {
                transaction = session.beginTransaction();
                session.remove(employee);
                transaction.commit(); // End of transaction
                System.out.println("The employee has been deleted.");
            }
            else {
                transaction.rollback();
                System.out.println("Employee not found.");
            }
        }
        catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }
}
