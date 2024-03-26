package bll;

import database.Database;
import entity.Aula;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class AulaBLL {

    public static void createAula(Aula aula) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(aula);
        entityManager.getTransaction().commit();
    }

    public static void deleteAula(Aula aula) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.remove(aula);
        entityManager.getTransaction().commit();
    }

    public static Aula getAulaById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        return entityManager.find(Aula.class, id);
    }

    public static void readAula() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a FROM Aula a");
        List<Aula> aulas = query.getResultList();
        for (Aula aula : aulas) {
            System.out.println("ID: " + aula.getIdAula() + " Nome: " + aula.getNome());
        }
    }

    public static void updateAula(Aula aula) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(aula);
        entityManager.getTransaction().commit();
    }
}
