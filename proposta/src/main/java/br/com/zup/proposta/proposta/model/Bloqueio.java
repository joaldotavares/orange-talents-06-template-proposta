package br.com.zup.proposta.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
public class Bloqueio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss", shape = Shape.STRING)
	private LocalDateTime bloqueadoEm;

	@NotBlank
	private String clienteIp;

	@NotBlank
	private String userAgente;

	@NotNull
	@ManyToOne
	@Valid
	private Cartao cartao;

	public Bloqueio() {
		super();
	}
	
	public Bloqueio(String clienteIp, String userAgente, Cartao cartao) {
		super();
		this.bloqueadoEm = LocalDateTime.now();
		this.clienteIp = clienteIp;
		this.userAgente = userAgente;
		this.cartao = cartao;
	}
	
	public Long getId() {
		return id;
	}

	public LocalDateTime getBloqueadoEm() {
		return bloqueadoEm;
	}
	
	public Cartao getCartao() {
		return cartao;
	}
}
