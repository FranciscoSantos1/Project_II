package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class Aula {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_aula")
    private int idAula;
    @Basic
    @Column(name = "nome")
    private String nome;
    @Basic
    @Column(name = "data_hora")
    private Date dataHora;
    @Basic
    @Column(name = "vagas")
    private int vagas;
    @Basic
    @Column(name = "id_funcionario")
    private int idFuncionario;
    @Basic
    @Column(name = "id_modalidade")
    private int idModalidade;
    @Basic
    @Column(name = "id_estadoaula")
    private int idEstadoaula;
    @Basic
    @Column(name = "local_aula")
    private String localAula;
    @Basic
    @Column(name = "num_min_atletas")
    private int numMinAtletas;
    @Basic
    @Column(name = "duracao")
    private Timestamp duracao;

    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
        this.idAula = idAula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(int idModalidade) {
        this.idModalidade = idModalidade;
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

        Aula aula = (Aula) o;

        if (idAula != aula.idAula) return false;
        if (vagas != aula.vagas) return false;
        if (idFuncionario != aula.idFuncionario) return false;
        if (idModalidade != aula.idModalidade) return false;
        if (idEstadoaula != aula.idEstadoaula) return false;
        if (nome != null ? !nome.equals(aula.nome) : aula.nome != null) return false;
        if (dataHora != null ? !dataHora.equals(aula.dataHora) : aula.dataHora != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAula;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (dataHora != null ? dataHora.hashCode() : 0);
        result = 31 * result + vagas;
        result = 31 * result + idFuncionario;
        result = 31 * result + idModalidade;
        result = 31 * result + idEstadoaula;
        return result;
    }

    public String getLocalAula() {
        return localAula;
    }

    public void setLocalAula(String localAula) {
        this.localAula = localAula;
    }

    public int getNumMinAtletas() {
        return numMinAtletas;
    }

    public void setNumMinAtletas(int numMinAtletas) {
        this.numMinAtletas = numMinAtletas;
    }

    public Timestamp getDuracao() {
        return duracao;
    }

    public void setDuracao(Timestamp duracao) {
        this.duracao = duracao;
    }
}
