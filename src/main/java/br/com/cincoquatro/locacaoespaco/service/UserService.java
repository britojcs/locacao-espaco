package br.com.cincoquatro.locacaoespaco.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.cincoquatro.locacaoespaco.adapter.UserAdapter;
import br.com.cincoquatro.locacaoespaco.dto.RoleDto;
import br.com.cincoquatro.locacaoespaco.dto.UserDto;
import br.com.cincoquatro.locacaoespaco.dto.UserProfileDto;
import br.com.cincoquatro.locacaoespaco.exception.CustomException;
import br.com.cincoquatro.locacaoespaco.exception.EntityNotFoundException;
import br.com.cincoquatro.locacaoespaco.model.User;
import br.com.cincoquatro.locacaoespaco.repository.UserRepository;
import br.com.cincoquatro.locacaoespaco.util.Constants;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@Service
@Transactional
public class UserService {

	private final String MESSAGE_USERNAME_EXISTS = "Este nome de usuário já existe";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserAdapter userAdapter;

	RoleDto roleDto = new RoleDto();

	public UserDto save(UserDto userDto) {
		if (checkUsernameExists(userDto.getUsername()))
			throw new CustomException(MESSAGE_USERNAME_EXISTS, HttpStatus.BAD_REQUEST);

		User user = new User();
		user.setRoles(roleDto.toModel(userDto.getRoles()));
		user.setUsername(userDto.getUsername());
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setPassword(passwordEncoder.encode(getDefaultPassword(userDto.getUsername())));
		user.setEnabled(userDto.getEnabled());

		return userAdapter.toDto(userRepository.save(user));
	}

	public void delete(Long id) {
		Optional<User> userOptional = userRepository.findById(id);

		if (userOptional.isEmpty())
			throw new EntityNotFoundException(User.class, "id", String.valueOf(id));

		User user = userOptional.get();

		checkAdminChange(user);
		checkUserAutoRemove(user);

		userRepository.deleteById(id);
	}

	public UserDto update(UserDto userDto, Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(id)));

		checkAdminChange(user);

		if (checkUsernameExists(user, userDto.getUsername()))
			throw new CustomException(MESSAGE_USERNAME_EXISTS, HttpStatus.BAD_REQUEST);

		user.setRoles(roleDto.toModel(userDto.getRoles()));
		user.setUsername(userDto.getUsername());
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setEnabled(user.getId() == Constants.ADMIN.ID ? true : userDto.getEnabled());

		return userAdapter.toDto(userRepository.save(user));
	}
	
	public void resetPassword(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(id)));

		user.setPassword(passwordEncoder.encode(getDefaultPassword(user.getUsername())));

		userRepository.save(user);
	}

	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(id)));
	}

	public SimplePage<UserDto> findAll(final Pageable pageable) {
		final Page<User> usersPage = userRepository.findAll(pageable);
		return new SimplePage<>(usersPage.getContent().stream().map(customer -> userAdapter.toDto(customer)).collect(Collectors.toList()),
				usersPage.getTotalElements(), pageable);
	}
	
	public void changePassword(String oldPassword, String newPassword) throws AuthenticationException {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
			} catch (AuthenticationException e) {
				throw new CustomException("Senha atual incorreta", HttpStatus.BAD_REQUEST);
			}
			
		} else
			return;

		User user = (User) userRepository.findByUsernameIgnoreCase(username).get();
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setDefaultPassword(Boolean.FALSE);
		userRepository.save(user);
	}

	public void changeProfile(UserProfileDto userProfileDto, User user) {
		checkAdminChange(user);

		user.setFirstname(userProfileDto.getFirstname());
		user.setLastname(userProfileDto.getLastname());

		userRepository.save(user);
	}

	public void updateLastLoginAt(User user) {
		user.setLastLoginAt(LocalDateTime.now());
		userRepository.save(user);
	}

	private boolean checkUsernameExists(String username) {
		return userRepository.existsByUsername(username);
	}

	private boolean checkUsernameExists(User user, String username) {
		return (!user.getUsername().equalsIgnoreCase(username)) && (checkUsernameExists(username));
	}

	private void checkAdminChange(User user) {
		if (user.getId() == Constants.ADMIN.ID)
			throw new CustomException("O Administrador não pode ser alterado ou removido", HttpStatus.BAD_REQUEST);
	}

	private void checkUserAutoRemove(User user) {
		User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getUsername() == userLogged.getUsername())
			throw new CustomException("Você não pode se excluir", HttpStatus.BAD_REQUEST);
	}
	
	private String getDefaultPassword(String username) {
		return "@" + username.toLowerCase();
	}

}
