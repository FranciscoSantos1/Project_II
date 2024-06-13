package bll;

import database.Database;
import entity.Funcionario;
import entity.Plano;
import entity.Socio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class SocioBLL {

    public static void createSocio(Socio socio) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Verifica se a entidade está no contexto de persistência
            if (!entityManager.contains(socio)) {
                // Se não estiver, associa a entidade com o contexto de persistência usando merge
                socio = entityManager.merge(socio);
            }

            entityManager.persist(socio);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            System.out.println("Erro ao criar sócio.");
        } finally {
            // Não é necessário retornar nada em um método void
        }
    }

    public static Socio findSocioByName(String name) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s FROM Socio s WHERE s.nome = :name");
            query.setParameter("name", name);
            return (Socio) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static int getNextSocioId() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT MAX(s.idSocio) FROM Socio s");
        Integer maxId = (Integer) query.getSingleResult();
        if (maxId != null) {
            return maxId + 1;
        } else {
            return 1;
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


    public static void updateSocio(Socio socio) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getConnection();
            if (connection != null) {
                String sql = "UPDATE socio SET nome = ?, contacto = ?, id_plano = ?, cod_postal = ?, rua = ?, n_porta = ? WHERE id_socio = ?";

                statement = connection.prepareStatement(sql);
                statement.setString(1, socio.getNome());
                statement.setObject(2, socio.getContacto());
                statement.setInt(3, socio.getIdPlano());
                statement.setString(4, socio.getCodPostal());
                statement.setString(5, socio.getRua());
                statement.setString(6, socio.getnPorta());
                statement.setInt(7, socio.getIdSocio());
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
        try {
            Query query = entityManager.createQuery("SELECT s FROM Socio s WHERE s.idSocio = :id");
            query.setParameter("id", id);
            return (Socio) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se nenhum resultado for encontrado
        }
    }

    public static List<Object[]> listSocio() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT s.idSocio, s.nome, s.contacto, CONCAT(s.codPostal, ' - ', s.rua, ' - ', s.nPorta) AS morada FROM Socio s WHERE s.ativo = true ORDER BY s.nome");
        List<Object[]> results = query.getResultList();
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
            return null;
        }
    }

    public static void deactivateSocio(int idSocio) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            Query query = entityManager.createQuery("UPDATE Socio s SET s.ativo = false WHERE s.idSocio = :id");
            query.setParameter("id", idSocio);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Socio> getAllSociosWeb() {
        EntityManager entityManager = Database.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s FROM Socio s WHERE s.ativo = true", Socio.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    //get socio by id for web
    public static Socio getSocioById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s FROM Socio s WHERE s.idSocio = :id", Socio.class);
            query.setParameter("id", id);
            return (Socio) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public static List<Socio> getAllAvailableSocios(Instant startInstant, Instant endInstant) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT s FROM Socio s WHERE s.idSocio NOT IN (SELECT a.id FROM Aula a WHERE a.dataHoraComeco >= :start AND a.dataHoraFim <= :end)");
        query.setParameter("start", startInstant);
        query.setParameter("end", endInstant);
        return query.getResultList();
    }
}
