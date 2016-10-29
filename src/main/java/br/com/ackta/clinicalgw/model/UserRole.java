package br.com.ackta.clinicalgw.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6373622541444046579L;

	@Id
	@ManyToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "id_role", referencedColumnName = "id")
	private Role role;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

}
