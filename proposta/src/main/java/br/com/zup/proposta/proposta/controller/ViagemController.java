package br.com.zup.proposta.proposta.controller;

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

import br.com.zup.proposta.proposta.dto.ViagemDTO;
import br.com.zup.proposta.proposta.model.Cartao;
import br.com.zup.proposta.proposta.repository.CartaoRepository;

@RestController
@RequestMapping("/cartoes")
public class ViagemController {

	private final Logger logger = LoggerFactory.getLogger(Cartao.class);

	private final CartaoRepository cartaoRepository;

	public ViagemController(CartaoRepository cartaoRepository) {
		super();
		this.cartaoRepository = cartaoRepository;
	}

	@PostMapping("/{id}/avisos")
	public ResponseEntity<?> inserir(@PathVariable String id, HttpServletRequest request, ViagemDTO viagemDto) {

		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		cartao.notificarViagem(request, viagemDto);
		cartaoRepository.save(cartao);
		logger.info("Aviso inserido com sucesso");
		return ResponseEntity.ok().build();
	}
}
