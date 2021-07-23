package br.com.zup.proposta.proposta.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.proposta.proposta.model.enums.NomeCarteira;

@Entity
public class Carteira {

	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	private NomeCarteira nomeCarteira;

	@NotBlank
	@Email
	private String email;

	@NotNull
	@Valid
	@ManyToOne
	private Cartao cartao;

	public Carteira(NomeCarteira nomeCarteira, @NotBlank @Email String email, @NotNull @Valid Cartao cartao) {
		super();
		this.nomeCarteira = nomeCarteira;
		this.email = email;
		this.cartao = cartao;
	}

	public String getId() {
		return id;
	}

	public NomeCarteira getNomeCarteira() {
		return nomeCarteira;
	}

	public String getEmail() {
		return email;
	}

	public Cartao getCartao() {
		return cartao;
	}

}
