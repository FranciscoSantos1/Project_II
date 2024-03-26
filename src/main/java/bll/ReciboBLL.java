package bll;

import database.Database;
import entity.EstadoPagamento;
import entity.Recibo;
import entity.Socio;
import entity.TipoPagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ReciboBLL {
    public static void createRecibo(Recibo recibo) {
        EntityManager entityManager = Database.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Socio socio = SocioBLL.findSocioById(recibo.getIdSocio());
            TipoPagamento tipoPagamento = TipoPagamentoBLL.findTipoPagamentoById(recibo.getIdTipopagamento());
            EstadoPagamento estadoPagamento = EstadoPagamentoBLL.findEstadoPagamentoById(recibo.getIdEstadopagamento());

            if (socio == null || tipoPagamento == null || estadoPagamento == null) {
                System.out.println("Socio ou TipoPagamento ou EstadoPagamento não encontrados para os IDs fornecidos. Criação de recibo cancelada.");
                return;
            }

            recibo.setIdSocio(socio.getIdSocio());
            recibo.setIdTipopagamento(tipoPagamento.getIdTipopagamento());
            recibo.setIdEstadopagamento(estadoPagamento.getIdEstadopagamento());
            entityManager.persist(recibo);

            transaction.commit();
            System.out.println("Recibo criado com sucesso!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Erro ao criar recibo.");
        } finally {
            entityManager.close();
        }
    }

}
