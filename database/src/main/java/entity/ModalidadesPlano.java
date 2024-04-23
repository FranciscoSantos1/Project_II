package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "modalidades_plano", schema = "public", catalog = "GinasioDB")
@IdClass(ModalidadesPlanoPK.class)
public class ModalidadesPlano {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_plano")
    private int idPlano;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_modalidade")
    private int idModalidade;

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public int getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(int idModalidade) {
        this.idModalidade = idModalidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModalidadesPlano that = (ModalidadesPlano) o;

        if (idPlano != that.idPlano) return false;
        if (idModalidade != that.idModalidade) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPlano;
        result = 31 * result + idModalidade;
        return result;
    }
}
