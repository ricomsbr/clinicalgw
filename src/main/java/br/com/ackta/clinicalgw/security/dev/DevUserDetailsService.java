package br.com.ackta.clinicalgw.security.dev;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.ackta.clinicalgw.model.User;
import br.com.ackta.clinicalgw.repository.UserRepository;
import br.com.ackta.clinicalgw.security.DapLegacyUser;

/**
 *
 *
 */
@Profile("development")
public class DevUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	/**
	 * @param userRepository
	 * @param authorityRepository
	 * @param groupRepository
	 */
	public DevUserDetailsService(UserRepository userLoginRepository) {
		this.userRepository = userLoginRepository;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found", username)));
		final List<GrantedAuthority> roleList = getRolesList(user);
		final DapLegacyUser result = new DapLegacyUser(user.getId(), user.getUsername(), user.getPassword(),
				user.getName(), user.isActive(), false, roleList);
		return result;
	}

	/**
	 * @param userLogin
	 * @return
	 */
	private List<GrantedAuthority> getRolesList(User userLogin) {
		List<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
		userLogin.getUserRole().stream()
				.forEach(role -> roleList.add(new SimpleGrantedAuthority(role.getRole().getName())));
		return roleList;
	}
}