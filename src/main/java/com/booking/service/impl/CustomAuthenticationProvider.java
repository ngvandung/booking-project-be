/**
 * 
 */
package com.booking.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.booking.model.User;
import com.booking.model.UserRole;
import com.booking.repository.RoleRepository;
import com.booking.repository.UserDetailsRepository;
import com.booking.repository.UserRoleRepository;

/**
 * @author ddung
 *
 */
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		User user = userDetailsRepository.findUserByUsername(username);
		if (user != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String comparePassword = user.getPassword();
			if (passwordEncoder.matches(password, comparePassword)) {
				Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
				List<UserRole> userRoles = userRoleRepository.findByUserId(user.getUserId());
				for (UserRole userRole : userRoles) {
					grantedAuthorities.add(new SimpleGrantedAuthority(
							roleRepository.findByRoleId(userRole.getRoleId()).getRoleName()));
				}
				return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
