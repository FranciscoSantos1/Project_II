package bll;

import entity.TipoPagamento;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPagamentoBLL {

    public static TipoPagamento findTipoPagamentoById(int id) {
        EntityManager entityManager = database.Database.getEntityManager();
        return entityManager.find(TipoPagamento.class, id);
    }

    public static List<TipoPagamento> readTipoPagamento() {
        EntityManager entityManager = database.Database.getEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM TipoPagamento t", TipoPagamento.class).getResultList();
        } finally {
            //entityManager.close();
        }
    }

    public static void getTipoPagamento() {
        List<TipoPagamento> tipoPagamentoList = readTipoPagamento();
        for (TipoPagamento tipoPagamento : tipoPagamentoList) {
            System.out.println(tipoPagamento.getIdTipopagamento() + " - " + tipoPagamento.getTipo());
        }
    }
}
