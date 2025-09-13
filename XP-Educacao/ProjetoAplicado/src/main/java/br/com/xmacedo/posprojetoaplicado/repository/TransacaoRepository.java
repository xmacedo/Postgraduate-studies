package br.com.xmacedo.posprojetoaplicado.repository;

import br.com.xmacedo.posprojetoaplicado.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
