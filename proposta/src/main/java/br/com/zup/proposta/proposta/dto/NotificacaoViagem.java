package br.com.zup.proposta.proposta.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class NotificacaoViagem {

	@NotBlank
	private String destino;

	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss", shape = Shape.STRING)
	@Future
	private LocalDateTime dataTermino;

	public NotificacaoViagem(String destino, LocalDateTime dataTermino) {
		super();
		this.destino = destino;
		this.dataTermino = dataTermino;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDateTime getDataTermino() {
		return dataTermino;
	}

}