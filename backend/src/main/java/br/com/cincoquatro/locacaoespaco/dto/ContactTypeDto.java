package br.com.cincoquatro.locacaoespaco.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.cincoquatro.locacaoespaco.model.enums.ContactType;

public class ContactTypeDto extends BaseEnumDto<ContactType, ContactTypeDto> {
	
	public ContactTypeDto() {
	}

	public ContactTypeDto(String name, String description) {
		super(name, description);
	}

	@Override
	public ContactTypeDto toDto(ContactType contactType) {
		if (contactType == null)
			return null;

		return new ContactTypeDto(contactType.name(), contactType.getDescription());
	}

	@Override
	public Set<ContactTypeDto> toDto(Set<ContactType> contactTypes) {
		return contactTypes.stream().map(contactType -> toDto(contactType)).collect(Collectors.toSet());
	}

	@Override
	public ContactType toModel(String contactType) {
		if (contactType == null)
			return null;

		return ContactType.getByContactTypeName(contactType);
	}

	@Override
	public Set<ContactType> toModel(Set<ContactTypeDto> contactTypesDto) {
		return contactTypesDto.stream().map(contactTypeDto -> toModel(contactTypeDto.getName())).collect(Collectors.toSet());
	}

}
