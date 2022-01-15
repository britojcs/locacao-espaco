package br.com.cincoquatro.locacaoespaco.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionalPackageDto {
	
	private Long id;

	@NotEmpty
	private String description;

}
