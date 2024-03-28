package bll;

import entity.TipoPagamento;
import jakarta.persistence.EntityManager;

public class TipoPagamentoBLL {

    public static TipoPagamento findTipoPagamentoById(int id) {
        EntityManager entityManager = database.Database.getEntityManager();
        return entityManager.find(TipoPagamento.class, id);
    }
}
