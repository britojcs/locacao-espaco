package br.com.cincoquatro.locacaoespaco.adapter;

import org.springframework.stereotype.Component;

import br.com.cincoquatro.locacaoespaco.dto.ContractDto;
import br.com.cincoquatro.locacaoespaco.model.Contract;

@Component
public class ContractAdapter extends BaseAdapter<Contract, ContractDto> {

	@Override
	public ContractDto toDto(Contract contract) {
		if (contract == null)
			return null;

		ContractDto contractDto = new ContractDto();
		contractDto.setId(contract.getId());
		contractDto.setName(contract.getName());
		contractDto.setDescription(contract.getDescription());
		contractDto.setContent(contract.getContent());
		contractDto.setEnabled(contract.getEnabled());
		contractDto.setUpdatedAt(contract.getUpdatedAt());

		return contractDto;
	}

	@Override
	public Contract transferProperties(Contract contract, ContractDto contractDto) {
		if (contractDto == null)
			return null;

		if (contract == null)
			contract = new Contract();

		contract.setId(contractDto.getId());
		contract.setName(contractDto.getName());
		contract.setDescription(contractDto.getDescription());
		contract.setContent(contractDto.getContent());
		contract.setEnabled(contractDto.getEnabled());

		return contract;
	}

}
