package br.com.cincoquatro.locacaoespaco.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.cincoquatro.locacaoespaco.model.enums.AdditionalRequiredType;

public class AdditionalRequiredTypeDto extends BaseEnumDto<AdditionalRequiredType, AdditionalRequiredTypeDto> {

	public AdditionalRequiredTypeDto() {
	}

	public AdditionalRequiredTypeDto(String name, String description) {
		super(name, description);
	}

	@Override
	public AdditionalRequiredTypeDto toDto(AdditionalRequiredType additionalRequiredType) {
		if (additionalRequiredType == null)
			return null;

		return new AdditionalRequiredTypeDto(additionalRequiredType.name(), additionalRequiredType.getDescription());
	}

	@Override
	public Set<AdditionalRequiredTypeDto> toDto(Set<AdditionalRequiredType> additionalRequiredTypes) {
		return additionalRequiredTypes.stream().map(additionalRequiredType -> toDto(additionalRequiredType)).collect(Collectors.toSet());
	}

	@Override
	public AdditionalRequiredType toModel(String additionalRequiredType) {
		if (additionalRequiredType == null)
			return null;

		return AdditionalRequiredType.getByAdditionalRequiredTypeName(additionalRequiredType);
	}

	@Override
	public Set<AdditionalRequiredType> toModel(Set<AdditionalRequiredTypeDto> additionalRequiredTypesDto) {
		return additionalRequiredTypesDto
				.stream()
				.map(additionalRequiredTypeDto -> toModel(additionalRequiredTypeDto.getName()))
				.collect(Collectors.toSet());
	}

}
