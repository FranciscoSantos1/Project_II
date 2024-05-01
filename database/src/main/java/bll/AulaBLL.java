package bll;

import database.Database;
import entity.Aula;
import entity.Funcionario;
import entity.Modalidade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AulaBLL {
    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Lisbon"); // Adjust this to your intended timezone


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

    public static String getLocalByIdAula(int id)
    {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a.localAula FROM Aula a WHERE a.id = :id");
        query.setParameter("id", id);
        return (String) query.getSingleResult();
    }

    public static String getTipoAulaByIdAula(int id)
    {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a.vagas FROM Aula a WHERE a.id = :id");
        query.setParameter("id", id);
        if((int) query.getSingleResult() == 1)
        {
            return "AULA INDIVIDUAL";
        }
        else
        {
            return "AULA GRUPO";
        }
    }


    public static List<Funcionario> getAvailableInstructors(Instant start, Instant end) {
        EntityManager entityManager = Database.getEntityManager();
        List<Funcionario> availableInstructors = new ArrayList<>();
        try {
            // Start transaction for read operation
            entityManager.getTransaction().begin();

            // Query to find instructors who do not have a class overlapping the new class time
            String jpql = "SELECT f FROM Funcionario f WHERE f.idTipofuncionario = 1 AND NOT EXISTS (" +
                    "SELECT a FROM Aula a WHERE a.idFuncionario = f.idFuncionario AND (" +
                    "a.dataHoraComeco < :end AND a.dataHoraFim > :start))";

            Query query = entityManager.createQuery(jpql, Funcionario.class);
            query.setParameter("start", start);
            query.setParameter("end", end);

            availableInstructors = query.getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return availableInstructors;
    }

    public static Instant getDateInicioByIdAula(int id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a.dataHoraComeco FROM Aula a WHERE a.id = :id");
        query.setParameter("id", id);
        return (Instant) query.getSingleResult();
    }

    public static Instant getDateFimByIdAula(int id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a.dataHoraFim FROM Aula a WHERE a.id = :id");
        query.setParameter("id", id);
        return (Instant) query.getSingleResult();
    }

    public void InstantFormatter(Instant instant) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = Date.from(instant);
    }


    public static List<Aula> getAulasByInstructorId(Integer instructorId) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a FROM Aula a WHERE a.idFuncionario = :instructorId");
        query.setParameter("instructorId", instructorId);
        return query.getResultList();
    }
}
