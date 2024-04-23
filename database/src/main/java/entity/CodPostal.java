package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cod_postal", schema = "public", catalog = "GinasioDB")
public class CodPostal {
    @Basic
    @Column(name = "desc_cod_postal")
    private String descCodPostal;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cod_postal")
    private String codPostal;

    public String getDescCodPostal() {
        return descCodPostal;
    }

    public void setDescCodPostal(String descCodPostal) {
        this.descCodPostal = descCodPostal;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodPostal codPostal1 = (CodPostal) o;

        if (descCodPostal != null ? !descCodPostal.equals(codPostal1.descCodPostal) : codPostal1.descCodPostal != null)
            return false;
        if (codPostal != null ? !codPostal.equals(codPostal1.codPostal) : codPostal1.codPostal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = descCodPostal != null ? descCodPostal.hashCode() : 0;
        result = 31 * result + (codPostal != null ? codPostal.hashCode() : 0);
        return result;
    }
}
