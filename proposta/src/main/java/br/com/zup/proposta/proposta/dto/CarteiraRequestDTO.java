package br.com.zup.proposta.proposta.dto;

import javax.validation.constraints.NotBlank;

public class CarteiraRequestDTO {

	@NotBlank
	private String email;
	
	@NotBlank
	private String nomeCarteira;

	public CarteiraRequestDTO(String nomeCarteira, String email) {
		super();
		this.email = email;
		this.nomeCarteira = nomeCarteira;
	}
	
}
