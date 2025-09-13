package br.com.xmacedo.posprojetoaplicado.service;

import br.com.xmacedo.posprojetoaplicado.model.PaisesAltoRisco;
import br.com.xmacedo.posprojetoaplicado.model.Transacao;
import br.com.xmacedo.posprojetoaplicado.model.request.TransacaoRequest;
import br.com.xmacedo.posprojetoaplicado.model.response.PaisesAltoRiscoResponse;
import br.com.xmacedo.posprojetoaplicado.model.response.TransacaoResponse;
import br.com.xmacedo.posprojetoaplicado.repository.PaisesAltoRiscoRepository;
import br.com.xmacedo.posprojetoaplicado.repository.TransacaoRepository;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final static Logger LOGGER = Logger.getLogger("TransacaoService");

    private PaisesAltoRiscoRepository paisesAltoRiscoRepository;
    private TransacaoRepository transacaoRepository;

    public TransacaoService(PaisesAltoRiscoRepository paisesAltoRiscoRepository, TransacaoRepository transacaoRepository) {
        this.paisesAltoRiscoRepository = paisesAltoRiscoRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public PaisesAltoRiscoResponse buscarPaisesAltoRisco() {
        List<PaisesAltoRisco> all = paisesAltoRiscoRepository.findAll();
        PaisesAltoRiscoResponse response = new PaisesAltoRiscoResponse();
        response.setPais(all.stream().map(PaisesAltoRisco::getPais).collect(Collectors.toList()));
        return response;
    }

    public TransacaoResponse processarTransacao(TransacaoRequest transacaoRequest) {

        try {
            ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
            ProcessInstantiationBuilder instance = engine.getRuntimeService().createProcessInstanceByKey("valida-transacao");


            instance.setVariable("valorTransacao", transacaoRequest.getValorTransacao());
            instance.setVariable("paisOrigem", transacaoRequest.getPaisOrigem());
            instance.executeWithVariablesInReturn();

            LOGGER.info("BPM STARTED successfully");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ERROR to started BPM: ", ex);
        }

        Transacao transacao = salvarTransacao(transacaoRequest);

        LOGGER.info("Transaction SAVED successfully");

        return buildTransacaoResponse(transacao);
    }

    public TransacaoResponse buscarTransacao(Integer transacaoID) {

        LOGGER.info("Transaction SAVED successfully");

        Optional<Transacao> transacaoById = transacaoRepository.findById(Long.valueOf(transacaoID));

        if (transacaoById.isPresent()) {
            return buildTransacaoResponse(transacaoById.get());
        }

        return null;
    }

    private TransacaoResponse buildTransacaoResponse(Transacao transacao) {
        TransacaoResponse transacaoResponse = new TransacaoResponse();
        transacaoResponse.setIdTransacao(transacao.getIdTransacao());
        transacaoResponse.setValorTransacao(transacao.getValorTransacao());
        transacaoResponse.setPaisOrigem(transacao.getPaisOrigem());
        transacaoResponse.setTransacaoAprovada(transacao.getTransacaoAprovada());

        return transacaoResponse;
    }

    private Transacao salvarTransacao(TransacaoRequest transacaoRequest) {
        Transacao transacao = new Transacao();

        transacao.setValorTransacao(transacaoRequest.getValorTransacao());
        transacao.setPaisOrigem(transacaoRequest.getPaisOrigem());

        return transacaoRepository.save(transacao);
    }

}
