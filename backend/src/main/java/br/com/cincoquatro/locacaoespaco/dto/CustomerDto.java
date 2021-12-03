package br.com.cincoquatro.locacaoespaco.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.cincoquatro.locacaoespaco.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

	private Long id;

	@NotEmpty
	@Size(min = 2, max = 100, message = "O nome do Cliente deve ter entre 2 e 80 caracteres")
	private String fullname;

	@NotNull
	@Pattern(regexp = Constants.REGEX.CPF_CNPJ, message = "CPF/CNPJ inválido!")
	private String cpfCnpj;

	@NotNull
	@Pattern(regexp = Constants.REGEX.CEP, message = "CEP inválido!")
	private String cep;

	private String address;
	private String addressComplement;
	private String district;
	private String city;

	@NotNull
	private StateDto state;

}
