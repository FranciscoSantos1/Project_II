package bll;

import database.Database;
import entity.Plano;
import entity.Socio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;

public class SocioBLL {

    public static void createSocio(Socio socio) {
        EntityManager entityManager = Database.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {

            transaction.begin();
            Plano plano = PlanoBLL.findPlanoById(socio.getIdPlano());

            if (plano == null) {
                System.out.println("Plano não encontrado para o ID fornecido. Criação de sócio cancelada.");
                return;
            }

            socio.setPlanoByIdPlano(plano);
            entityManager.persist(socio);

            transaction.commit();
            System.out.println("Sócio criado com sucesso!");

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Erro ao criar sócio.");
        } finally {
            entityManager.close();
        }
    }

    public static void deleteSocio(Socio socio) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.remove(socio);
        entityManager.getTransaction().commit();
    }

    public static Socio getSocioById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        return entityManager.find(Socio.class, id);
    }

    public static void updateSocio(Socio socio) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(socio);
        entityManager.getTransaction().commit();
    }

    public static void readSocio() {
        EntityManager entityManager = Database.getEntityManager();
        entityManager.createQuery("SELECT s FROM Socio s").getResultList().forEach(socio -> {
            System.out.println("ID: " + ((Socio) socio).getIdSocio() + " Nome: " + ((Socio) socio).getNome());
        });
    }

    public static Socio findSocioById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        return entityManager.find(Socio.class, id);
    }

    public static List<Object[]> listSocio() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT s.idSocio, s.nome, s.contacto, CONCAT(s.codPostal, ' - ', s.rua, ' - ', s.nPorta) AS morada FROM Socio s ORDER BY s.nome");
        List<Object[]> results = query.getResultList();
        //entityManager.close();
        return results;
    }


}
