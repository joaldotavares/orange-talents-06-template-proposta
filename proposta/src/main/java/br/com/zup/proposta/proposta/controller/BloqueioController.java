package br.com.zup.proposta.proposta.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.proposta.proposta.interfaces.CartaoClient;
import br.com.zup.proposta.proposta.model.Cartao;
import br.com.zup.proposta.proposta.repository.CartaoRepository;
import feign.Response;

@RestController
@RequestMapping("/cartoes/")
public class BloqueioController {

	private final Logger logger = LoggerFactory.getLogger(Cartao.class);

	private final CartaoRepository cartaoRepository;

	private final CartaoClient cartaoClient;

	public BloqueioController(CartaoRepository cartaoRepository, CartaoClient cartaoClient) {
		super();
		this.cartaoRepository = cartaoRepository;
		this.cartaoClient = cartaoClient;
	}

	@PostMapping("/{id}/bloqueios")
	public ResponseEntity<?> inserir(@PathVariable String id, HttpServletRequest request) {

		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		if (cartao.verificarBloqueio()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		Response feignResponse = cartaoClient.cartaoLegado(id, Map.of("sistemaResponsavel:", "proposta zup"));

		if (feignResponse.status() == 200) {
			logger.info(id, feignResponse.body());
			cartao.bloquearCartao(request);
			cartaoRepository.save(cartao);
			return ResponseEntity.ok().build();
		}

		logger.error("Não é possivel bloquear esse cartão");
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
	}
}
