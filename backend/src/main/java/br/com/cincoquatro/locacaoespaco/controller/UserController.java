package br.com.cincoquatro.locacaoespaco.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cincoquatro.locacaoespaco.dto.UserDto;
import br.com.cincoquatro.locacaoespaco.model.User;
import br.com.cincoquatro.locacaoespaco.service.UserService;
import br.com.cincoquatro.locacaoespaco.util.Constants.PAGE;
import br.com.cincoquatro.locacaoespaco.util.ResponseUtil;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<SimplePage<UserDto>> users(@SortDefault(sort = "username") @PageableDefault(size = PAGE.SIZE) Pageable pageable) {
		return ResponseEntity.ok(userService.findAll(pageable));
	}
	
	@ResponseBody
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public User getUser(@PathVariable("id") Long id) {
		return userService.findById(id);
	}

	@ResponseBody
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<UserDto> newUser(@RequestBody @Valid UserDto userDto) {
		UserDto userDtoSaved = userService.save(userDto);
		return new ResponseEntity<UserDto>(userDtoSaved, HttpStatus.CREATED);
	}

	@ResponseBody
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public UserDto updateUser(@RequestBody @Valid UserDto user, @PathVariable("id") Long id) {
		return userService.update(user, id);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		userService.delete(id);
		return ResponseUtil.mountResult_200("Usuário excluído com sucesso!");
	}

	@GetMapping("/whoami")
	public User user() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
