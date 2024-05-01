package bll;

import database.Database;
import entity.Plano;
import entity.Socio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            return;
        }
    }

    /*public static void deleteSocio(Socio socio) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.remove(socio);
        entityManager.getTransaction().commit();
    }*/

    /*public static Socio getSocioById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        return entityManager.find(Socio.class, id);
    }*/

    public static void updateSocio(Socio socio) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getConnection();
            if (connection != null) {
                String sql = "UPDATE socio SET nome = ?, contacto = ?, id_plano = ?,  WHERE id_socio = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, socio.getNome());
                statement.setObject(2, socio.getContacto());
                statement.setInt(3, socio.getIdPlano());
                statement.setInt(4, socio.getIdSocio());
                statement.executeUpdate();
            } else {
                System.out.println("Não foi possível obter a conexão com o banco de dados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return;
        }
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

    //get plano by id
    public static Plano findPlanoById(int id) {
        EntityManager entityManager = Database.getEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT p FROM Plano p WHERE p.idPlano = :id");
            query.setParameter("id", id);
            return (Plano) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se nenhum resultado for encontrado
        }
    }

    //check if the socio has updated the plano in the database
    public static boolean checkIfSocioHasUpdatedPlano(int idSocio, int idPlano) {
        EntityManager entityManager = Database.getEntityManager();
        Socio socio = entityManager.find(Socio.class, idSocio);
        return socio.getIdPlano() == idPlano;
    }

    public static void deactivateSocio(int idSocio) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getConnection();
            if (connection != null) {
                String sql = "DELETE FROM socio WHERE id_socio = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, idSocio);
                statement.executeUpdate();
            } else {
                System.out.println("Não foi possível obter a conexão com o banco de dados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return;
        }
    }

}
