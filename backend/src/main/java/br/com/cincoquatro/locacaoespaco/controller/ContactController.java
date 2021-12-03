package br.com.cincoquatro.locacaoespaco.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cincoquatro.locacaoespaco.dto.ContactDto;
import br.com.cincoquatro.locacaoespaco.service.ContactService;
import br.com.cincoquatro.locacaoespaco.util.ResponseUtil;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContactController {

	@Autowired
	private ContactService customerContactService;

	@ResponseBody
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<ContactDto> getContact(@PathVariable("id") Long id) {
		ContactDto customerContactDto = customerContactService.findById(id);
		return new ResponseEntity<ContactDto>(customerContactDto, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<ContactDto> newContact(@RequestBody @Valid ContactDto customerContactDto) {
		ContactDto customerContactDtoSaved = customerContactService.save(customerContactDto);
		return new ResponseEntity<ContactDto>(customerContactDtoSaved, HttpStatus.CREATED);
	}

	@ResponseBody
	@PutMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<ContactDto> updateContact(@RequestBody @Valid ContactDto customerContactDto) {
		ContactDto customerContactDtoUpdated = customerContactService.update(customerContactDto);
		return new ResponseEntity<ContactDto>(customerContactDtoUpdated, HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<?> deleteContact(@PathVariable("id") Long id) {
		customerContactService.delete(id);
		return ResponseUtil.mountResult_200("Contato excluído com sucesso!");
	}

}
