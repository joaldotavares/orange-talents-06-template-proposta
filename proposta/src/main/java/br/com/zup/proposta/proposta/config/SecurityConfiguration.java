package br.com.zup.proposta.proposta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers(HttpMethod.GET, "/actuator/*").permitAll().antMatchers(HttpMethod.GET, "/proposta/")
				.hasAuthority("SCOPE_proposta:read").antMatchers(HttpMethod.POST, "/proposta/")
				.hasAuthority("SCOPE_proposta:write").antMatchers(HttpMethod.POST, "/biometrias")
				.hasAuthority("SCOPE_biometria:write").antMatchers(HttpMethod.POST, "/api/cartoes/{id}/biometrias/")
				.hasAuthority("SCOPE_biometria:write").antMatchers(HttpMethod.POST, "/api/cartoes/{id}/bloqueios/")
				.hasAuthority("SCOPE_proposta:write").anyRequest().authenticated())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}
}
