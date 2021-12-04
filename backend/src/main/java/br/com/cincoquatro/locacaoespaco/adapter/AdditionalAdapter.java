package br.com.cincoquatro.locacaoespaco.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cincoquatro.locacaoespaco.dto.AdditionalDto;
import br.com.cincoquatro.locacaoespaco.model.Additional;

@Component
public class AdditionalAdapter extends BaseAdapter<Additional, AdditionalDto> {

	@Autowired
	private AdditionalPackageAdapter additionalPackageAdapter;

	@Override
	public AdditionalDto toDto(Additional additional) {
		if (additional == null)
			return null;

		AdditionalDto additionalDto = new AdditionalDto();
		additionalDto.setId(additional.getId());
		additionalDto.setName(additional.getName());
		additionalDto.setValue(additional.getValue());
		additionalDto.setRequired(additional.getRequired());
		additionalDto.setEnabled(additional.getEnabled());
		additionalDto.setAdditionalPackage(additionalPackageAdapter.toDto(additional.getAdditionalPackage()));

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
		additional.setRequired(additionalDto.getRequired());
		additional.setEnabled(additionalDto.getEnabled());
		additional.setAdditionalPackage(additionalPackageAdapter.toModel(additionalDto.getAdditionalPackage()));

		return additional;
	}

}
