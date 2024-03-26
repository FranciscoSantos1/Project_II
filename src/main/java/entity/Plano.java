package entity;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
public class Plano {
    @Basic
    @Column(name = "tipo")
    private String tipo;
    @Basic
    @Column(name = "descricao")
    private String descricao;
    @Basic
    @Column(name = "valor")
    private BigInteger valor;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_plano")
    private int idPlano;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plano plano = (Plano) o;

        if (idPlano != plano.idPlano) return false;
        if (tipo != null ? !tipo.equals(plano.tipo) : plano.tipo != null) return false;
        if (descricao != null ? !descricao.equals(plano.descricao) : plano.descricao != null) return false;
        if (valor != null ? !valor.equals(plano.valor) : plano.valor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tipo != null ? tipo.hashCode() : 0;
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        result = 31 * result + idPlano;
        return result;
    }
}
