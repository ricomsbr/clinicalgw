package br.com.ackta.clinicalgw.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 *
 */
@ConfigurationProperties(prefix = "app.ldap")
@Component
public class LdapConnectionSettings {

	private String domain;

	private String url;

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return this.domain;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
