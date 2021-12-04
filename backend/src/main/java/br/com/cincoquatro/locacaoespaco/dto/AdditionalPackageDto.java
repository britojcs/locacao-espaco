package br.com.cincoquatro.locacaoespaco.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionalPackageDto {

	@NotEmpty
	private String description;

}
