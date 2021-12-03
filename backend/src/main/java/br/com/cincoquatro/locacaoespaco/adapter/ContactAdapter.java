package br.com.cincoquatro.locacaoespaco.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.cincoquatro.locacaoespaco.dto.ContactDto;
import br.com.cincoquatro.locacaoespaco.dto.ContactTypeDto;
import br.com.cincoquatro.locacaoespaco.model.Contact;
import br.com.cincoquatro.locacaoespaco.model.Customer;

@Component
public class ContactAdapter extends BaseAdapter<Contact, ContactDto> {

	@Override
	public ContactDto toDto(Contact contact) {
		if (contact == null)
			return null;

		ContactDto contactDto = new ContactDto();
		contactDto.setId(contact.getId());
		contactDto.setContactType(new ContactTypeDto().toDto(contact.getContactType()));
		contactDto.setName(contact.getName());
		contactDto.setDescription(contact.getDescription());

		contactDto.setCustomerId(Optional.ofNullable(contact.getCustomer().getId()).orElse(null));

		return contactDto;
	}

	@Override
	public Contact transferProperties(Contact contact, ContactDto contactDto) {
		if (contactDto == null)
			return null;

		if (contact == null)
			contact = new Contact();

		contact.setId(contactDto.getId());
		contact.setContactType(new ContactTypeDto().toModel(contactDto.getContactType().name));
		contact.setName(contactDto.getName());
		contact.setDescription(contactDto.getDescription());

		Customer customer = new Customer();
		customer.setId(contactDto.getCustomerId());
		contact.setCustomer(customer);

		return contact;
	}

}
