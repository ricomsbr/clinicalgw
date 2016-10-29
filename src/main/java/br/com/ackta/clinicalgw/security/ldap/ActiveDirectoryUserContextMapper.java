package br.com.ackta.clinicalgw.security.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Service;

import br.com.ackta.clinicalgw.model.User;
import br.com.ackta.clinicalgw.repository.UserRepository;
import br.com.ackta.clinicalgw.security.DapLegacyUser;

/**
 *
 *
 */
@Profile({ "ldap" })
@Service
public class ActiveDirectoryUserContextMapper implements UserDetailsContextMapper, Serializable {

	private static final long serialVersionUID = 2369849562441525772L;

	private final UserRepository userLoginRepository;

	/**
	 * @param userRepository
	 * @param authorityRepository
	 * @param groupRepository
	 */
	@Autowired
	public ActiveDirectoryUserContextMapper(UserRepository userRepository) {
		this.userLoginRepository = userRepository;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.ldap.userdetails.UserDetailsContextMapper
	 * #mapUserFromContext(org.springframework.ldap.core.DirContextOperations,
	 * java.lang.String, java.util.Collection)
	 */
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> adAuthorities) {
		final String name = String.valueOf(ctx.getObjectAttribute("CN"));
		final User userLogin = userLoginRepository.findByUsername(username.toUpperCase())
				.orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found", username)));
		final List<GrantedAuthority> roleList = getRolesList(userLogin);
		final DapLegacyUser result = new DapLegacyUser(userLogin.getId(), userLogin.getUsername(),
				userLogin.getPassword(), name, userLogin.isActive(), false, roleList);
		return result;
	}

	/**
	 * @param userLogin
	 * @return
	 */
	private List<GrantedAuthority> getRolesList(User userLogin) {
		final List<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
		userLogin.getUserRole().stream()
				.forEach(role -> roleList.add(new SimpleGrantedAuthority(role.getRole().getName())));
		return roleList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.ldap.userdetails.UserDetailsContextMapper
	 * #mapUserToContext
	 * (org.springframework.security.core.userdetails.UserDetails,
	 * org.springframework.ldap.core.DirContextAdapter)
	 */
	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		// Sistema não atualiza as informações do usuário no AD.
	}
}
