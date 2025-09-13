package br.com.xmacedo.posprojetoaplicado.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "DTO de request com os dados da Transação.")
public class TransacaoRequest {
    @Schema(description = "Identificação da transação",
            example = "123456")
    private Long idTransacao;

    @Schema(description = "Valor da transação",
            example = "595.01",
            required = true)
    private BigDecimal valorTransacao;

    @Schema(description = "Pais origem da Transação.",
            example = "Brasil",
            required = true)
    private String paisOrigem;

    public Long getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Long idTransacao) {
        this.idTransacao = idTransacao;
    }

    public BigDecimal  getValorTransacao() {
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
}
