package br.com.cincoquatro.locacaoespaco.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoCepDto implements Serializable {

	private static final long serialVersionUID = 1991270320091815174L;

	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;
	private String ibge;

}
