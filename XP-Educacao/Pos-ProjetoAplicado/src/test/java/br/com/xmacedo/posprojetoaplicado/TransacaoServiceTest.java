package br.com.xmacedo.posprojetoaplicado;

import br.com.xmacedo.posprojetoaplicado.model.PaisesAltoRisco;
import br.com.xmacedo.posprojetoaplicado.model.Transacao;
import br.com.xmacedo.posprojetoaplicado.model.request.TransacaoRequest;
import br.com.xmacedo.posprojetoaplicado.model.response.PaisesAltoRiscoResponse;
import br.com.xmacedo.posprojetoaplicado.model.response.TransacaoResponse;
import br.com.xmacedo.posprojetoaplicado.repository.PaisesAltoRiscoRepository;
import br.com.xmacedo.posprojetoaplicado.repository.TransacaoRepository;
import br.com.xmacedo.posprojetoaplicado.service.TransacaoService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private PaisesAltoRiscoRepository paisesAltoRiscoRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ProcessEngine processEngine;

    @Mock
    private RuntimeService runtimeService;

    @Mock
    private ProcessInstantiationBuilder processInstantiationBuilder;

    @InjectMocks
    private TransacaoService transacaoService;

    @BeforeEach
    void setup() {
        // Mock do fluxo do Camunda
        when(processEngine.getRuntimeService()).thenReturn(runtimeService);
        when(runtimeService.createProcessInstanceByKey(anyString())).thenReturn(processInstantiationBuilder);
        when(processInstantiationBuilder.setVariable(anyString(), any())).thenReturn(processInstantiationBuilder);
        when(processInstantiationBuilder.executeWithVariablesInReturn()).thenReturn(null);
    }

    @Test
    void deveBuscarPaisesAltoRisco() {
        PaisesAltoRisco paisAltoRiscoA = new PaisesAltoRisco();
        paisAltoRiscoA.setId(1L);
        paisAltoRiscoA.setPais("Irã");

        PaisesAltoRisco paisAltoRiscoB = new PaisesAltoRisco();
        paisAltoRiscoB.setId(2L);
        paisAltoRiscoB.setPais("Coreia do Norte");
        
        when(paisesAltoRiscoRepository.findAll()).thenReturn(Arrays.asList(paisAltoRiscoA, paisAltoRiscoB));

        PaisesAltoRiscoResponse response = transacaoService.buscarPaisesAltoRisco();

        assertNotNull(response);
        assertEquals(2, response.getPais().size());
        assertTrue(response.getPais().contains("Irã"));
        assertTrue(response.getPais().contains("Coreia do Norte"));
    }

    @Test
    void deveProcessarTransacao() {
        TransacaoRequest request = new TransacaoRequest();
        request.setValorTransacao(new BigDecimal("1000"));
        request.setPaisOrigem("Brasil");

        Transacao transacaoSalva = new Transacao();
        transacaoSalva.setIdTransacao(1L);
        transacaoSalva.setValorTransacao(request.getValorTransacao());
        transacaoSalva.setPaisOrigem(request.getPaisOrigem());
        transacaoSalva.setTransacaoAprovada(true);

        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacaoSalva);

        TransacaoResponse response = transacaoService.processarTransacao(request);

        verify(runtimeService).createProcessInstanceByKey("valida-transacao");
        verify(processInstantiationBuilder).setVariable("valorTransacao", request.getValorTransacao());
        verify(processInstantiationBuilder).setVariable("paisOrigem", request.getPaisOrigem());
        verify(processInstantiationBuilder).executeWithVariablesInReturn();

        assertNotNull(response);
        assertEquals(1L, response.getIdTransacao());
        assertEquals(new BigDecimal("1000"), response.getValorTransacao());
        assertEquals("Brasil", response.getPaisOrigem());
        assertTrue(response.getTransacaoAprovada());
    }

    @Test
    void deveBuscarTransacaoExistente() {
        Transacao transacao = new Transacao();
        transacao.setIdTransacao(1L);
        transacao.setValorTransacao(new BigDecimal("200"));
        transacao.setPaisOrigem("Japão");
        transacao.setTransacaoAprovada(false);

        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));

        TransacaoResponse response = transacaoService.buscarTransacao(1);

        assertNotNull(response);
        assertEquals(1L, response.getIdTransacao());
        assertEquals(new BigDecimal("200"), response.getValorTransacao());
        assertEquals("Japão", response.getPaisOrigem());
        assertFalse(response.getTransacaoAprovada());
    }

    @Test
    void deveRetornarNullQuandoTransacaoNaoExistir() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.empty());

        TransacaoResponse response = transacaoService.buscarTransacao(1);

        assertNull(response);
    }
}

