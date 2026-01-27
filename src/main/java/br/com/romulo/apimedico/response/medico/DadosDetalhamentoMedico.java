package br.com.romulo.apimedico.response.medico;

import br.com.romulo.apimedico.models.Endereco;
import br.com.romulo.apimedico.models.Medico;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone, 
		Especialidade especialidade, Endereco endereco) {
    public DadosDetalhamentoMedico (Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), 
        		medico.getEspecialidade(), medico.getEndereco());

    }
}