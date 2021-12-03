package br.com.cincoquatro.locacaoespaco.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactDto {

	private Long id;

	@NotNull(message = "Informe o tipo de contato")
	private ContactTypeDto contactType;

	@NotEmpty
	@Size(min = 2, max = 80, message = "O contato deve ter entre 2 e 80 caracteres")
	private String name;

	private String description;

	private Long customerId;

}
