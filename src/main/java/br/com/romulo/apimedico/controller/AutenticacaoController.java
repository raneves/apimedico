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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	@Autowired
    private AuthenticationManager manager;
	
	//para funcionar tem que criar um usuario e senha no banco de dados
	//para demonstracao criei usuario: romulo, senha: 123456, no entanto
	//a senha vou armazenar criptografada, utilizando o algoritmo BCrpt, ele
	//vai criptografar a senha 123456 e armazenar no banco de dados
	
	//$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
	    var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
	    var authentication = manager.authenticate(token);

	    return ResponseEntity.ok().build();
	}
	
	
}
