package bll;

import entity.Plano;
import jakarta.persistence.EntityManager;
import database.Database;

public class PlanoBLL {

    public static Plano findPlanoById(int id) {
        EntityManager entityManager = Database.getEntityManager();
        return entityManager.find(Plano.class, id);
    }
}
