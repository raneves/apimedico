package br.com.romulo.apimedico.response.medico;

import br.com.romulo.apimedico.response.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
		@NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {

}
