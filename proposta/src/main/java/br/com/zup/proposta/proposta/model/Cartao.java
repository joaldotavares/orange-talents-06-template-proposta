package br.com.zup.proposta.proposta.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.zup.proposta.proposta.dto.VencimentoDTO;
import br.com.zup.proposta.proposta.model.enums.StatusCartao;

@Entity
public class Cartao {

	@Id
	private String id;
	
	@NotBlank
	private String titular;
	
	@NotNull
	private BigDecimal limite;
	
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss", shape = Shape.STRING)
	private LocalDateTime emitidoEm;
	
	@NotNull
	@Valid
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
	private List<Bloqueio> bloqueios = new ArrayList<>();	
	
	@NotNull
	@Valid
	@OneToMany(mappedBy = "cartao")
	private Set<Biometria> biometrias = new HashSet<>();

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.MERGE)
	private Vencimento vencimento;
	
	@NotNull
	@Valid
	@OneToOne
	private Proposta idProposta;
	
	@Enumerated(EnumType.STRING)
	private StatusCartao statusCartao = StatusCartao.ATIVO;

	public Cartao() {
		super();
	}

	public Cartao(String id, LocalDateTime emitidoEm, String titular, BigDecimal limite, VencimentoDTO vencimento, Proposta idProposta) {
		super();
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.vencimento = vencimento.toModel(this);
		this.idProposta = idProposta;
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

	public Vencimento getVencimento() {
		return vencimento;
	}

	public Proposta getIdProposta() {
		return idProposta;
	}

	public Set<Biometria> getBiometrias() {
		return biometrias;
	}

	public void adicionarBiometria(@Valid Biometria biomeria) {
		this.biometrias.add(biomeria);
	}
	
	public boolean verificarBloqueio() {
		return this.statusCartao.equals(StatusCartao.BLOQUEADO);
	}
	
	public boolean bloquearCartao(HttpServletRequest request) {
		if(statusCartao == StatusCartao.ATIVO) {
			this.statusCartao = StatusCartao.BLOQUEADO;
			this.bloqueios.add(new Bloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"), this));
		}
		return false;
	}
}
