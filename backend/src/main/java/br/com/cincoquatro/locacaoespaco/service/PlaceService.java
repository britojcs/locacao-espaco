package br.com.cincoquatro.locacaoespaco.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.cincoquatro.locacaoespaco.adapter.PlaceAdapter;
import br.com.cincoquatro.locacaoespaco.dto.PlaceDto;
import br.com.cincoquatro.locacaoespaco.exception.CustomException;
import br.com.cincoquatro.locacaoespaco.exception.EntityNotFoundException;
import br.com.cincoquatro.locacaoespaco.model.Place;
import br.com.cincoquatro.locacaoespaco.repository.PlaceRepository;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@Service
@Transactional
public class PlaceService {

	@Autowired
	private PlaceAdapter placeAdapter;

	@Autowired
	private PlaceRepository placeRepository;

	public PlaceDto save(PlaceDto placeDto) {
		Place placeFromDto = placeAdapter.toModel(placeDto);

		Place placeSaved = placeRepository.save(placeFromDto);

		return placeAdapter.toDto(placeSaved);
	}

	public PlaceDto update(PlaceDto placeDto) {
		Long id = placeDto.getId();

		Place placeSaved = placeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Place.class, "id", String.valueOf(id)));

		Place placeUpdated = placeRepository.save(placeAdapter.toModel(placeDto, placeSaved));

		return placeAdapter.toDto(placeUpdated);
	}

	public void delete(Long id) {
		if (!placeRepository.existsById(id))
			throw new EntityNotFoundException(Place.class, "id", String.valueOf(id));

		placeRepository.deleteById(id);
	}

	public PlaceDto findById(Long id) {
		Place place = placeRepository.findById(id).orElseThrow(() -> new CustomException("Este espaço não existe", HttpStatus.BAD_REQUEST));

		return placeAdapter.toDto(place);
	}

	public SimplePage<PlaceDto> findAll(final Pageable pageable) {
        final Page<Place> placesPage = placeRepository.findAll(pageable);
        return new SimplePage<>(placesPage.getContent()
                .stream()
                .map(place -> placeAdapter.toDto(place))
                .collect(Collectors.toList()),
                placesPage.getTotalElements(), pageable);
    }

}
