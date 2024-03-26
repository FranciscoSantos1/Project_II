package entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Date;

@Entity
public class Recibo {
    @Basic
    @Column(name = "data_hora_emissao")
    private Date dataHoraEmissao;
    @Basic
    @Column(name = "id_socio")
    private int idSocio;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_recibo")
    private int idRecibo;
    @Basic
    @Column(name = "mes")
    private Date mes;
    @Basic
    @Column(name = "valor")
    private BigInteger valor;
    @Basic
    @Column(name = "id_tipopagamento")
    private int idTipopagamento;
    @Basic
    @Column(name = "id_estadopagamento")
    private int idEstadopagamento;
    @Basic
    @Column(name = "id_plano")
    private int idPlano;
    @Basic
    @Column(name = "id_funcionario")
    private int idFuncionario;
    @ManyToOne
    @JoinColumn(name = "id_plano", referencedColumnName = "id_plano", insertable = false, updatable = false)
    private Plano planoByIdPlano;
    @ManyToOne
    @JoinColumn(name = "id_tipopagamento", referencedColumnName = "id_tipopagamento", insertable = false, updatable = false)
    private TipoPagamento tipoPagamentoByIdTipopagamento;

    public Date getDataHoraEmissao() {
        return dataHoraEmissao;
    }

    public void setDataHoraEmissao(Date dataHoraEmissao) {
        this.dataHoraEmissao = dataHoraEmissao;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public int getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(int idRecibo) {
        this.idRecibo = idRecibo;
    }

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public int getIdTipopagamento() {
        return idTipopagamento;
    }

    public void setIdTipopagamento(int idTipopagamento) {
        this.idTipopagamento = idTipopagamento;
    }

    public int getIdEstadopagamento() {
        return idEstadopagamento;
    }

    public void setIdEstadopagamento(int idEstadopagamento) {
        this.idEstadopagamento = idEstadopagamento;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

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

        Recibo recibo = (Recibo) o;

        if (idSocio != recibo.idSocio) return false;
        if (idRecibo != recibo.idRecibo) return false;
        if (idTipopagamento != recibo.idTipopagamento) return false;
        if (idEstadopagamento != recibo.idEstadopagamento) return false;
        if (idPlano != recibo.idPlano) return false;
        if (idFuncionario != recibo.idFuncionario) return false;
        if (dataHoraEmissao != null ? !dataHoraEmissao.equals(recibo.dataHoraEmissao) : recibo.dataHoraEmissao != null)
            return false;
        if (mes != null ? !mes.equals(recibo.mes) : recibo.mes != null) return false;
        if (valor != null ? !valor.equals(recibo.valor) : recibo.valor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dataHoraEmissao != null ? dataHoraEmissao.hashCode() : 0;
        result = 31 * result + idSocio;
        result = 31 * result + idRecibo;
        result = 31 * result + (mes != null ? mes.hashCode() : 0);
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        result = 31 * result + idTipopagamento;
        result = 31 * result + idEstadopagamento;
        result = 31 * result + idPlano;
        result = 31 * result + idFuncionario;
        return result;
    }

    public Plano getPlanoByIdPlano() {
        return planoByIdPlano;
    }

    public void setPlanoByIdPlano(Plano planoByIdPlano) {
        this.planoByIdPlano = planoByIdPlano;
    }
}
