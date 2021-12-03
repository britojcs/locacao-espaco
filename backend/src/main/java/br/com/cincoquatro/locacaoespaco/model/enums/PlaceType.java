package br.com.cincoquatro.locacaoespaco.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PlaceType {

	// @formatter:off
	MAIN("Principal"),
    DEPENDENT_MAIN("Dependente de espaço principal"),
    DEPENDENT_FREE_TIME("Dependente de horário livre");
	// @formatter:on    

	private final String description;

	PlaceType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static PlaceType getByPlaceTypeName(String placeTypeName) {
		for (PlaceType name : PlaceType.values())
			if (name.name().equals(placeTypeName))
				return name;

		return null;
	}

}
