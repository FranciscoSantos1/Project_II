package entity;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "linha_aula", schema = "public", catalog = "GinasioDB")
@IdClass(entity.LinhaAulaPK.class)
public class LinhaAula {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "id_aula")
    private int idAula;

    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
        this.idAula = idAula;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "id_socio")
    private int idSocio;

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    @Basic
    @Column(name = "id_funcionario")
    private int idFuncionario;

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinhaAula linhaAula = (LinhaAula) o;

        if (idAula != linhaAula.idAula) return false;
        if (idSocio != linhaAula.idSocio) return false;
        if (idFuncionario != linhaAula.idFuncionario) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAula;
        result = 31 * result + idSocio;
        result = 31 * result + idFuncionario;
        return result;
    }
}
