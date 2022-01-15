package br.com.cincoquatro.locacaoespaco.adapter;

import org.springframework.stereotype.Component;

import br.com.cincoquatro.locacaoespaco.dto.CustomerDto;
import br.com.cincoquatro.locacaoespaco.dto.StateDto;
import br.com.cincoquatro.locacaoespaco.model.Customer;
import br.com.cincoquatro.locacaoespaco.util.StringUtil;

@Component
public class CustomerAdapter extends BaseAdapter<Customer, CustomerDto> {

	@Override
	public CustomerDto toDto(Customer customer) {
		if (customer == null)
			return null;

		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setFullname(customer.getFullname());
		customerDto.setCpfCnpj(customer.getCpfCnpj());
		customerDto.setCep(customer.getCep());
		customerDto.setAddress(customer.getAddress());
		customerDto.setAddressComplement(customer.getAddressComplement());
		customerDto.setDistrict(customer.getDistrict());
		customerDto.setCity(customer.getCity());
		customerDto.setState(new StateDto().toDto(customer.getState()));

		return customerDto;
	}

	@Override
	public Customer transferProperties(Customer customer, CustomerDto customerDto) {
		if (customerDto == null)
			return null;

		if (customer == null)
			customer = new Customer();

		customer.setId(customerDto.getId());
		customer.setFullname(StringUtil.toUpperCase(customerDto.getFullname()));
		customer.setCpfCnpj(customerDto.getCpfCnpj());
		customer.setCep(customerDto.getCep());
		customer.setAddress(StringUtil.toUpperCase(customerDto.getAddress()));
		customer.setAddressComplement(StringUtil.toUpperCase(customerDto.getAddressComplement()));
		customer.setDistrict(StringUtil.toUpperCase(customerDto.getDistrict()));
		customer.setCity(StringUtil.toUpperCase(customerDto.getCity()));
		customer.setState(customerDto.getState() != null ? new StateDto().toModel(customerDto.getState().name) : null);

		return customer;
	}

}
