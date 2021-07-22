package br.com.zup.proposta.proposta.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.util.Assert;

import br.com.zup.proposta.proposta.dto.CartaoRequestDTO;
import br.com.zup.proposta.proposta.model.enums.StatusProposta;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String documento;

	@NotBlank
	@Column(nullable = false)
	@Email
	private String email;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@NotBlank
	@Column(nullable = false)
	private String endereco;

	@NotNull
	@Column(nullable = false)
	@PositiveOrZero
	private BigDecimal salario;

	@Enumerated(EnumType.STRING)
	private StatusProposta status;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Cartao cartao;

	public Proposta() {
		super();
	}

	public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
		super();
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
		this.status = StatusProposta.NAO_ELEGIVEL;
	}

	public Long getId() {
		return id;
	}

	public String getDocumento() {
		return documento;
	}

	public StatusProposta getStatus() {
		return status;
	}

	public String getNome() {
		return nome;
	}

	public void atualizarStatusProposta(StatusProposta statusProposta) {
		Assert.isTrue(this.status.equals(StatusProposta.NAO_ELEGIVEL), "Essa proposta é ELEGIVEL");
		this.status = statusProposta;
	}
	
	public CartaoRequestDTO toCartaoRequestDTO() {
		return new CartaoRequestDTO(this);
	}

	public void toCartaoDTO(Cartao cartao) {
		this.cartao = cartao;
	}
}
