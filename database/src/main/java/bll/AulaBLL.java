package bll;

import database.Database;
import entity.Aula;
import entity.Funcionario;
import entity.Modalidade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.time.Instant;
import java.util.ArrayList;
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
            System.out.println("ID: " + aula.getId() + " Nome: " + aula.getNome() + " Modalidade: " + aula.getIdModalidade() + " Professor: " + aula.getIdFuncionario());
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

    public static List<Aula> getAllAulas() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a FROM Aula a");
        return query.getResultList();
    }

    public static String getInstrutorNameByIdAula(int id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT f.nome FROM Funcionario f, Aula a WHERE a.idFuncionario = f.idFuncionario AND a.id = :id");
        query.setParameter("id", id);
        return (String) query.getSingleResult();
    }

    public static String getModalidadeNameByIdAula(int id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT m.modalidade FROM Modalidade m, Aula a WHERE a.idModalidade = m.idModalidade AND a.id = :id");
        query.setParameter("id", id);
        return (String) query.getSingleResult();
    }


    public static List<Funcionario> getAvailableInstructors(Instant start, Instant end) {
        EntityManager entityManager = Database.getEntityManager();
        List<Funcionario> availableInstructors = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            // Query to find instructors not engaged in another class at the same time
            Query query = entityManager.createQuery(
                    "SELECT f FROM Funcionario f WHERE f.idTipofuncionario = 2 AND NOT EXISTS (" +
                            "SELECT 1 FROM Aula a WHERE a.idFuncionario = f.idFuncionario AND NOT (" +
                            "a.dataHoraFim <= :start OR a.dataHoraComeco >= :end))", Funcionario.class);
            query.setParameter("start", start);
            query.setParameter("end", end);
            availableInstructors = query.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to fetch available instructors", e);
        } finally {
            entityManager.close();
        }
        return availableInstructors;
    }
}
