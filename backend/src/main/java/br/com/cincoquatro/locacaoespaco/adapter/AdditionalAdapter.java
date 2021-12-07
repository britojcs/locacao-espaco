package br.com.cincoquatro.locacaoespaco.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.cincoquatro.locacaoespaco.dto.AdditionalDto;
import br.com.cincoquatro.locacaoespaco.dto.AdditionalRequiredTypeDto;
import br.com.cincoquatro.locacaoespaco.model.Additional;
import br.com.cincoquatro.locacaoespaco.model.AdditionalPackage;

@Component
public class AdditionalAdapter extends BaseAdapter<Additional, AdditionalDto> {

	@Override
	public AdditionalDto toDto(Additional additional) {
		if (additional == null)
			return null;

		AdditionalDto additionalDto = new AdditionalDto();
		additionalDto.setId(additional.getId());
		additionalDto.setName(additional.getName());
		additionalDto.setValue(additional.getValue());
		additionalDto.setEnabled(additional.getEnabled());
		additionalDto.setRequiredType(new AdditionalRequiredTypeDto().toDto(additional.getRequiredType()));

		Optional.ofNullable(additional.getAdditionalPackage()).ifPresent(additionalPackage -> {
			additionalDto.setAdditionalPackage(additionalPackage.getDescription());
		});

		return additionalDto;
	}

	@Override
	public Additional transferProperties(Additional additional, AdditionalDto additionalDto) {
		if (additionalDto == null)
			return null;

		if (additional == null)
			additional = new Additional();

		additional.setId(additionalDto.getId());
		additional.setName(additionalDto.getName());
		additional.setValue(additionalDto.getValue());
		additional.setEnabled(additionalDto.getEnabled());
		additional.setRequiredType(new AdditionalRequiredTypeDto().toModel(additionalDto.getRequiredType().name));

		String additionalPackageDescription = additionalDto.getAdditionalPackage();
		if (additionalPackageDescription != null && additionalPackageDescription.trim().length() > 0) {
			AdditionalPackage additionalPackage = new AdditionalPackage();
			additionalPackage.setId(additional.getId());
			additionalPackage.setAdditional(additional);
			additionalPackage.setDescription(additionalPackageDescription);
			additional.setAdditionalPackage(additionalPackage);
		} else
			additional.setAdditionalPackage(null);

		return additional;
	}

}
