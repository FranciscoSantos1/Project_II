package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_funcionario", schema = "public", catalog = "GinasioDB")
public class TipoFuncionario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_tipofuncionario")
    private int idTipofuncionario;
    @Basic
    @Column(name = "tipo")
    private String tipo;

    public int getIdTipofuncionario() {
        return idTipofuncionario;
    }

    public void setIdTipofuncionario(int idTipofuncionario) {
        this.idTipofuncionario = idTipofuncionario;
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

        TipoFuncionario that = (TipoFuncionario) o;

        if (idTipofuncionario != that.idTipofuncionario) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTipofuncionario;
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }
}
