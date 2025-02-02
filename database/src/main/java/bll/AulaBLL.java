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
import org.springframework.stereotype.Service;


import static database.Database.getEntityManager;

@Service
public class AulaBLL {
    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Lisbon");


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
        EntityManager entityManager = Database.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s.nome FROM Socio s JOIN LinhaAula l ON s.idSocio = l.idSocio WHERE l.idAula = :id");
            query.setParameter("id", id);
            String socioNome = (String) query.getSingleResult();
            System.out.println("Sócio Nome para a Aula ID " + id + ": " + socioNome); // Adicione este log
            return socioNome;
        } catch (Exception e) {
            e.printStackTrace();
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

    public static String getLocalByIdAula(int id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a.localAula FROM Aula a WHERE a.id = :id");
        query.setParameter("id", id);
        return (String) query.getSingleResult();
    }

    public static String getTipoAulaByIdAula(int id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT a.totalLugares FROM Aula a WHERE a.id = :id");
        query.setParameter("id", id);
        if ((int) query.getSingleResult() == 1) {
            return "AULA INDIVIDUAL";
        } else {
            return "AULA GRUPO";
        }
    }


    public static List<Funcionario> getAvailableInstructors(Instant start, Instant end) {
        EntityManager entityManager = Database.getEntityManager();
        List<Funcionario> availableInstructors = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();

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
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        try {
            LinhaAulaBLL.deleteAllLinhasAulaByAulaId(aulaId, entityManager);
            Aula aula = entityManager.find(Aula.class, aulaId);
            if (aula != null) {
                entityManager.remove(aula);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    public static List<Funcionario> getAllInstrutores() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT f FROM Funcionario f WHERE f.idTipofuncionario = 1");
        return query.getResultList();
    }

    public static void AdicionarVagaAula(Integer aulaId) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Aula aula = entityManager.find(Aula.class, aulaId);
        aula.setVagas(aula.getVagas() + 1);
        entityManager.getTransaction().commit();
    }

    public static void RemoverVagaAula(Integer aulaId) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Aula aula = entityManager.find(Aula.class, aulaId);
        aula.setVagas(aula.getVagas() - 1);
        entityManager.getTransaction().commit();
    }

    public static void getModalidadeById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT m.modalidade FROM Modalidade m, Aula a WHERE a.idModalidade = m.idModalidade AND a.id = :id");
        query.setParameter("id", id);
        System.out.println(query.getSingleResult());
    }

    public static List<Aula> getAllAulasGrupoSocio(int idsocio) {
        EntityManager entityManager = Database.getEntityManager();

        Socio socio = SocioBLL.findSocioById(idsocio);
        List<Modalidade> modalidades = ModalidadeBLL.getModalidadesDoPlano(socio.getIdPlano());

        List<Aula> aulasDeGrupo = new ArrayList<>();

        for (Modalidade modalidade : modalidades) {
            Query query = entityManager.createQuery("SELECT a FROM Aula a WHERE a.idModalidade = :idModalidade AND a.vagas >= 1");
            query.setParameter("idModalidade", modalidade.getIdModalidade());
            List<Aula> aulas = query.getResultList();
            aulasDeGrupo.addAll(aulas);
        }

        return aulasDeGrupo;
    }

    public static boolean checkIfSocioIsInAula(int idSocio, int idAula) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT l FROM LinhaAula l WHERE l.idSocio = :idSocio AND l.idAula = :idAula");
        query.setParameter("idSocio", idSocio);
        query.setParameter("idAula", idAula);
        return query.getResultList().size() > 0;
    }


    public static void checkAulaStatus() {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Query query = entityManager.createQuery("SELECT a FROM Aula a WHERE a.dataHoraFim < :currentDate AND a.idEstadoaula = 1");
        query.setParameter("currentDate", Instant.now());
        List<Aula> aulas = query.getResultList();
        for (Aula aula : aulas) {
            aula.setIdEstadoaula(2);
            entityManager.merge(aula);
        }
        entityManager.getTransaction().commit();
    }

    public int getNumAlunosByIdAula(Integer id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT COUNT(l.idSocio) FROM LinhaAula l WHERE l.idAula = :id");
        query.setParameter("id", id);
        return ((Number) query.getSingleResult()).intValue();
    }

    public String getEstadoAulaById(Integer id) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT e.estado FROM EstadoAula e, Aula a WHERE a.idEstadoaula = e.idEstadoaula AND a.id = :id");
        query.setParameter("id", id);
        return (String) query.getSingleResult();
    }
    public static void removeAulasPassadas() {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Query query = entityManager.createQuery("SELECT a FROM Aula a WHERE a.dataHoraFim < :currentDate");
        query.setParameter("currentDate", Instant.now());
        List<Aula> aulas = query.getResultList();
        for (Aula aula : aulas) {
            LinhaAulaBLL.deleteAllLinhasAulaByAulaId(aula.getId(), entityManager);
            entityManager.remove(aula);
        }
    }


    public void createLinhaAula(LinhaAula linhaAula) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(linhaAula);
        entityManager.getTransaction().commit();
    }
}


