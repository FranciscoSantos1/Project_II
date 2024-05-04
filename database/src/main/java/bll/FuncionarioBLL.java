package bll;

import java.time.Instant;
import java.util.List;
import jakarta.persistence.*;
import database.Database;
import entity.Funcionario;
import entity.TipoFuncionario;

public class FuncionarioBLL {

    public static void createFuncionario(Funcionario funcionario) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(funcionario);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager.isOpen()) {
                //entityManager.close();
            }
        }
    }

    public static void deleteFuncionario(Funcionario funcionario) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(funcionario) ? funcionario : entityManager.merge(funcionario));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public static Funcionario getFuncionarioById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            return entityManager.find(Funcionario.class, id);
        } finally {
            entityManager.close();
        }
    }

    public static List<Funcionario> readFuncionario() {
        EntityManager entityManager = Database.getEntityManager();
        try {
            TypedQuery<Funcionario> query = entityManager.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public static void updateFuncionario(Funcionario funcionario) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(funcionario);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public static List<TipoFuncionario> getAllTipoFuncionario() {
        return readEntities(TipoFuncionario.class);
    }

    private static <T> List<T> readEntities(Class<T> entityClass) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            TypedQuery<T> query = entityManager.createQuery("SELECT t FROM " + entityClass.getSimpleName() + " t", entityClass);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public TipoFuncionario findTipoFuncionarioByTipo(String tipo) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM TipoFuncionario t WHERE t.tipo = :tipo", TipoFuncionario.class)
                    .setParameter("tipo", tipo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public static boolean emailExists(String email) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            String queryStr = "SELECT COUNT(f) FROM Funcionario f WHERE f.email = :email";
            Long count = entityManager.createQuery(queryStr, Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        }

    }

    public static boolean verifyLogin(String email, String password) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            String queryStr = "SELECT COUNT(f) FROM Funcionario f WHERE f.email = :email AND f.password = :password";
            Long count = entityManager.createQuery(queryStr, Long.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    public static Funcionario getFuncionarioByEmail(String email) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            return entityManager.createQuery("SELECT f FROM Funcionario f WHERE f.email = :email", Funcionario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static Funcionario getTipoFuncionarioByIdTipoFuncionario(int id) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            return entityManager.createQuery("SELECT f FROM Funcionario f WHERE f.id = :id", Funcionario.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public static List<Funcionario> getAllFuncionarios() {
        EntityManager entityManager = Database.getEntityManager();
        try {
            TypedQuery<Funcionario> query = entityManager.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
            return query.getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFuncionarioNameById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            return entityManager.createQuery("SELECT f.nome FROM Funcionario f WHERE f.id = :id", String.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static List<Funcionario> getAllInstrutores() {
        EntityManager entityManager = Database.getEntityManager();
        try {
            return entityManager.createQuery("SELECT f FROM Funcionario f WHERE f.idTipofuncionario = 1", Funcionario.class)
                    .getResultList();
        } finally {
            //entityManager.close();
        }
    }
}
