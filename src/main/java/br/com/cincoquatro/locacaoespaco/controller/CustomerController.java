package br.com.cincoquatro.locacaoespaco.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
import br.com.cincoquatro.locacaoespaco.dto.CustomerDto;
import br.com.cincoquatro.locacaoespaco.service.ContactService;
import br.com.cincoquatro.locacaoespaco.service.CustomerService;
import br.com.cincoquatro.locacaoespaco.util.Constants.PAGE;
import br.com.cincoquatro.locacaoespaco.util.ResponseUtil;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ContactService customerContactService;

	@ResponseBody
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<SimplePage<CustomerDto>> customers(@SortDefault(sort = "fullName") @PageableDefault(size = PAGE.SIZE) Pageable pageable) {
		return ResponseEntity.ok(customerService.findAll(pageable));
	}

	@ResponseBody
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") Long id) {
		CustomerDto customerDto = customerService.findById(id);
		return new ResponseEntity<CustomerDto>(customerDto, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<CustomerDto> newCustomer(@RequestBody @Valid CustomerDto customerDto) {
		CustomerDto customerDtoSaved = customerService.save(customerDto);
		return new ResponseEntity<CustomerDto>(customerDtoSaved, HttpStatus.CREATED);
	}

	@ResponseBody
	@PutMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<CustomerDto> updateCustomer(@RequestBody @Valid CustomerDto customerDto) {
		CustomerDto customerDtoUpdated = customerService.update(customerDto);
		return new ResponseEntity<CustomerDto>(customerDtoUpdated, HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
		customerService.delete(id);
		return ResponseUtil.result_200("Cliente excluído com sucesso!");
	}

	@ResponseBody
	@GetMapping("/{id}/contacts")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
	public ResponseEntity<SimplePage<ContactDto>> contacts(@PathVariable("id") Long customerId,
			@SortDefault(sort = "contactType") @PageableDefault(size = PAGE.SIZE) Pageable pageable) {
		return ResponseEntity.ok(customerContactService.findAll(customerId, ContactService.BY_CUSTOMER, pageable));
	}

}
