package bll;

import entity.Plano;
import jakarta.persistence.EntityManager;
import database.Database;

import java.util.List;

public class PlanoBLL {

    public static Plano findPlanoById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        return entityManager.find(Plano.class, id);
    }

    public static List<Plano> getAllPlanos() {
        EntityManager entityManager = Database.getEntityManager();
        List<Plano> planos = entityManager.createQuery("SELECT p FROM Plano p", Plano.class).getResultList();
        return planos;
    }
}
