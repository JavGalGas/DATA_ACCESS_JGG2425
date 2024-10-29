package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
//                Query<DeptEntity> query = session.createQuery("from org.example.DeptEntity");
//                List<DeptEntity> deptEntities = query.list();
                employee.setDepartment(new DeptEntity(){

                });
                session.update(employee);
            }
            else
                System.out.println("Employee not found");
        }
        catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }
}
