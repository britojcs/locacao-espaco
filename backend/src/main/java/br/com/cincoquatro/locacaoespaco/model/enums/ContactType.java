package br.com.cincoquatro.locacaoespaco.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ContactType {

	// @formatter:off
	PERSONAL_EMAIL("EMAIL PESSOAL"),
    BUSINESS_EMAIL("EMAIL COMERCIAL"),
    PERSONAL_PHONE("TELEFONE PESSOAL"),
    BUSINESS_PHONE("TELEFONE COMERCIAL");
	// @formatter:on    

	private final String description;

	ContactType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static ContactType getByContactTypeName(String contactTypeName) {
		for (ContactType name : ContactType.values())
			if (name.name().equals(contactTypeName))
				return name;

		return null;
	}

}
