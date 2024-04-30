package entity;

import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.Duration;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;

@Entity
    @Table(name = "aula")
    public class Aula {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @ColumnDefault("nextval('aula_id_aula_seq'")
        @Column(name = "id_aula", nullable = false)
        private Integer id;

        @Column(name = "nome", nullable = false, length = 50)
        private String nome;

        @Column(name = "vagas", nullable = false)
        private Integer vagas;

        @Basic
        @Column(name = "id_funcionario")
        private int idFuncionario;

        @Basic
        @Column(name = "id_modalidade")
        private int idModalidade;

        @Basic
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



        public Instant getDataHoraFim() {
            return dataHoraFim;
        }

        public void setDataHoraFim(Instant dataHoraFim) {
            this.dataHoraFim = dataHoraFim;
        }


        public Instant getDataHoraComeco() {
            return dataHoraComeco;
        }

        public void setDataHoraComeco(Instant dataHoraComeco) {
            this.dataHoraComeco = dataHoraComeco;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Integer getVagas() {
            return vagas;
        }

        public void setVagas(Integer vagas) {
            this.vagas = vagas;
        }

        public Integer getIdFuncionario() {
            return idFuncionario;
        }

        public void setIdFuncionario(Integer idFuncionario) {
            this.idFuncionario = idFuncionario;
        }

        public Integer getIdModalidade() {
            return idModalidade;
        }

        public void setIdModalidade(Integer idModalidade) {
            this.idModalidade = idModalidade;
        }

        public Integer getIdEstadoaula() {
            return idEstadoaula;
        }

        public void setIdEstadoaula(Integer idEstadoaula) {
            this.idEstadoaula = idEstadoaula;
        }

        public String getLocalAula() {
            return localAula;
        }

        public void setLocalAula(String localAula) {
            this.localAula = localAula;
        }

        public Integer getNumMinAtletas() {
            return numMinAtletas;
        }

        public void setNumMinAtletas(Integer numMinAtletas) {
            this.numMinAtletas = numMinAtletas;
        }

        public LocalTime getDuracao() {
            return duracao;
        }

        public void setDuracao(LocalTime duracao) {
            this.duracao = duracao;
        }

    }