package br.com.ackta.clinicalgw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ackta.clinicalgw.model.User;

/**
 *
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * @param username
	 * @return
	 */
	public Optional<User> findByUsername(String username);

}
