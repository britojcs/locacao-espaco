package br.com.cincoquatro.locacaoespaco.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.cincoquatro.locacaoespaco.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDto {

	@NotEmpty
	@Size(min = 2, max = 30, message = "O primeiro nome deve ter entre 2 e 30 caracteres")
	@Pattern(regexp = Constants.REGEX.NAME, message = "Nome inválido!")
	private String firstname;

	private String lastname;

}
