package br.com.cincoquatro.locacaoespaco.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.cincoquatro.locacaoespaco.model.enums.PlaceType;

public class PlaceTypeDto extends BaseEnumDto<PlaceType, PlaceTypeDto> {

	public PlaceTypeDto() {
	}

	public PlaceTypeDto(String name, String description) {
		super(name, description);
	}

	@Override
	public PlaceTypeDto toDto(PlaceType placeType) {
		if (placeType == null)
			return null;

		return new PlaceTypeDto(placeType.name(), placeType.getDescription());
	}

	@Override
	public Set<PlaceTypeDto> toDto(Set<PlaceType> placeTypes) {
		return placeTypes.stream().map(placeType -> toDto(placeType)).collect(Collectors.toSet());
	}

	@Override
	public PlaceType toModel(String placeType) {
		if (placeType == null)
			return null;

		return PlaceType.getByPlaceTypeName(placeType);
	}

	@Override
	public Set<PlaceType> toModel(Set<PlaceTypeDto> placeTypesDto) {
		return placeTypesDto.stream().map(placeTypeDto -> toModel(placeTypeDto.getName())).collect(Collectors.toSet());
	}

}
