package bll;

import database.Database;
import entity.Aula;
import entity.LinhaAula;
import entity.Socio;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LinhaAulaBLL {
    public static void createLinhaAula(LinhaAula linhaAula) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(linhaAula);
        entityManager.getTransaction().commit();
    }

    public void deleteLinhaAula(LinhaAula linhaAula) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.remove(linhaAula);
        entityManager.getTransaction().commit();
    }

    public static LinhaAula getLinhaAulaById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        return entityManager.find(LinhaAula.class, id);
    }

    public static void readLinhaAula() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT l FROM LinhaAula l");
        List<LinhaAula> linhaAulas = query.getResultList();
        for (LinhaAula linhaAula : linhaAulas) {
            System.out.println("ID: " + linhaAula.getIdAula() + " ID Aula: " + linhaAula.getIdSocio() + " ID Socio: " + linhaAula.getIdSocio());
        }
    }

    public static void updateLinhaAula(LinhaAula linhaAula) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(linhaAula);
        entityManager.getTransaction().commit();
    }




    public static List<Socio> getSociosByAulaId(int aulaId) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT l FROM LinhaAula l WHERE l.idAula = :aulaId");
        query.setParameter("aulaId", aulaId);
        List<LinhaAula> linhaAulas = query.getResultList();

        List<Socio> socios = new ArrayList<>();
        for (LinhaAula linhaAula : linhaAulas) {
            Socio socio = SocioBLL.findSocioById(linhaAula.getIdSocio());
            if (socio != null) {
                socios.add(socio);
            }
        }

        return socios;
    }

    public static void deleteAllLinhasAulaByAulaId(int aulaId, EntityManager entityManager) {
        Query query = entityManager.createQuery("DELETE FROM LinhaAula l WHERE l.idAula = :aulaId");
        query.setParameter("aulaId", aulaId);
        query.executeUpdate();
    }

}