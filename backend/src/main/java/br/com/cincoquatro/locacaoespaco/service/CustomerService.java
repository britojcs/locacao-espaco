package br.com.cincoquatro.locacaoespaco.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cincoquatro.locacaoespaco.adapter.CustomerAdapter;
import br.com.cincoquatro.locacaoespaco.dto.CustomerDto;
import br.com.cincoquatro.locacaoespaco.exception.EntityNotFoundException;
import br.com.cincoquatro.locacaoespaco.model.Customer;
import br.com.cincoquatro.locacaoespaco.repository.CustomerRepository;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerAdapter customerAdapter;

	@Autowired
	private CustomerRepository customerRepository;

	public CustomerDto save(CustomerDto customerDto) {
		Customer customerFromDto = customerAdapter.toModel(customerDto);

		Customer customerSaved = customerRepository.save(customerFromDto);

		return customerAdapter.toDto(customerSaved);
	}

	public CustomerDto update(CustomerDto customerDto) {
		Long id = customerDto.getId();
		Customer customerSaved = customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Customer.class, "id", String.valueOf(id)));

		Customer customerUpdated = customerRepository.save(customerAdapter.toModel(customerDto, customerSaved));

		return customerAdapter.toDto(customerUpdated);
	}

	public void delete(Long id) {
		if (!customerRepository.existsById(id))
			throw new EntityNotFoundException(Customer.class, "id", String.valueOf(id));

		customerRepository.deleteById(id);
	}
	
	public CustomerDto findById(Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Customer.class, "id", String.valueOf(id)));
		return customerAdapter.toDto(customer);
	}
	
	public SimplePage<CustomerDto> findAll(final Pageable pageable) {
        final Page<Customer> customersPage = customerRepository.findAll(pageable);
        return new SimplePage<>(customersPage.getContent()
                .stream()
                .map(customer -> customerAdapter.toDto(customer))
                .collect(Collectors.toList()),
                customersPage.getTotalElements(), pageable);
    }

}
