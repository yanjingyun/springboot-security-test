package com.yjy.security.entity;

import com.yjy.system.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = -7145852170609747160L;

	private Integer id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtUser() {
	}

	public JwtUser(User user) {
		id = user.getId();
		username = user.getUsername();
		password = user.getPassword();
		authorities = user.getRoles();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "JwtUser [id=" + id + ", username=" + username + ", password=" + password + ", authorities="
				+ authorities + "]";
	}
}