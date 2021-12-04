package br.com.cincoquatro.locacaoespaco.adapter;

import org.springframework.stereotype.Component;

import br.com.cincoquatro.locacaoespaco.dto.AdditionalPackageDto;
import br.com.cincoquatro.locacaoespaco.model.AdditionalPackage;

@Component
public class AdditionalPackageAdapter extends BaseAdapter<AdditionalPackage, AdditionalPackageDto> {

	@Override
	public AdditionalPackageDto toDto(AdditionalPackage additionalPackage) {
		if (additionalPackage == null)
			return null;

		AdditionalPackageDto additionalPackageDto = new AdditionalPackageDto();
		additionalPackageDto.setId(additionalPackage.getId());
		additionalPackageDto.setDescription(additionalPackage.getDescription());

		return additionalPackageDto;
	}

	@Override
	public AdditionalPackage transferProperties(AdditionalPackage additionalPackage, AdditionalPackageDto additionalPackageDto) {
		if (additionalPackageDto == null)
			return null;

		if (additionalPackage == null)
			additionalPackage = new AdditionalPackage();

		additionalPackage.setId(additionalPackageDto.getId());
		additionalPackage.setDescription(additionalPackageDto.getDescription());

		return additionalPackage;
	}

}
