package br.com.zup.proposta.proposta.interfaces;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.proposta.proposta.dto.CartaoDTO;
import br.com.zup.proposta.proposta.dto.CartaoRequestDTO;
import br.com.zup.proposta.proposta.dto.CarteiraRequestDTO;
import br.com.zup.proposta.proposta.dto.NotificacaoViagem;
import feign.Response;

@FeignClient(name = "cartao", url = "http://localhost:8888")
public interface CartaoClient {

	@RequestMapping(method = RequestMethod.POST, value = "/api/cartoes")
	CartaoDTO cartaoDto(@RequestBody CartaoRequestDTO cartaoRequest);

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/bloqueios")
	public Response cartaoLegado(@PathVariable String id, @RequestBody Map<String, String> sistema);

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/avisos")
	public String notificarSistema(@PathVariable String id, @RequestBody NotificacaoViagem sistema);

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/carteiras")
	public String asocciarCarteira(@PathVariable String id, @RequestBody CarteiraRequestDTO sistema);
}