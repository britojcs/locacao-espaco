package br.com.cincoquatro.locacaoespaco.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AdditionalRequiredType {

	// @formatter:off
	NOT_REQUIRED("Não é obrigatório"),
	REQUIRED_MAIN_LEASE("Obrigatório em qualquer locação"),
	REQUIRED_ANY_LEASE("Obrigatório em locação de espaço principal");
	// @formatter:on    

	private final String description;

	AdditionalRequiredType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static AdditionalRequiredType getByAdditionalRequiredTypeName(String additionalRequiredTypeName) {
		for (AdditionalRequiredType name : AdditionalRequiredType.values())
			if (name.name().equals(additionalRequiredTypeName))
				return name;

		return null;
	}

}
