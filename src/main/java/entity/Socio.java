package entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Socio {
    @Basic
    @Column(name = "nome")
    private String nome;
    @Basic
    @Column(name = "contacto")
    private Integer contacto;
    @Basic
    @Column(name = "rua")
    private String rua;
    @Basic
    @Column(name = "n_porta")
    private String nPorta;
    @Basic
    @Column(name = "id_plano")
    private int idPlano;
    @Basic
    @Column(name = "cod_postal")
    private String codPostal;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_socio")
    private int idSocio;
    @OneToMany(mappedBy = "idSocio")
    private Collection<Recibo> recibosByIdSocio;
    @ManyToOne
    @JoinColumn(name = "id_plano", referencedColumnName = "id_plano", insertable = false, updatable = false)
    private Plano planoByIdPlano;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getContacto() {
        return contacto;
    }

    public void setContacto(Integer contacto) {
        this.contacto = contacto;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getnPorta() {
        return nPorta;
    }

    public void setnPorta(String nPorta) {
        this.nPorta = nPorta;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
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

        Socio socio = (Socio) o;

        if (idPlano != socio.idPlano) return false;
        if (idSocio != socio.idSocio) return false;
        if (nome != null ? !nome.equals(socio.nome) : socio.nome != null) return false;
        if (contacto != null ? !contacto.equals(socio.contacto) : socio.contacto != null) return false;
        if (rua != null ? !rua.equals(socio.rua) : socio.rua != null) return false;
        if (nPorta != null ? !nPorta.equals(socio.nPorta) : socio.nPorta != null) return false;
        if (codPostal != null ? !codPostal.equals(socio.codPostal) : socio.codPostal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nome != null ? nome.hashCode() : 0;
        result = 31 * result + (contacto != null ? contacto.hashCode() : 0);
        result = 31 * result + (rua != null ? rua.hashCode() : 0);
        result = 31 * result + (nPorta != null ? nPorta.hashCode() : 0);
        result = 31 * result + idPlano;
        result = 31 * result + (codPostal != null ? codPostal.hashCode() : 0);
        result = 31 * result + idSocio;
        return result;
    }

    public Collection<Recibo> getRecibosByIdSocio() {
        return recibosByIdSocio;
    }

    public void setRecibosByIdSocio(Collection<Recibo> recibosByIdSocio) {
        this.recibosByIdSocio = recibosByIdSocio;
    }

    public Plano getPlanoByIdPlano() {
        return planoByIdPlano;
    }

    public void setPlanoByIdPlano(Plano planoByIdPlano) {
        this.planoByIdPlano = planoByIdPlano;
    }
}
