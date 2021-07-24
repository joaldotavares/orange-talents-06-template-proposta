package br.com.zup.proposta.proposta.dto;

import org.springframework.security.crypto.encrypt.Encryptors;

import br.com.zup.proposta.proposta.model.Proposta;
import br.com.zup.proposta.proposta.model.enums.StatusProposta;

public class PropostaRequestDTO {

	private Long id;
	private String documento;
	private String nome;
	private StatusProposta statusProposta;
	private String cartao;

	public PropostaRequestDTO(Proposta proposta, String secret, String salt) {
		this.id = proposta.getId();
		this.documento = Encryptors.text(secret, salt).encrypt(proposta.getDocumento());
		this.nome = proposta.getNome();
		this.statusProposta = proposta.getStatus();
		this.cartao = statusProposta.equals(StatusProposta.ELEGIVEL) ? proposta.getCartao().getId()
				: "Proposta não elegivel";
	}

	public Long getId() {
		return id;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public StatusProposta getStatusProposta() {
		return statusProposta;
	}

	public String getCartao() {
		return cartao;
	}
	
}
