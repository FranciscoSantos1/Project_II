package entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class LinhaAulaPK implements Serializable {
    @Column(name = "id_aula")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAula;
    @Column(name = "id_socio")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSocio;

    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
        this.idAula = idAula;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinhaAulaPK that = (LinhaAulaPK) o;

        if (idAula != that.idAula) return false;
        if (idSocio != that.idSocio) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAula;
        result = 31 * result + idSocio;
        return result;
    }
}
