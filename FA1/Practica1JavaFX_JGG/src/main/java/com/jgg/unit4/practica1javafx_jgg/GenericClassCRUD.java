package com.jgg.unit4.practica1javafx_jgg;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import javax.naming.Referenceable;


public class GenericClassCRUD<T> {

    private final Class<T> entityClass;
    public static SessionFactory sessionFactory = null;

    public GenericClassCRUD(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Session openSession() {
        try {
            if ( sessionFactory == null )
                sessionFactory = new Configuration().configure().buildSessionFactory();
            SellerApplication.LOGGER.log(Level.INFO, "Opening session");
            Session session = sessionFactory.openSession();
            if (session == null) {
                SellerApplication.LOGGER.log(Level.SEVERE, "Session is null");
            }
            return session;
        } catch (HibernateException hibernateException) {
            SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while opening session", hibernateException);
            SellerApplication.LOGGER.info(hibernateException.getMessage());
            return null;
        }
    }

    // CREATE
    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = openSession()) {
            if (session != null) {
                transaction = session.beginTransaction();
                session.persist(entity);
                transaction.commit();
            }
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
            SellerApplication.LOGGER.log(Level.SEVERE, "Hibernate error during save operation", hibernateException);
            SellerApplication.LOGGER.info(hibernateException.getMessage());
            // Notify the user about the failure
        } catch (RuntimeException runtimeException) {
            if (transaction != null) {
                transaction.rollback();
            }
            SellerApplication.LOGGER.log(Level.SEVERE, "Unexpected error during save operation", runtimeException);
            SellerApplication.LOGGER.info(runtimeException.getMessage());
            // Notify the user about the unexpected error
        }
    }

    //UPDATE
    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = openSession()) {
            if (session != null) {
                transaction = session.beginTransaction();
                session.merge(entity);
                transaction.commit();
            }
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
            SellerApplication.LOGGER.log(Level.SEVERE, "Hibernate error during update operation", hibernateException);
            SellerApplication.LOGGER.info(hibernateException.getMessage());
            // Notify the user about the failure
        } catch (RuntimeException runtimeException) {
            if (transaction != null) {
                transaction.rollback();
            }
            SellerApplication.LOGGER.log(Level.SEVERE, "Unexpected error during update operation", runtimeException);
            SellerApplication.LOGGER.info(runtimeException.getMessage());
            // Notify the user about the unexpected error
        }
    }

    // READ by ID
    public T findById(int id) {
        try (Session session = openSession() ) {
            if (session != null) {
                return session.get(entityClass, id);
            } else {
                return null;
            }
        } catch (HibernateException hibernateException) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Hibernate error during find by ID operation", hibernateException);
            SellerApplication.LOGGER.info(hibernateException.getMessage());
            return null;
        }
    }

    // READ All
    public List<T> findAll() {
        try (Session session = openSession()) {
            if (session != null) {
                String hibernateQl = "FROM " + entityClass.getSimpleName();
                Query<T> query = session.createQuery(hibernateQl, entityClass);
                return query.list();
            } else {
                return Collections.emptyList();
            }
        } catch (HibernateException hibernateException) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Hibernate error during find all operation", hibernateException);
            SellerApplication.LOGGER.info(hibernateException.getMessage());
            return Collections.emptyList();
        }
    }

    // DELETE
    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = openSession()) {
            if (session != null) {
                transaction = session.beginTransaction();
                session.remove(entity);
                transaction.commit();
            }
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
            SellerApplication.LOGGER.log(Level.SEVERE, "Hibernate error during during deletion operation", hibernateException);
            SellerApplication.LOGGER.info(hibernateException.getMessage());
            // Notify the user about the failure
        } catch (RuntimeException runtimeException) {
            if (transaction != null) {
                transaction.rollback();
            }
            SellerApplication.LOGGER.log(Level.SEVERE, "Unexpected error occurred during deletion operation", runtimeException);
            SellerApplication.LOGGER.info(runtimeException.getMessage());
            // Notify the user about the unexpected error
        }
    }
}
