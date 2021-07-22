package br.com.zup.proposta.proposta.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.proposta.dto.BiometriaDTO;
import br.com.zup.proposta.proposta.model.Biometria;
import br.com.zup.proposta.proposta.model.Cartao;
import br.com.zup.proposta.proposta.repository.BiometriaRepository;
import br.com.zup.proposta.proposta.repository.CartaoRepository;

@RestController
@RequestMapping("/biometrias")
public class BiometriaController {
	
	private final BiometriaRepository biometriaRepository;
	
	private final CartaoRepository cartaoRepository;
	
	public BiometriaController(BiometriaRepository biometriaRepository, CartaoRepository cartaoRepository) {
		super();
		this.biometriaRepository = biometriaRepository;
		this.cartaoRepository = cartaoRepository;
	}

	@PostMapping("/{id}")
	public ResponseEntity<?> inserir(@RequestBody @Valid BiometriaDTO biometriaDto, @PathVariable("id") String idCartao,
			UriComponentsBuilder builder) {
		
		Cartao cartao = extracted(idCartao);
				
		Biometria biometria = biometriaDto.toModel(cartao);
		biometriaRepository.save(biometria);
		
		cartao.adicionarBiometria(biometria);
		cartaoRepository.save(cartao);

		return ResponseEntity.created(builder.path("/biometria/{id}").build(biometria.getId())).build();
	}

	private Cartao extracted(String id) {
		Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return cartao;
	}
}
