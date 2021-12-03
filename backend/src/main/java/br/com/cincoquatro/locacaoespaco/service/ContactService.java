package br.com.cincoquatro.locacaoespaco.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.cincoquatro.locacaoespaco.adapter.ContactAdapter;
import br.com.cincoquatro.locacaoespaco.dto.ContactDto;
import br.com.cincoquatro.locacaoespaco.exception.CustomException;
import br.com.cincoquatro.locacaoespaco.model.Contact;
import br.com.cincoquatro.locacaoespaco.repository.ContactRepository;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@Service
@Transactional
public class ContactService {
	
	public static final int BY_CUSTOMER = 0;

	@Autowired
	private ContactAdapter contactAdapter;

	@Autowired
	private ContactRepository contactRepository;

	public ContactDto save(ContactDto contactDto) {
		if (contactDto.getCustomerId() == null)
			throw new CustomException("Não foi informado um dono para este Contato", HttpStatus.BAD_REQUEST);
			
		Contact contactFromDto = contactAdapter.toModel(contactDto);

		Contact contactSaved = contactRepository.save(contactFromDto);

		return contactAdapter.toDto(contactSaved);
	}

	public ContactDto update(ContactDto contactDto) {
		Long contactId = contactDto.getId();

		if (contactDto.getCustomerId() != null) {
			Contact contactSaved = getContactByCustomerIdAndContactId(contactDto.getCustomerId(), contactId);

			Contact contactUpdated = contactRepository.save(contactAdapter.toModel(contactDto, contactSaved));

			return contactAdapter.toDto(contactUpdated);
		}

		throw new CustomException("Não foi informado um dono para este Contato", HttpStatus.BAD_REQUEST);
	}

	public void delete(Long id) {
		contactRepository.deleteById(id);
	}

	public ContactDto findById(Long id) {
		Contact contact = contactRepository.findById(id).orElseThrow(() -> new CustomException("Este contato não existe", HttpStatus.BAD_REQUEST));

		return contactAdapter.toDto(contact);
	}
	
	public SimplePage<ContactDto> findAll(Long customerId, int owner, final Pageable pageable) {
		Page<Contact> contactsPage = Page.empty(pageable);

		switch (owner) {
		case BY_CUSTOMER:
			contactsPage = contactRepository.findAllByCustomerId(customerId, pageable);
			break;
		default:
			break;
		}
        
        return new SimplePage<>(contactsPage.getContent()
                .stream()
                .map(contact -> contactAdapter.toDto(contact))
                .collect(Collectors.toList()),
                contactsPage.getTotalElements(), pageable);
    }
	
	private Contact getContactByCustomerIdAndContactId(Long customerId, Long id) {
		return contactRepository.findByCustomerIdAndId(customerId, id)
				.orElseThrow(() -> new CustomException("Este contato não existe", HttpStatus.BAD_REQUEST));
	}

}
