package br.com.cincoquatro.locacaoespaco.adapter;

import org.springframework.stereotype.Component;

import br.com.cincoquatro.locacaoespaco.dto.LeaseTypeDto;
import br.com.cincoquatro.locacaoespaco.dto.PlaceDto;
import br.com.cincoquatro.locacaoespaco.model.Place;
import br.com.cincoquatro.locacaoespaco.util.StringUtil;

@Component
public class PlaceAdapter extends BaseAdapter<Place, PlaceDto> {

	@Override
	public PlaceDto toDto(Place place) {
		if (place == null)
			return null;

		PlaceDto placeDto = new PlaceDto();
		placeDto.setId(place.getId());
		placeDto.setLeaseType(new LeaseTypeDto().toDto(place.getLeaseType()));
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
		place.setLeaseType(new LeaseTypeDto().toModel(placeDto.getLeaseType().name));
		place.setName(StringUtil.toUpperCase(placeDto.getName()));
		place.setValue(placeDto.getValue());
		place.setEnabled(placeDto.getEnabled());

		return place;
	}

}
