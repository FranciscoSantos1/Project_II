package entity;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "estado_pagamento", schema = "public", catalog = "GinasioDB")
public class EstadoPagamento {
    @Basic
    @jakarta.persistence.Column(name = "estado")
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_estadopagamento")
    private int idEstadopagamento;

    public int getIdEstadopagamento() {
        return idEstadopagamento;
    }

    public void setIdEstadopagamento(int idEstadopagamento) {
        this.idEstadopagamento = idEstadopagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstadoPagamento that = (EstadoPagamento) o;

        if (idEstadopagamento != that.idEstadopagamento) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = estado != null ? estado.hashCode() : 0;
        result = 31 * result + idEstadopagamento;
        return result;
    }
}
