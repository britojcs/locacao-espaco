package br.com.cincoquatro.locacaoespaco.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cincoquatro.locacaoespaco.adapter.ContractAdapter;
import br.com.cincoquatro.locacaoespaco.dto.ContractDto;
import br.com.cincoquatro.locacaoespaco.exception.EntityNotFoundException;
import br.com.cincoquatro.locacaoespaco.model.Contract;
import br.com.cincoquatro.locacaoespaco.repository.ContractRepository;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@Service
@Transactional
public class ContractService {

	@Autowired
	private ContractAdapter contractAdapter;

	@Autowired
	private ContractRepository contractRepository;

	public ContractDto save(ContractDto contractDto) {
		Contract contractFromDto = contractAdapter.toModel(contractDto);

		Contract contractSaved = contractRepository.save(contractFromDto);

		return contractAdapter.toDto(contractSaved);
	}

	public ContractDto update(ContractDto contractDto) {
		Long id = contractDto.getId();
		Contract contractSaved = contractRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Contract.class, "id", String.valueOf(id)));

		Contract contractUpdated = contractRepository.save(contractAdapter.toModel(contractDto, contractSaved));
		
		ContractDto contractDtoUpdated = contractAdapter.toDto(contractUpdated);
		contractDtoUpdated.setUpdatedAt(LocalDateTime.now()); // current updatedAt
		
		return contractDtoUpdated;
	}

	public void delete(Long id) {
		if (!contractRepository.existsById(id))
			throw new EntityNotFoundException(Contract.class, "id", String.valueOf(id));

		contractRepository.deleteById(id);
	}
	
	public ContractDto findById(Long id) {
		Contract contract = contractRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Contract.class, "id", String.valueOf(id)));
		return contractAdapter.toDto(contract);
	}
	
	public SimplePage<ContractDto> findAll(final Pageable pageable) {
        final Page<Contract> contractPage = contractRepository.findAll(pageable);
        return new SimplePage<>(contractPage.getContent()
                .stream()
                .map(contract -> contractAdapter.toDto(contract))
                .collect(Collectors.toList()),
                contractPage.getTotalElements(), pageable);
    }
	
	public List<ContractDto> findAllActives() {
        final List<Contract> contractList = contractRepository.findByEnabled(Boolean.TRUE);
        return contractList
                .stream()
                .map(contract -> contractAdapter.toDto(contract))
                .collect(Collectors.toList());
    }

}
