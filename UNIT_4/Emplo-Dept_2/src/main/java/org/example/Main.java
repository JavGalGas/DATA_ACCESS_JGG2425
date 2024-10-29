package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger =
                org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate")
                .setLevel(Level.SEVERE);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        if (session != null) {
            System.out.println("Session successfully opened!");
        } else {
            System.out.println("Error opening session!");
        }

        Query<EmployeeEntity> query = session.createQuery("from org.example.EmployeeEntity");
        List<EmployeeEntity> employees = query.list();

        for ( EmployeeEntity employee : employees ) {
            System.out.println("Number : "+ employee.getEmpno() + " Name: " + employee.getEname());
        }
    }
}