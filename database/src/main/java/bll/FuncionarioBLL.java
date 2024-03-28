package bll;

import java.util.List;
import jakarta.persistence.*;

import database.Database;
import entity.Funcionario;
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
}
