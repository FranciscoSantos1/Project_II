package bll;

import java.util.List;
import jakarta.persistence.*;

import database.Database;
import entity.Funcionario;
import entity.TipoFuncionario;
import jakarta.persistence.EntityManager;

public class FuncionarioBLL {

    public static void createFuncionario(Funcionario funcionario) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(funcionario);
        entityManager.getTransaction().commit();
    }

    public static void deleteFuncionario(Funcionario funcionario) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.remove(funcionario);
        entityManager.getTransaction().commit();
    }

    public static Funcionario getFuncionarioById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        return entityManager.find(Funcionario.class, id);
    }

    public static void readFuncionario() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT f FROM Funcionario f");
        List<Funcionario> funcionarios = query.getResultList();
        for (Funcionario funcionario : funcionarios) {
            System.out.println("ID: " + funcionario.getIdFuncionario() + " Nome: " + funcionario.getNome());
        }
    }

    public static void updateFuncionario(Funcionario funcionario) {
        EntityManager entityManager = Database.getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(funcionario);
        entityManager.getTransaction().commit();
    }

    public static List<TipoFuncionario> getAllTipoFuncionario() {
        EntityManager entityManager = Database.getEntityManager();
        try {
            TypedQuery<TipoFuncionario> query = entityManager.createQuery("SELECT t FROM TipoFuncionario t", TipoFuncionario.class);
            return query.getResultList();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }


    public TipoFuncionario findTipoFuncionarioByTipo(String tipo) {
        EntityManager entityManager = Database.getEntityManager();
        try {
            TypedQuery<TipoFuncionario> query = entityManager.createQuery(
                    "SELECT t FROM TipoFuncionario t WHERE t.tipo = :tipo", TipoFuncionario.class);
            query.setParameter("tipo", tipo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // or handle it according to your business logic
        } finally {
            entityManager.close();
        }
    }

}
