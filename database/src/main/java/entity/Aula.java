package entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "aula")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "vagas", nullable = false)
    private Integer vagas;

    @Column(name = "id_funcionario")
    private int idFuncionario;

    @Column(name = "id_modalidade")
    private int idModalidade;

    @Column(name = "id_estadoaula")
    private int idEstadoaula;

    @Column(name = "local_aula", nullable = false, length = 50)
    private String localAula;

    @Column(name = "num_min_atletas", nullable = false)
    private Integer numMinAtletas;

    @Column(name = "data_hora_comeco", nullable = false)
    private Instant dataHoraComeco;

    @Column(name = "data_hora_fim", nullable = false)
    private Instant dataHoraFim;

    @Column(name = "duracao", nullable = false)
    private LocalTime duracao;

    @Column(name = "total_lugares", nullable = false)
    private Integer totalLugares;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getVagas() { return vagas; }
    public void setVagas(Integer vagas) { this.vagas = vagas; }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    public int getIdModalidade() { return idModalidade; }
    public void setIdModalidade(int idModalidade) { this.idModalidade = idModalidade; }

    public int getIdEstadoaula() { return idEstadoaula; }
    public void setIdEstadoaula(int idEstadoaula) { this.idEstadoaula = idEstadoaula; }

    public String getLocalAula() { return localAula; }
    public void setLocalAula(String localAula) { this.localAula = localAula; }

    public Integer getNumMinAtletas() { return numMinAtletas; }
    public void setNumMinAtletas(Integer numMinAtletas) { this.numMinAtletas = numMinAtletas; }

    public Instant getDataHoraComeco() { return dataHoraComeco; }
    public void setDataHoraComeco(Instant dataHoraComeco) { this.dataHoraComeco = dataHoraComeco; }

    public Instant getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(Instant dataHoraFim) { this.dataHoraFim = dataHoraFim; }

    public LocalTime getDuracao() { return duracao; }
    public void setDuracao(LocalTime duracao) { this.duracao = duracao; }

    public Integer getTotalLugares() { return totalLugares; }
    public void setTotalLugares(Integer totalLugares) { this.totalLugares = totalLugares; }
}
