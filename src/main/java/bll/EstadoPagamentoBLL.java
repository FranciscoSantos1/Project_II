package bll;

import entity.EstadoPagamento;
import jakarta.persistence.EntityManager;

public class EstadoPagamentoBLL {

    public static EstadoPagamento findEstadoPagamentoById(int id) {
        EntityManager entityManager = database.Database.getEntityManager();
        return entityManager.find(EstadoPagamento.class, id);
    }
}
