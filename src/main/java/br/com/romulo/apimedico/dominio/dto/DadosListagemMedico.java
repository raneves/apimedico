package br.com.romulo.apimedico.dominio.dto;

import br.com.romulo.apimedico.dominio.entidade.Medico;
import br.com.romulo.apimedico.dominio.enuns.Especialidade;

public record DadosListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public DadosListagemMedico(Medico medico) {
        this(medico.getId() ,medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

}