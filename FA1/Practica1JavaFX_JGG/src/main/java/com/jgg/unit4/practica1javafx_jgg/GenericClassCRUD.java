package com.jgg.unit4.practica1javafx_jgg;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;


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
                com.jgg.unit4.practica1javafx_jgg.SellerApplication.LOGGER.log(Level.SEVERE, "Session is null");
            }
            return session;
        } catch (HibernateException hibernateException) {
            SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while opening session", hibernateException);
            UI.showErrorAlert("Error", "An error occurred while opening session", hibernateException.getMessage());
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
            // Notify the user about the unexpected error
            UI.showErrorAlert("Error", "An error occurred during save operation", runtimeException.getMessage());
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
            // Notify the user about the failure
            UI.showErrorAlert("Error", "An error occurred during update operation", hibernateException.getMessage());
        } catch (RuntimeException runtimeException) {
            if (transaction != null) {
                transaction.rollback();
            }
            SellerApplication.LOGGER.log(Level.SEVERE, "Unexpected error during update operation", runtimeException);
            // Notify the user about the unexpected error
            UI.showErrorAlert("Error", "An error occurred during update operation", runtimeException.getMessage());
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
            UI.showErrorAlert("Error", "An error occurred while finding by ID operation", hibernateException.getMessage());
            return null;
        }
    }

    // FILTERED READ by ID
    public T findById(int id, String filterName, String paramName, Object value) {
        try (Session session = openSession() ) {
            if (session != null && filterName != null && !filterName.isEmpty()) {
                session.enableFilter(filterName);
                if(paramName != null && !paramName.isEmpty()) {
                    session.enableFilter(filterName).setParameter(paramName, value);
                }
                T object = session.get(entityClass, id);
                session.disableFilter(filterName);
                return object;
            } else {
                return null;
            }
        } catch (HibernateException hibernateException) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Hibernate error during find by ID operation", hibernateException);
            UI.showErrorAlert("Error", "An error occurred during find by ID operation", hibernateException.getMessage());
            return null;
        }
    }

    // FILTERED READ All
    public List<T> findAll(String filterName, String paramName, Object value) {
        try (Session session = openSession()) {
            if (session != null && filterName != null
                    && !filterName.isEmpty() && paramName != null
                    && !paramName.isEmpty() && value != null) {
                String hibernateQl = "FROM " + entityClass.getSimpleName();
                Query<T> query = session.createQuery(hibernateQl, entityClass);
                 session.enableFilter(filterName);
                if(value.getClass().equals(Integer.class)) {
                    session.enableFilter(filterName).setParameter(paramName, value);
                }
                List<T> list = query.list();
                session.disableFilter(filterName);
                return list;
            } else {
                return Collections.emptyList();
            }
        } catch (HibernateException hibernateException) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Hibernate error during find all operation", hibernateException);
            UI.showErrorAlert("Error", "An error occurred during find all operation", hibernateException.getMessage());
            return Collections.emptyList();
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
            UI.showErrorAlert("Error", "An error occurred during find all operation", hibernateException.getMessage());
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
            // Notify the user about the failure
            UI.showErrorAlert("Error", "An error occurred while deleting entity", hibernateException.getMessage());
        } catch (RuntimeException runtimeException) {
            if (transaction != null) {
                transaction.rollback();
            }
            com.jgg.unit4.practica1javafx_jgg.SellerApplication.LOGGER.log(Level.SEVERE, "Unexpected error occurred during deletion operation", runtimeException);
            // Notify the user about the unexpected error
            UI.showErrorAlert("Error", "An error occurred while deleting entity", runtimeException.getMessage());
        }
    }
}
