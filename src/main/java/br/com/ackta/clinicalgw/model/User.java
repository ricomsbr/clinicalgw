package br.com.ackta.clinicalgw.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.com.ackta.clinicalgw.security.IUser;

@Entity
@Table(name = "ackta_user")
@SQLDelete(sql = "UPDATE ackta_user SET active = 0 WHERE id = ? AND version = ?")
@Where(clause = "active = 1")
public class User implements IPersistable, IUser {
	/**
	 *
	 */
	private static final long serialVersionUID = -8714671084359611920L;

	@Id
	@SequenceGenerator(name = "sq_user", sequenceName = "sq_use")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_user")
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "active", nullable = false)
	private boolean active = true;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "password", nullable = false)
	private String password;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserRole> userRole = new HashSet<UserRole>();

	public User() {
		super();
	}

	/**
	 * @param id
	 * @param username
	 * @param name
	 */
	public User(String username, String name, String password, boolean active) {
		this();
		this.username = username;
		this.name = name;
		this.active = true;
		this.password = password;
		this.active = active;
	}

	/**
	 * @param id
	 * @param username
	 * @param name
	 */
	public User(Long id, String username, String name, String password, boolean active) {
		this(username, name, password, active);
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

}
