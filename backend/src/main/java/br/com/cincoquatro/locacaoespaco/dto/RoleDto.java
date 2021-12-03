package br.com.cincoquatro.locacaoespaco.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.cincoquatro.locacaoespaco.model.enums.Role;

public class RoleDto extends BaseEnumDto<Role, RoleDto> {
	
	public RoleDto() {
	}

	public RoleDto(String name, String description) {
		super(name, description);
	}

	@Override
	public RoleDto toDto(Role role) {
		if (role == null)
			return null;

		return new RoleDto(role.name(), role.getDescription());
	}

	@Override
	public Set<RoleDto> toDto(Set<Role> roles) {
		return roles.stream().map(role -> toDto(role)).collect(Collectors.toSet());
	}

	@Override
	public Role toModel(String roleName) {
		if (roleName == null)
			return null;

		return Role.getByRoleName(roleName);
	}

	@Override
	public Set<Role> toModel(Set<RoleDto> rolesDto) {
		return rolesDto.stream().map(roleDto -> toModel(roleDto.getName())).collect(Collectors.toSet());
	}

}
