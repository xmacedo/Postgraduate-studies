package br.com.xmacedo.posprojetoaplicado.controller;

import br.com.xmacedo.posprojetoaplicado.model.request.TransacaoRequest;
import br.com.xmacedo.posprojetoaplicado.model.response.TransacaoResponse;
import br.com.xmacedo.posprojetoaplicado.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

@RestController
@Tag(name = "Transacao", description = "API para gerenciamento das transações")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final static Logger LOGGER = Logger.getLogger("TransacaoController");

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/buscarPaisesAltoRisco")
    public ResponseEntity<?> buscaPaisesAltoRisco() {
        return ResponseEntity.ok(transacaoService.buscarPaisesAltoRisco());
    }

    @Operation(summary = "Validar transação recebida.",
              description = "Guarda a transação no banco de dados e inicia o processo de BPM para avaliar a transação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Processo iniciado com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro ao tentar processar a transação, contate um administrador.")
    })
    @PostMapping("/validaTransacao")
    public ResponseEntity<?> validaTransacao(@RequestBody TransacaoRequest transacaoRequest) {
        TransacaoResponse transacaoResponse = transacaoService.processarTransacao(transacaoRequest);
        LOGGER.info("Process STARTED successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoResponse);
    }

    @GetMapping("/{transacaoID}")
    public ResponseEntity<?> buscaTransacao(@PathVariable Integer transacaoID) {
        return ResponseEntity.ok(transacaoService.buscarTransacao(transacaoID));
    }
}
