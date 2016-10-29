package br.com.ackta.clinicalgw.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.ackta.clinicalgw.RecaptchaSettings;
import br.com.ackta.clinicalgw.security.IUser;

/**
 *
 *
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

	private RecaptchaSettings recaptchaSettings;

	private final RestTemplate restTemplate;

	@Autowired
	public LoginController(@Qualifier("PlainRestClient") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@RequestMapping("/user")
	public @ResponseBody Map<String, Object> user(@AuthenticationPrincipal IUser user) {
		return Collections.singletonMap("name", user.getName());
	}

	@RequestMapping(value = "/recaptcha-{id}")
	public String recaptcha(@PathVariable("id") String response) {

		final MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("response", response);
		map.add("secret", recaptchaSettings.getKey());

		final String result = restTemplate.postForObject(recaptchaSettings.getUrl(), map, String.class);
		return result;
	}

	@ExceptionHandler(RestClientException.class)
	@ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = "recaptcha request failed")
	public void recaptchaUnavailable(RestClientException e) {
	}
}
