package br.com.zup.proposta.proposta.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.proposta.proposta.dto.ViagemDTO;
import br.com.zup.proposta.proposta.interfaces.CartaoClient;
import br.com.zup.proposta.proposta.model.Cartao;
import br.com.zup.proposta.proposta.repository.CartaoRepository;
import feign.FeignException.FeignClientException;

@RestController
@RequestMapping("/cartoes")
public class ViagemController {

	private final CartaoRepository cartaoRepository;

	private final CartaoClient cartaoClient;

	public ViagemController(CartaoRepository cartaoRepository, CartaoClient cartaoClient) {
		super();
		this.cartaoRepository = cartaoRepository;
		this.cartaoClient = cartaoClient;
	}

	@PostMapping("/{id}/avisos")
	public ResponseEntity<?> inserir(@PathVariable String id, HttpServletRequest request, ViagemDTO viagemDto) {
		Cartao cartao = cartaoRepository.findById(id).orElseThrow();

		try {
			cartaoClient.notificarSistema(id, viagemDto.toNotificacao());
			cartao.notificarViagem(request, viagemDto);
			cartaoRepository.save(cartao);
			return ResponseEntity.ok().build();
		} catch (FeignClientException e) {
			return ResponseEntity.status(e.status()).build();
		}

	}
}
