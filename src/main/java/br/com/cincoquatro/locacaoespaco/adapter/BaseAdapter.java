package br.com.cincoquatro.locacaoespaco.adapter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseAdapter<Entity, Dto> {

	public abstract Dto toDto(Entity entity);

	public abstract Entity transferProperties(Entity entity, Dto dto);

	public Entity toModel(Dto dto) {
		return transferProperties(null, dto);
	}

	public Entity toModel(Dto dto, Entity entity) {
		return transferProperties(entity, dto);
	}

	public List<Entity> toModel(List<Dto> dtoList) {
		return dtoList.stream().map(dto -> toModel(dto)).collect(Collectors.toList());
	}

	public List<Dto> toDto(List<Entity> entityList) {
		return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
	}

}
