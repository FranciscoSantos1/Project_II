package entity;

import jakarta.persistence.Column;

import java.io.Serializable;

public class ModalidadesPlanoPK implements Serializable {
    @Column(name = "id_modalidade")
    private int idModalidade;

    @Column(name = "id_plano")
    private int idPlano;
}
