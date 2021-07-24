package br.com.zup.proposta.proposta.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.proposta.dto.CartaoDTO;
import br.com.zup.proposta.proposta.dto.PropostaDTO;
import br.com.zup.proposta.proposta.interfaces.CartaoClient;
import br.com.zup.proposta.proposta.model.Proposta;
import br.com.zup.proposta.proposta.model.enums.StatusProposta;
import br.com.zup.proposta.proposta.repository.PropostaRepository;

@RestController
@RequestMapping(value = "/proposta")
public class PropostaController {

	private final Logger logger = LoggerFactory.getLogger(Proposta.class);

	private final PropostaRepository propostaRepository;

	private final CartaoClient cartaoClient;

	@Value("${criptografia.secret}")
	private String secret;

	@Value("${criptografia.salt}")
	private String salt;

	public PropostaController(PropostaRepository propostaRepository, CartaoClient cartaoClient) {
		super();
		this.propostaRepository = propostaRepository;
		this.cartaoClient = cartaoClient;
	}

	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody @Valid PropostaDTO dto, UriComponentsBuilder builder) {

		Proposta proposta = dto.toModel(secret, salt);
		if (proposta.verificarProposta(propostaRepository, secret, salt)) {
			logger.error("Erro ao inserir proposta com o CPF/CNPJ={}", dto.getDocumento());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		propostaRepository.save(proposta);
		logger.info("Cadastrada com sucesso a proposta com o CPF/CNPJ={}", proposta.getDocumento());

		return ResponseEntity.created(builder.path("proposta/{id}").buildAndExpand(proposta.getId()).toUri()).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obter(@PathVariable Long id) {
		Optional<Proposta> proposta = propostaRepository.findById(id);

		if (proposta.isPresent()) {
			return ResponseEntity.ok(proposta);
		}
		logger.error("Não existe proposta com esse id");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@Scheduled(fixedDelayString = "5000")
	@Transactional
	public void inserirCartao() {
		List<Proposta> propostas = propostaRepository.findByStatus(StatusProposta.ELEGIVEL);

		while (propostas.size() > 0) {
			Proposta proposta = propostas.get(0);
			CartaoDTO cartaoDto = cartaoClient.cartaoDto(proposta.toCartaoRequestDTO());
			proposta.toCartaoDTO(cartaoDto.toModel(proposta));

			propostaRepository.save(proposta);
			propostas.remove(0);
		}

		logger.info("Fim de tudo");
	}
}
