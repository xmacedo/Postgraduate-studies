package br.com.xmacedo.posprojetoaplicado.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "DTO para respostas com dados da Transação.")
public class TransacaoResponse {
    @Schema(description = "Identificação da transação",
            example = "123456")
    private Long idTransacao;

    @Schema(description = "Valor da transação",
            example = "595.01")
    private BigDecimal valorTransacao;

    @Schema(description = "Pais origem da Transação.",
            example = "Brasil")
    private String paisOrigem;

    @Schema(description = "Inidicação se a transação foi aprovada ou não.",
            example = "true/false")
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
