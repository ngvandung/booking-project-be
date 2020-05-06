/**
 * 
 */
package com.booking.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = userDetailsRepository.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		List<UserRole> userRoles = userRoleRepository.findByUserId(user.getUserId());
		for (UserRole userRole : userRoles) {
			grantedAuthorities
					.add(new SimpleGrantedAuthority(roleRepository.findByRoleId(userRole.getRoleId()).getRoleName()));
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				passwordEncoder.encode(user.getPassword()), true, true, true, true, grantedAuthorities);
	}

}
