package br.com.cincoquatro.locacaoespaco.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.cincoquatro.locacaoespaco.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private Long id;

	@NotEmpty
	@Size(min = 5, max = 25, message = "O nome de usuário deve ter entre 5 e 25 caracteres")
	@Pattern(regexp = Constants.REGEX.USERNAME, message = "Usuário/Login inválido!")
	private String username;

	@Size(min = 2, max = 30, message = "O primeiro nome deve ter entre 2 e 30 caracteres")
	@Pattern(regexp = Constants.REGEX.NAME, message = "Nome inválido!")
	private String firstname;

	private String lastname;

	private String fullname;

	@NotNull
	private Boolean enabled;

	private Set<RoleDto> roles = new HashSet<>();

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime lastLoginAt;

}
