package bll;

import entity.EstadoPagamento;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EstadoPagamentoBLL {

    public static EstadoPagamento findEstadoPagamentoById(int id) {
        EntityManager entityManager = database.Database.getEntityManager();
        return entityManager.find(EstadoPagamento.class, id);
    }

    public static List<EstadoPagamento> readEstadoPagamento() {
        EntityManager entityManager = database.Database.getEntityManager();
        try {
            return entityManager.createQuery("SELECT e FROM EstadoPagamento e", EstadoPagamento.class).getResultList();
        } finally {
            //entityManager.close();
        }
    }
}
