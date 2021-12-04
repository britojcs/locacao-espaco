package br.com.cincoquatro.locacaoespaco.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionalDto {

	private Long id;

	@NotEmpty
	@Size(min = 2, max = 200, message = "O nome deve ter entre 2 e 200 caracteres")
	private String name;

	@NotNull
	private BigDecimal value;

	@NotNull
	private Boolean required;

	@NotNull
	private Boolean enabled;

	private AdditionalPackageDto additionalPackage;

}
