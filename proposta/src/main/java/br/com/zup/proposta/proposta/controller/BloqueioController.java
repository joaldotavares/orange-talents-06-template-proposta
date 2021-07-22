package br.com.zup.proposta.proposta.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.proposta.proposta.model.Cartao;
import br.com.zup.proposta.proposta.repository.CartaoRepository;

@RestController
@RequestMapping("/cartoes/")
public class BloqueioController {

	private final CartaoRepository cartaoRepository;

	public BloqueioController(CartaoRepository cartaoRepository) {
		super();
		this.cartaoRepository = cartaoRepository;
	}
	
	@PostMapping("/{id}/bloqueios")
	public ResponseEntity<?> inserir(@PathVariable String id, HttpServletRequest request){
		
		Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if(cartao.verificarBloqueio()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}
		
		cartao.bloquearCartao(request);
		cartaoRepository.save(cartao);
		return ResponseEntity.ok().build();
	}
}
