package br.com.cincoquatro.locacaoespaco.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContractDto {

	private Long id;

	@NotEmpty
	@Size(min = 2, max = 50, message = "O nome do Contrato deve ter entre 2 e 50 caracteres")
	private String name;

	private String description;

	@NotEmpty
	private String content;

	@NotNull
	private Boolean enabled;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime updatedAt;

}
