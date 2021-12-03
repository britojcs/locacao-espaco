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
public class PlaceDto {

	private Long id;

	@NotNull(message = "Informe o tipo de espaço")
	private PlaceTypeDto placeType;

	@NotEmpty
	@Size(min = 2, max = 80, message = "O nome deve ter entre 2 e 80 caracteres")
	private String name;

	@NotNull
	private BigDecimal value;

	@NotNull
	private Boolean enabled;

}
