package com.jgg.Unit4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class crudHandler {

    private static final SessionFactory sessionFactory = null;
    public static void openSession(){
        if(sessionFactory == null){
            new Configuration().configure().buildSessionFactory(); //try-with-resources
        }
        Session session = sessionFactory.openSession();
        if (session != null) {
            System.out.println("Session successfully opened!");
        } else {
            System.out.println("Error opening session!");
        }
    }
    public static void executeInsert(String columnOption) {

    }

    public static void executeSelect(String columnOption) {

    }

    public static void executeUpdate(String columnOption) {

    }

    public static void executeDelete(String columnOption) {

    }
}
