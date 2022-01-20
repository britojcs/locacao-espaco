package br.com.cincoquatro.locacaoespaco.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEnumDto<E extends Enum<?>, D> {

	public String name;
	public String description;

	public abstract D toDto(E enumeration);

	public abstract Set<D> toDto(Set<E> enumerations);

	public abstract E toModel(String enumName);

	public abstract Set<E> toModel(Set<D> enumerations);

}
