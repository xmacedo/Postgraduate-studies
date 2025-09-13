package br.com.xmacedo.posprojetoaplicado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transacao")
    @SequenceGenerator(name = "seq_transacao", sequenceName = "seq_transacao", allocationSize = 1)
    @Column(name = "idTransacao")
    private Long idTransacao;

    @Column(name = "valor_transacao")
    private BigDecimal valorTransacao;
    @Column(name = "pais_origem")
    private String paisOrigem;
    @Column(name = "transacao_aprovada")
    private Boolean transacaoAprovada;

    public Long getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Long idTransacao) {
        this.idTransacao = idTransacao;
    }

    public BigDecimal getValorTransacao() {
        return valorTransacao;
    }

    public void setValorTransacao(BigDecimal valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public Boolean getTransacaoAprovada() {
        return transacaoAprovada;
    }

    public void setTransacaoAprovada(Boolean transacaoAprovada) {
        this.transacaoAprovada = transacaoAprovada;
    }
}
