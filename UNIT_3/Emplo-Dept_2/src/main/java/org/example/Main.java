package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        if (session != null) {
            System.out.println();
        } else {
            System.out.println();
        }

        Query<EmployeeEntity> query = session.createQuery("from con.jdbc:postgresql://localhost:5432/EmployeeDB");
        List<EmployeeEntity> employees = query.list();
    }
}