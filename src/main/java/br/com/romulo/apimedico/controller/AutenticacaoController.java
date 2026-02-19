package br.com.romulo.apimedico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.romulo.apimedico.dominio.dto.DadosAutenticacao;
import br.com.romulo.apimedico.dominio.entidade.Usuario;
import br.com.romulo.apimedico.infra.security.DadosTokenJWT;
import br.com.romulo.apimedico.infra.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/requisitar-token")
public class AutenticacaoController {
	@Autowired
    private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	//para funcionar tem que criar um usuario e senha no banco de dados
	//para demonstracao criei usuario: romulo, senha: 123456, no entanto
	//a senha vou armazenar criptografada, utilizando o algoritmo BCrpt, ele
	//vai criptografar a senha 123456 e armazenar no banco de dados
	
	//$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
	    var authentication = manager.authenticate(authenticationToken);

	    var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

	    return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}
	
	
}
