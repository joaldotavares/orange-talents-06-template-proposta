package br.com.zup.proposta.proposta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.zup.proposta.proposta.model.Cartao;
import br.com.zup.proposta.proposta.model.Proposta;

public class CartaoDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss", shape = Shape.STRING)
	private LocalDateTime emitidoEm;

	@NotBlank
	private String titular;

	@NotNull
	private BigDecimal limite;

	@NotNull
	private VencimentoDTO vencimento;

	private String propostaId;

	public CartaoDTO() {

	}

	public Cartao toModel(Proposta proposta) {
		return new Cartao(id, emitidoEm, titular, limite, vencimento, proposta);
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public String getTitular() {
		return titular;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public VencimentoDTO getVencimento() {
		return vencimento;
	}

	public String getPropostaId() {
		return propostaId;
	}

}
