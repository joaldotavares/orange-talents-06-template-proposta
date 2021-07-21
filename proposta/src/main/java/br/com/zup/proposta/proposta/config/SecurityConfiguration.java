package br.com.zup.proposta.proposta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers(HttpMethod.GET, "/actuator/*").permitAll()
				.antMatchers(HttpMethod.GET, "/proposta/").hasAuthority("SCOPE_proposta:read")
				.antMatchers(HttpMethod.POST, "/proposta/").hasAuthority("SCOPE_proposta:write")
				.antMatchers(HttpMethod.POST, "/biometrias").hasAuthority("SCOPE_biometrias:write")
				.anyRequest().authenticated()
				).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}
}
