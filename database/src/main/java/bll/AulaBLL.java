package bll;

import database.Database;
import entity.*;
import bll.LinhaAulaBLL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static database.Database.getEntityManager;

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

    public static String getSocioNameByIdAula(int id) {
        try{
            EntityManager entityManager = Database.getEntityManager();
            Query query = entityManager.createQuery("SELECT s.nome FROM Socio s, LinhaAula l WHERE l.idSocio = s.idSocio AND l.idAula = :id");
            query.setParameter("id", id);
            return (String) query.getSingleResult();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static List<Aula> getAllAulasGrupo() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a FROM Aula a WHERE a.totalLugares > 1");
        return query.getResultList();
    }

    public static List<Aula> getAllAulasIndividual() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a FROM Aula a WHERE a.totalLugares = 1");
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
        Query query = entityManager.createQuery("SELECT a.totalLugares FROM Aula a WHERE a.id = :id");
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

    public static List<Socio> getAllSociosFromAula(Integer id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT s FROM Socio s JOIN LinhaAula l ON s.idSocio = l.idSocio WHERE l.idAula = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public static List<Socio> getAllSocios() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT s FROM Socio s");
        return query.getResultList();
    }


    public static List<Aula> getAulasByInstructorId(Integer instructorId) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a FROM Aula a WHERE a.idFuncionario = :instructorId");
        query.setParameter("instructorId", instructorId);
        return query.getResultList();
    }

    public static List<Socio> getAvailableSocios(Instant start, Instant end) {
    EntityManager entityManager = Database.getEntityManager();
    Query query = entityManager.createQuery(
        "SELECT s FROM Socio s WHERE s.idSocio NOT IN (" +
        "SELECT l.idSocio FROM LinhaAula l JOIN Aula a ON l.idAula = a.id " +
        "WHERE (a.dataHoraComeco BETWEEN :start AND :end) OR " +
        "(a.dataHoraFim BETWEEN :start AND :end) OR " +
        "(a.dataHoraComeco < :start AND a.dataHoraFim > :end))");
    query.setParameter("start", start);
    query.setParameter("end", end);
    return query.getResultList();
}


public static List<Aula> getAulasBySocioId(Integer socioId) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a FROM Aula a JOIN LinhaAula l ON a.id = l.idAula WHERE l.idSocio = :socioId");
        query.setParameter("socioId", socioId);
        return query.getResultList();
    }

    public static void deleteAulaWithLinhaAula(int aulaId) {
        EntityManager entityManager = Database.getEntityManager();
        // Check if a transaction is already active
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        try {
            // Delete all dependent 'linha_aula' records first
            LinhaAulaBLL.deleteAllLinhasAulaByAulaId(aulaId, entityManager);
            // Now, find and delete the 'aula' record
            Aula aula = entityManager.find(Aula.class, aulaId);
            if (aula != null) {
                entityManager.remove(aula);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;  // Rethrow the exception for the caller to handle
        }
    }

    public static List<Funcionario> getAllInstrutores() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT f FROM Funcionario f WHERE f.idTipofuncionario = 1");
        return query.getResultList();
    }
}

