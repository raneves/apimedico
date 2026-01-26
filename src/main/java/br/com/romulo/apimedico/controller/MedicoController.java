package br.com.romulo.apimedico.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.romulo.apimedico.models.Medico;
import br.com.romulo.apimedico.repository.MedicoRepository;
import br.com.romulo.apimedico.response.medico.DadosAtualizacaoMedico;
import br.com.romulo.apimedico.response.medico.DadosCadastroMedico;
import br.com.romulo.apimedico.response.medico.DadosListagemMedico;
import jakarta.validation.Valid;
import lombok.experimental.var;

@RestController
@RequestMapping("medicos")
public class MedicoController {
	 
	@Autowired private MedicoRepository medicoRepository;
	
	 @PostMapping
	 @Transactional
     public void cadastrar(@RequestBody DadosCadastroMedico dados) {
		 medicoRepository.save(new Medico(dados));
	}
	 
//	@GetMapping
//    public List<DadosListagemMedico> listar() {
//		return medicoRepository.findAll().stream().map(DadosListagemMedico::new).toList();
//    }
	 
	 @GetMapping
	 public Page<DadosListagemMedico> listar(Pageable paginacao) {//cuidado com o import org.springframework.data.domain.Pageable
		 //http://localhost:8080/medicos?size=1
		//http://localhost:8080/medicos?size=1&page=1
		 //http://localhost:8080/medicos?sort=nome
		 //http://localhost8080/medicos?sort=crm,desc&size=2&page=1   --> ordenado decrescente
		 //return medicoRepository.findAll(paginacao).map(DadosListagemMedico::new); //carrega todos os registros
		 
		 return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //retorna apenas os ativos
		 
	 }
	 
	@PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = medicoRepository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
    }
	
	@DeleteMapping("/{id}")	
	@Transactional
	public void excluir(@PathVariable Long id) {
		//medicoRepository.deleteById(id); exclui direto
		
		//2. exclusao logica
		var medico = medicoRepository.getReferenceById(id);
        medico.excluir();
	}
}