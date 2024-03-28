package entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "tipo_pagamento", schema = "public", catalog = "GinasioDB")
public class TipoPagamento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_tipopagamento")
    private int idTipopagamento;
    @Basic
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "tipoPagamentoByIdTipopagamento")
    private Collection<Recibo> recibosByIdTipopagamento;

    public int getIdTipopagamento() {
        return idTipopagamento;
    }

    public void setIdTipopagamento(int idTipopagamento) {
        this.idTipopagamento = idTipopagamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipoPagamento that = (TipoPagamento) o;

        if (idTipopagamento != that.idTipopagamento) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTipopagamento;
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    public Collection<Recibo> getRecibosByIdTipopagamento() {
        return recibosByIdTipopagamento;
    }

    public void setRecibosByIdTipopagamento(Collection<Recibo> recibosByIdTipopagamento) {
        this.recibosByIdTipopagamento = recibosByIdTipopagamento;
    }
}
