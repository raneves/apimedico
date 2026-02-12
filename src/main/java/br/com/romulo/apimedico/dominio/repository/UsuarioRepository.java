package br.com.romulo.apimedico.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.romulo.apimedico.dominio.entidade.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	UserDetails findByLogin(String username);
}
