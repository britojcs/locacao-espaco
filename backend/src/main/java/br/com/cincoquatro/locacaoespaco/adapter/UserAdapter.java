package br.com.cincoquatro.locacaoespaco.adapter;

import org.springframework.stereotype.Component;

import br.com.cincoquatro.locacaoespaco.dto.RoleDto;
import br.com.cincoquatro.locacaoespaco.dto.UserDto;
import br.com.cincoquatro.locacaoespaco.model.User;

@Component
public class UserAdapter extends BaseAdapter<User, UserDto> {

	@Override
	public UserDto toDto(User user) {
		if (user == null)
			return null;

		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setFirstname(user.getFirstname());
		userDto.setLastname(user.getLastname());
		userDto.setFullname(user.getFirstname() + " " + user.getLastname());
		userDto.setRoles(new RoleDto().toDto(user.getRoles()));
		userDto.setEnabled(user.getEnabled());
		userDto.setLastLoginAt(user.getLastLoginAt());

		return userDto;
	}

	@Override
	public User transferProperties(User user, UserDto userDto) {
		if (userDto == null)
			return null;

		if (user == null)
			user = new User();

		user.setId(userDto.getId());
		user.setUsername(userDto.getUsername());
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setRoles(new RoleDto().toModel(userDto.getRoles()));
		user.setEnabled(userDto.getEnabled());
		user.setLastLoginAt(userDto.getLastLoginAt());

		return user;
	}

}
