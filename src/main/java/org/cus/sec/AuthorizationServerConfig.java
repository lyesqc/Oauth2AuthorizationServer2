package org.cus.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	// @Value("${authorisation.clientid:'devglan'}")
	static final String CLIEN_ID = "devglan-client";
	@Value("${authorisation.secretid:devglan-secret}")
	static final String CLIENT_SECRET = "devglan-secret";
	static final String GRANT_TYPE_PASSWORD = "password";
	static final String AUTHORIZATION_CODE = "authorization_code";
	static final String REFRESH_TOKEN = "refresh_token";
	static final String IMPLICIT = "implicit";
	static final String SCOPE_READ = "read";
	static final String SCOPE_WRITE = "write";
	static final String TRUST = "trust";
	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60 * 60;
	static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	private BCryptPasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("as466gf");
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
				.accessTokenConverter(accessTokenConverter());
	}

	/*
	 * 
	 * 
	 * @Override public void configure(ClientDetailsServiceConfigurer
	 * configurer) throws Exception {
	 * 
	 * configurer.inMemory().withClient(CLIEN_ID).secret(passwordEncoder.encode(
	 * CLIENT_SECRET)) .authorizedGrantTypes(GRANT_TYPE_PASSWORD,
	 * AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
	 * .scopes(SCOPE_READ).accessTokenValiditySeconds(
	 * ACCESS_TOKEN_VALIDITY_SECONDS)
	 * .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS); }
	 * 
	 * @Override public void configure(AuthorizationServerSecurityConfigurer
	 * security) { security.tokenKeyAccess("permitAll()")
	 * .checkTokenAccess("isAuthenticated()")
	 * .passwordEncoder(this.passwordEncoder); }
	 */

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("clientapp").secret(encoder().encode("123456"))
				.authorizedGrantTypes("password", "authorization_code", "refresh_token").authorities("READ_ONLY_CLIENT")
				.scopes("read_profile_info")
				.redirectUris("http://localhost/").accessTokenValiditySeconds(7200)
				.refreshTokenValiditySeconds(240000);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.allowFormAuthenticationForClients();
	}
	/**
	 * @Bean public FilterRegistrationBean corsFilter() {
	 *       UrlBasedCorsConfigurationSource source = new
	 *       UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
	 *       CorsConfiguration(); config.setAllowCredentials(true);
	 *       config.addAllowedOrigin("*"); config.addAllowedHeader("*");
	 *       config.addAllowedMethod("*");
	 *       source.registerCorsConfiguration("/**", config);
	 *       FilterRegistrationBean bean = new FilterRegistrationBean(new
	 *       CorsFilter(source)); bean.setOrder(0); return bean; }
	 **/
}