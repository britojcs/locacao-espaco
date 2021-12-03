package br.com.cincoquatro.locacaoespaco.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ContactType {

	// @formatter:off
	PERSONAL_EMAIL("Email pessoal"),
    BUSINESS_EMAIL("Email comercial"),
    PERSONAL_PHONE("Telefone pessoal"),
    BUSINESS_PHONE("Telefone comercial");
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
