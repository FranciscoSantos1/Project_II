package database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.sql.Connection;

public class Database {

    private static final String PERSISTENCE_UNIT_NAME = "default2";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static EntityManager entityManager = factory.createEntityManager();

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static Query query(String query) {
        return entityManager.createNamedQuery(query);
    }

    public static Query customQuery(String query) {
        return entityManager.createQuery(query);
    }

    public static <T> T find(Class<T> type, Object key) {
        return entityManager.find(type, key);
    }

    public static void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public static void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    public static void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }

    public static void insert(Object entity) {
        entityManager.persist(entity);
    }

    public static void update(Object entity) {
        entityManager.merge(entity);
    }

    public static void delete(Object entity) {
        entityManager.remove(entity);
    }

    public static void connect() {
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
    }

    public static void disconnect() {
        entityManager.close();
        entityManager = null;
    }

    public static Connection getConnection() {
        connect(); // Certifique-se de que a conexão com o EntityManager está estabelecida
        return entityManager.unwrap(Session.class).doReturningWork(connection -> (Connection) connection);
    }
}
