package br.com.romulo.apimedico.medico;

public record DadosCadastroMedico(String nome, String email, String crm, 
		Especialidade especialidade, DadosEndereco endereco) {

}
