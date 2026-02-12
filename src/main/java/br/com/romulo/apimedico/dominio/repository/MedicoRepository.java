package br.com.romulo.apimedico.dominio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.romulo.apimedico.dominio.entidade.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
	Page<Medico> findAllByAtivoTrue(Pageable paginacao);
}
