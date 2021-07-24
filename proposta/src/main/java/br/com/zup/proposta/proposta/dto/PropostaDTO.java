package br.com.zup.proposta.proposta.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.security.crypto.encrypt.Encryptors;

import br.com.zup.proposta.proposta.config.validation.CPFouCNPJ;
import br.com.zup.proposta.proposta.model.Proposta;

public class PropostaDTO {

	@NotBlank
	@CPFouCNPJ
	private String documento;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String nome;

	@NotBlank
	private String endereco;

	@NotNull
	@PositiveOrZero
	private BigDecimal salario;

	public PropostaDTO(String documento, String email, String nome, String endereco, BigDecimal salario) {
		super();
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public String getDocumento() {
		return documento;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public Proposta toModel(String secret, String salt) {
		String criptografia = Encryptors.text(secret, salt).encrypt(this.documento);
		return new Proposta(criptografia, email, nome, endereco, salario);
	}
}
