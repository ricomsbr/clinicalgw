package br.com.ackta.clinicalgw.model;

import java.io.Serializable;

/**
 *
 *
 */
public interface IPersistable extends Serializable {

	Long getVersion();

	Long getId();

	void setVersion(Long version);

	void setId(Long id);

	boolean isActive();

}
