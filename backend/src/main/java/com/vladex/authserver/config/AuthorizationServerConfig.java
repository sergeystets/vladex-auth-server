package com.vladex.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Value("${app.security.jwt.keystore-location}")
  private String keyStorePath;

  @Value("${app.security.jwt.keystore-password}")
  private String keyStorePassword;

  @Value("${app.security.jwt.key-alias}")
  private String keyAlias;

  @Value("${app.security.jwt.private-key-passphrase}")
  private String privateKeyPassphrase;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenEnhancer jwtTokenEnhancer;

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    final TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    enhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer, accessTokenConverter()));
    endpoints
        .tokenStore(tokenStore()).tokenEnhancer(enhancerChain)
        .accessTokenConverter(accessTokenConverter())
        .authenticationManager(authenticationManager);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.allowFormAuthenticationForClients()
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient("vladex-web-ui")
        .authorizedGrantTypes("implicit")
        .authorities("USER")
        .scopes("API")
        .redirectUris("http://localhost:8080/sign-in/success", "http://178.165.0.142:8081/sign-in/success")
        .autoApprove(true)
        .accessTokenValiditySeconds(60 * 60 * 24)
        .refreshTokenValiditySeconds(240000)

            .and()

         .withClient("vladex-mobile")
         .authorizedGrantTypes("implicit")
         .authorities("USER")
         .scopes("API")
         .redirectUris("app://vladex-messenger")
         .autoApprove(true)
         .accessTokenValiditySeconds(60 * 60 * 24)
         .refreshTokenValiditySeconds(240000);
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    final KeyStoreKeyFactory keyStoreKeyFactory =
        new KeyStoreKeyFactory(new ClassPathResource(keyStorePath), keyStorePassword.toCharArray());
    converter
        .setKeyPair(keyStoreKeyFactory.getKeyPair(keyAlias, privateKeyPassphrase.toCharArray()));
    return converter;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    defaultTokenServices.setTokenEnhancer(accessTokenConverter());
    return defaultTokenServices;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}