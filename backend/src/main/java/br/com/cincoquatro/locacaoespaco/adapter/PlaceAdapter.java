package br.com.cincoquatro.locacaoespaco.adapter;

import org.springframework.stereotype.Component;

import br.com.cincoquatro.locacaoespaco.dto.PlaceDto;
import br.com.cincoquatro.locacaoespaco.dto.PlaceTypeDto;
import br.com.cincoquatro.locacaoespaco.model.Place;

@Component
public class PlaceAdapter extends BaseAdapter<Place, PlaceDto> {

	@Override
	public PlaceDto toDto(Place place) {
		if (place == null)
			return null;

		PlaceDto placeDto = new PlaceDto();
		placeDto.setId(place.getId());
		placeDto.setPlaceType(new PlaceTypeDto().toDto(place.getPlaceType()));
		placeDto.setName(place.getName());
		placeDto.setValue(place.getValue());
		placeDto.setEnabled(place.getEnabled());

		return placeDto;
	}

	@Override
	public Place transferProperties(Place place, PlaceDto placeDto) {
		if (placeDto == null)
			return null;

		if (place == null)
			place = new Place();

		place.setId(placeDto.getId());
		place.setPlaceType(new PlaceTypeDto().toModel(placeDto.getPlaceType().name));
		place.setName(placeDto.getName());
		place.setValue(placeDto.getValue());
		place.setEnabled(placeDto.getEnabled());

		return place;
	}

}
