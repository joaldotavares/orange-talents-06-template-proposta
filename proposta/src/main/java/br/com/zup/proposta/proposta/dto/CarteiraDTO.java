package br.com.zup.proposta.proposta.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.proposta.proposta.model.Cartao;
import br.com.zup.proposta.proposta.model.Carteira;
import br.com.zup.proposta.proposta.model.enums.NomeCarteira;

public class CarteiraDTO {

	@NotNull
	private NomeCarteira nomeCarteira;

	@NotBlank
	@Email
	private String email;

	public CarteiraDTO(NomeCarteira nomeCarteira, String email) {
		super();
		this.email = email;
		this.nomeCarteira = nomeCarteira;
	}

	public String getEmail() {
		return email;
	}

	public NomeCarteira getNomeCarteira() {
		return nomeCarteira;
	}

	public Carteira toModel(Cartao cartao) {
		return new Carteira(nomeCarteira, email, cartao);
	}

	public CarteiraRequestDTO carteiraSistema() {
		return new CarteiraRequestDTO(nomeCarteira.toString(), email);
	}
}
