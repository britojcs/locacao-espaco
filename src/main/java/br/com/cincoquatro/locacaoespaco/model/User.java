package br.com.cincoquatro.locacaoespaco.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.cincoquatro.locacaoespaco.model.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntityAudit implements UserDetails, Serializable {

	private static final long serialVersionUID = -4470993131127826141L;

	@Column(name = "username", nullable = false, length = 25, unique = true)
	private String username;

	@Column(name = "password", nullable = false, length = 200)
	private String password;

	@Column(name = "firstname", nullable = false, length = 30)
	private String firstname;

	@Column(name = "lastname", nullable = false, length = 50)
	private String lastname;

	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Set<Role> roles = new HashSet<>();
	
	@Column(name = "default_password", nullable = false)
	private Boolean defaultPassword = true;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authorities = new ArrayList<>();

		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.name()));
		});

		return authorities;
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
		return enabled;
	}

}
