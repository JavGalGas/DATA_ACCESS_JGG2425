package com.jgg.unit4.practica1javafx_jgg;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.logging.Level;


public class GenericClassCRUD<T> {

    private final Class<T> entityClass;
    public static SessionFactory sessionFactory = null;

    public GenericClassCRUD(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public static Session openSession() throws Exception {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure(); // Carga de hibernate.cfg.xml
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory.openSession();
    }

    // CREATE or UPDATE
    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null)
                transaction.rollback();
            SellerAppApplication.LOGGER.log(Level.SEVERE,"An exception has occurred: " + exception.getMessage());
        }
    }

    //UPDATE
    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null)
                transaction.rollback();
            SellerAppApplication.LOGGER.log(Level.SEVERE,"An exception has occurred: " + exception.getMessage());
        }
    }

    // READ by ID
    public T findById(int id) {
        try (Session session = openSession() ) {
            return session.get(entityClass, id);
        } catch( Exception exception ) {
            SellerAppApplication.LOGGER.log(Level.SEVERE,"An exception has occurred: " + exception.getMessage());
            return null;
        }
    }

    // READ All
    public List<T> findAll() {
        try (Session session = openSession()) {
            String hql = "FROM " + entityClass.getSimpleName();
            Query<T> query = session.createQuery(hql, entityClass);
            return query.list();
        } catch( Exception exception ) {
            SellerAppApplication.LOGGER.log(Level.SEVERE,"An exception has occurred: " + exception.getMessage());
            return null;
        }
    }

    // DELETE
    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null)
                transaction.rollback();
            SellerAppApplication.LOGGER.log(Level.SEVERE,"An exception has occurred: " + exception.getMessage());
        }
    }

//    public static void updateClass( Class<T> class ) {
//        try ( Session session = openSession() ) {
//            Seller seller = session.get( Seller.class,
//                    sellerId );
//            if ( seller != null ) {
//                session.beginTransaction();
//                /*updates seller*/
//                session.merge(seller); // you can also use ‘update’
//                session.getTransaction().commit();
//            }
//            else
//                System.out.println("Employee not found");
//        }
//        catch( Exception exception ) {
//            System.out.println( exception.getMessage() );
//        }
//    }
//
//    public static void insertClass() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Department name?: ");
//        String dname = scanner.nextLine();
//        System.out.print("Department location?: ");
//        String dloc = scanner.nextLine();
//        Transaction transaction;
//        try ( Session session = openSession() ) {
//            transaction = session.beginTransaction();
//            DeptEntity department = new DeptEntity();
//            department.setDeptname( dname );
//            department.setLoc( dloc );
//            session.persist( department ); // you can also use ‘save’
//            transaction.commit(); // End of transaction
//        }
//        catch( Exception e ) {
//            transaction.rollback();
//            System.out.println( e.getMessage() );
//        }
//    }
}
