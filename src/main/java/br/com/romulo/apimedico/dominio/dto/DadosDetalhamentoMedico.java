package br.com.romulo.apimedico.dominio.dto;

import br.com.romulo.apimedico.dominio.entidade.Endereco;
import br.com.romulo.apimedico.dominio.entidade.Medico;
import br.com.romulo.apimedico.dominio.enuns.Especialidade;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone, 
		Especialidade especialidade, Endereco endereco) {
    public DadosDetalhamentoMedico (Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), 
        		medico.getEspecialidade(), medico.getEndereco());

    }
}