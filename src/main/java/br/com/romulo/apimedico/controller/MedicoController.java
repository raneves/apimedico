package br.com.romulo.apimedico.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.romulo.apimedico.dominio.dto.DadosAtualizacaoMedico;
import br.com.romulo.apimedico.dominio.dto.DadosCadastroMedico;
import br.com.romulo.apimedico.dominio.dto.DadosDetalhamentoMedico;
import br.com.romulo.apimedico.dominio.dto.DadosListagemMedico;
import br.com.romulo.apimedico.dominio.entidade.Medico;
import br.com.romulo.apimedico.dominio.repository.MedicoRepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("medicos")
public class MedicoController {
	 
	@Autowired private MedicoRepository medicoRepository;
	
	 @PostMapping
	 @Transactional
     public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
		 var medico = new Medico(dados);
		 medicoRepository.save(medico);
		 var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		 
		 return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}
	 
//	@GetMapping
//    public List<DadosListagemMedico> listar() {
//		return medicoRepository.findAll().stream().map(DadosListagemMedico::new).toList();
//    }
	 
	 @GetMapping
	 public ResponseEntity<Page<DadosListagemMedico>> listar(Pageable paginacao) {//cuidado com o import org.springframework.data.domain.Pageable
		 //http://localhost:8080/medicos?size=1
		//http://localhost:8080/medicos?size=1&page=1
		 //http://localhost:8080/medicos?sort=nome
		 //http://localhost8080/medicos?sort=crm,desc&size=2&page=1   --> ordenado decrescente
		 //return medicoRepository.findAll(paginacao).map(DadosListagemMedico::new); //carrega todos os registros
		 
		 var page = medicoRepository.findAllByAtivoTrue(paginacao)
                 .map(DadosListagemMedico::new);
		 return ResponseEntity.ok(page);
		 
	 }
	 
	@PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = medicoRepository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
		
		//return ResponseEntity.ok().build();
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
	
	@DeleteMapping("/{id}")	
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		//medicoRepository.deleteById(id); exclui direto
		
		//2. exclusao logica
		var medico = medicoRepository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build(); //204 no content
	}
	
	
	@GetMapping("/{id}")	
	public ResponseEntity detalhar(@PathVariable Long id) {			
		var medico = medicoRepository.getReferenceById(id);       
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
}