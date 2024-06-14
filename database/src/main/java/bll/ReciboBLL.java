package bll;

import database.Database;
import entity.EstadoPagamento;
import entity.Recibo;
import entity.Socio;
import entity.TipoPagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

@Service
public class ReciboBLL {
    public static void createRecibo(Recibo recibo) {
        EntityManager entityManager = Database.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Verifica se a entidade está no contexto de persistência
            if (!entityManager.contains(recibo)) {
                // Se não estiver, associa a entidade com o contexto de persistência usando merge
                recibo = entityManager.merge(recibo);
            }

            entityManager.persist(recibo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Erro ao criar recibo.");
        }
    }

    public static int getNextIdRecibo() {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createQuery("SELECT MAX(r.idRecibo) FROM Recibo r");
        Integer maxId = (Integer) query.getSingleResult();
        if (maxId != null) {
            return maxId + 1;
        } else {
            return 1;
        }
    }
}
