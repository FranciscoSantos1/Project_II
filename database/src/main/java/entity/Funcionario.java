package entity;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
public class Funcionario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_funcionario")
    private int idFuncionario;
    @Basic
    @Column(name = "nome")
    private String nome;
    @Basic
    @Column(name = "contacto")
    private String contacto;
    @Basic
    @Column(name = "rua")
    private String rua;
    @Basic
    @Column(name = "n_porta")
    private String nPorta;
    @Basic
    @Column(name = "valor_hora")
    private BigInteger valorHora;
    @Basic
    @Column(name = "vencimento")
    private BigInteger vencimento;
    @Basic
    @Column(name = "cod_postal")
    private String codPostal;
    @Basic
    @Column(name = "id_tipofuncionario")
    private int idTipofuncionario;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
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

    public BigInteger getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigInteger valorHora) {
        this.valorHora = valorHora;
    }

    public BigInteger getVencimento() {
        return vencimento;
    }

    public void setVencimento(BigInteger vencimento) {
        this.vencimento = vencimento;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public int getIdTipofuncionario() {
        return idTipofuncionario;
    }

    public void setIdTipofuncionario(int idTipofuncionario) {
        this.idTipofuncionario = idTipofuncionario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Funcionario that = (Funcionario) o;

        if (idFuncionario != that.idFuncionario) return false;
        if (idTipofuncionario != that.idTipofuncionario) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (contacto != null ? !contacto.equals(that.contacto) : that.contacto != null) return false;
        if (rua != null ? !rua.equals(that.rua) : that.rua != null) return false;
        if (nPorta != null ? !nPorta.equals(that.nPorta) : that.nPorta != null) return false;
        if (valorHora != null ? !valorHora.equals(that.valorHora) : that.valorHora != null) return false;
        if (vencimento != null ? !vencimento.equals(that.vencimento) : that.vencimento != null) return false;
        if (codPostal != null ? !codPostal.equals(that.codPostal) : that.codPostal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFuncionario;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (contacto != null ? contacto.hashCode() : 0);
        result = 31 * result + (rua != null ? rua.hashCode() : 0);
        result = 31 * result + (nPorta != null ? nPorta.hashCode() : 0);
        result = 31 * result + (valorHora != null ? valorHora.hashCode() : 0);
        result = 31 * result + (vencimento != null ? vencimento.hashCode() : 0);
        result = 31 * result + (codPostal != null ? codPostal.hashCode() : 0);
        result = 31 * result + idTipofuncionario;
        return result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
