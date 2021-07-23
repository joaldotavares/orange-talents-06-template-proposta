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

import br.com.zup.proposta.proposta.dto.CarteiraDTO;
import br.com.zup.proposta.proposta.interfaces.CartaoClient;
import br.com.zup.proposta.proposta.model.Cartao;
import br.com.zup.proposta.proposta.model.Carteira;
import br.com.zup.proposta.proposta.repository.CartaoRepository;
import br.com.zup.proposta.proposta.repository.CarteiraRepository;
import feign.FeignException.FeignClientException;

@RestController
@RequestMapping(value = "/cartoes")
public class CarteiraController {

	private final CartaoRepository cartaoRepository;

	private final CarteiraRepository carteiraRepository;

	private final CartaoClient cartaoClient;

	public CarteiraController(CartaoRepository cartaoRepository, CarteiraRepository carteiraRepository,
			CartaoClient cartaoClient) {
		super();
		this.cartaoRepository = cartaoRepository;
		this.carteiraRepository = carteiraRepository;
		this.cartaoClient = cartaoClient;
	}

	@PostMapping("/{id}/carteiras")
	public ResponseEntity<?> inserir(@PathVariable String id, @RequestBody @Valid CarteiraDTO carteiraDto,
			UriComponentsBuilder builder) {
		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		if (cartao.associarCarteira(carteiraDto.getNomeCarteira())) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		try {

			cartaoClient.asocciarCarteira(id, carteiraDto.carteiraSistema());
			Carteira carteira = carteiraDto.toModel(cartao);
			carteiraRepository.save(carteira);

			return ResponseEntity
					.created(builder.path("/{id}/carteiras/{id}").buildAndExpand(id, carteira.getId()).toUri()).build();

		} catch (FeignClientException e) {

			return ResponseEntity.status(e.status()).build();

		}
	}
}
