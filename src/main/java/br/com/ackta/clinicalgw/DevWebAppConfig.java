package br.com.ackta.clinicalgw;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 *
 */
@Configuration
@EnableConfigurationProperties
public class DevWebAppConfig {

	@Bean
	@ConfigurationProperties(prefix = "app.proxy")
	public ProxySettings proxySettings() {
		return new ProxySettings();
	}

	@Bean
	@Qualifier("PlainRestClient")
	public RestTemplate restTemplate() {
		final SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		final ProxySettings settings = proxySettings();
		final Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(settings.getHost(), settings.getPort()));
		clientHttpRequestFactory.setProxy(proxy);
		final RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		return restTemplate;
	}
}
