package br.com.cincoquatro.locacaoespaco.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.cincoquatro.locacaoespaco.adapter.AdditionalAdapter;
import br.com.cincoquatro.locacaoespaco.dto.AdditionalDto;
import br.com.cincoquatro.locacaoespaco.exception.CustomException;
import br.com.cincoquatro.locacaoespaco.exception.EntityNotFoundException;
import br.com.cincoquatro.locacaoespaco.model.Additional;
import br.com.cincoquatro.locacaoespaco.repository.AdditionalRepository;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@Service
@Transactional
public class AdditionalService {

	@Autowired
	private AdditionalAdapter additionalAdapter;

	@Autowired
	private AdditionalRepository additionalRepository;

	public AdditionalDto save(AdditionalDto additionalDto) {
		Additional additionalFromDto = additionalAdapter.toModel(additionalDto);

		Additional additionalSaved = additionalRepository.save(additionalFromDto);

		return additionalAdapter.toDto(additionalSaved);
	}

	public AdditionalDto update(AdditionalDto additionalDto) {
		Long id = additionalDto.getId();

		Additional additionalSaved = additionalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Additional.class, "id", String.valueOf(id)));

		Additional additionalUpdated = additionalRepository.save(additionalAdapter.toModel(additionalDto, additionalSaved));

		return additionalAdapter.toDto(additionalUpdated);
	}

	public void delete(Long id) {
		if (!additionalRepository.existsById(id))
			throw new EntityNotFoundException(Additional.class, "id", String.valueOf(id));

		additionalRepository.deleteById(id);
	}

	public AdditionalDto findById(Long id) {
		Additional additional = additionalRepository.findById(id).orElseThrow(() -> new CustomException("Este adicional não existe", HttpStatus.BAD_REQUEST));

		return additionalAdapter.toDto(additional);
	}

	public SimplePage<AdditionalDto> findAll(final Pageable pageable) {
        final Page<Additional> additionalsPage = additionalRepository.findAll(pageable);
        return new SimplePage<>(additionalsPage.getContent()
                .stream()
                .map(additional -> additionalAdapter.toDto(additional))
                .collect(Collectors.toList()),
                additionalsPage.getTotalElements(), pageable);
    }

}
