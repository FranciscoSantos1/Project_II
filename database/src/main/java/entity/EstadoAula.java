package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_aula", schema = "public", catalog = "GinasioDB")
public class EstadoAula {
    @Basic
    @Column(name = "estado")
    private String estado;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_estadoaula")
    private int idEstadoaula;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdEstadoaula() {
        return idEstadoaula;
    }

    public void setIdEstadoaula(int idEstadoaula) {
        this.idEstadoaula = idEstadoaula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstadoAula that = (EstadoAula) o;

        if (idEstadoaula != that.idEstadoaula) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = estado != null ? estado.hashCode() : 0;
        result = 31 * result + idEstadoaula;
        return result;
    }
}
