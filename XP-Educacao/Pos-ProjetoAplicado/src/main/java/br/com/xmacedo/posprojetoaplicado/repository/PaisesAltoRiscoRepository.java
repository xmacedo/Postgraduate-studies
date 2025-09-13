package br.com.xmacedo.posprojetoaplicado.repository;

import br.com.xmacedo.posprojetoaplicado.model.PaisesAltoRisco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisesAltoRiscoRepository extends JpaRepository<PaisesAltoRisco, Long> {

    PaisesAltoRisco findByPais(String pais);
}
