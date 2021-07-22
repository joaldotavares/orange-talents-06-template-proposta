package br.com.zup.proposta.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
public class Viagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String destino;

	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@Future
	private LocalDateTime dataTermino;

	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss", shape = Shape.STRING)
	private LocalDateTime instanteAviso;

	@NotBlank
	private String clienteIp;

	@NotBlank
	private String userAgent;

	@NotNull
	@Valid
	@ManyToOne
	private Cartao cartao;

	public Viagem(String destino, LocalDateTime dataTermino, String clienteIp, String userAgent, Cartao cartao) {
		super();
		this.destino = destino;
		this.dataTermino = dataTermino;
		this.instanteAviso = LocalDateTime.now();
		this.clienteIp = clienteIp;
		this.userAgent = userAgent;
		this.cartao = cartao;
	}

	public Long getId() {
		return id;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDateTime getDataTermino() {
		return dataTermino;
	}

	public LocalDateTime getInstanteAviso() {
		return instanteAviso;
	}

	public String getClienteIp() {
		return clienteIp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public Cartao getCartao() {
		return cartao;
	}

}
