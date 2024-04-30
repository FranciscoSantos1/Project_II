package bll;

import database.Database;
import entity.Modalidade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class ModalidadeBLL {

    public static void createModalidade(Modalidade modalidade) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(modalidade);
        entityManager.getTransaction().commit();
    }

    public static void deleteModalidade(Modalidade modalidade) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.remove(modalidade);
        entityManager.getTransaction().commit();
    }

    public static Modalidade getModalidadeById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        return entityManager.find(Modalidade.class, id);
    }

    public static List<Modalidade> getAllModalidades() {
        EntityManager entityManager = Database.getEntityManager();
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            Query query = entityManager.createQuery("SELECT m FROM Modalidade m");
            List<Modalidade> modalidades = query.getResultList();
            entityManager.getTransaction().commit();
            return modalidades;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    public static void readModalidade() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT m FROM Modalidade m");
        List<Modalidade> modalidades = query.getResultList();
        for (Modalidade modalidade : modalidades) {
            System.out.println("ID: " + modalidade.getIdModalidade() + " Nome: " + modalidade.getModalidade());
        }
    }

    public static void updateModalidade(Modalidade modalidade) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(modalidade);
        entityManager.getTransaction().commit();
    }
}
