package br.com.cincoquatro.locacaoespaco.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LeaseType {

	// @formatter:off
	INDEPENDENT("Locação independente (Espaço principal)"),
    DEPENDENT_MAIN("Locação dependente de espaço principal"),
    DEPENDENT_FREE_TIME("Locação dependente de horário livre");
	// @formatter:on    

	private final String description;

	LeaseType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static LeaseType getByLeaseTypeName(String leaseTypeName) {
		for (LeaseType name : LeaseType.values())
			if (name.name().equals(leaseTypeName))
				return name;

		return null;
	}

}
