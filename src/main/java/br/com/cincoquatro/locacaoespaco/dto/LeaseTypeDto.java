package br.com.cincoquatro.locacaoespaco.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.cincoquatro.locacaoespaco.model.enums.LeaseType;

public class LeaseTypeDto extends BaseEnumDto<LeaseType, LeaseTypeDto> {

	public LeaseTypeDto() {
	}

	public LeaseTypeDto(String name, String description) {
		super(name, description);
	}

	@Override
	public LeaseTypeDto toDto(LeaseType leaseType) {
		if (leaseType == null)
			return null;

		return new LeaseTypeDto(leaseType.name(), leaseType.getDescription());
	}

	@Override
	public Set<LeaseTypeDto> toDto(Set<LeaseType> leaseTypes) {
		return leaseTypes.stream().map(leaseType -> toDto(leaseType)).collect(Collectors.toSet());
	}

	@Override
	public LeaseType toModel(String leaseType) {
		if (leaseType == null)
			return null;

		return LeaseType.getByLeaseTypeName(leaseType);
	}

	@Override
	public Set<LeaseType> toModel(Set<LeaseTypeDto> leaseTypesDto) {
		return leaseTypesDto.stream().map(leaseTypeDto -> toModel(leaseTypeDto.getName())).collect(Collectors.toSet());
	}

}
