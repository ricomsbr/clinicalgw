/**
 *
 */
package br.com.ackta.clinicalgw.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 *
 */
@Entity
@Table(name = "role")
@SQLDelete(sql = "UPDATE role SET active = 0 WHERE id = ? AND version = ?")
@Where(clause = "active = 1")
public class Role implements IPersistable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3946218884003292425L;

	@Id
	@SequenceGenerator(name = "sq_role", sequenceName = "sq_role")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_role")
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "active", nullable = false)
	private boolean active = true;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "role")
	private Set<UserRole> userRole = new HashSet<UserRole>();

	public Role() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
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
