package br.com.zup.proposta.proposta.dto;

import br.com.zup.proposta.proposta.model.Proposta;

public class CartaoRequestDTO {

	private String documento;

	private String nome;

	private Long idProposta;

	public CartaoRequestDTO() {
		super();
	}

	public CartaoRequestDTO(Proposta proposta) {
		super();
		this.documento = proposta.getDocumento();
		this.nome = proposta.getNome();
		this.idProposta = proposta.getId();
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public Long getIdProposta() {
		return idProposta;
	}

}
