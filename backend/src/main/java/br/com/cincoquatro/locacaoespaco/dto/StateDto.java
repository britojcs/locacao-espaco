package br.com.cincoquatro.locacaoespaco.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.cincoquatro.locacaoespaco.model.enums.State;

public class StateDto extends BaseEnumDto<State, StateDto> {

	public StateDto() {
	}

	public StateDto(String name, String description) {
		super(name, description);
	}

	@Override
	public StateDto toDto(State state) {
		if (state == null)
			return null;

		return new StateDto(state.name(), state.getDescription());
	}

	@Override
	public Set<StateDto> toDto(Set<State> states) {
		return states.stream().map(state -> toDto(state)).collect(Collectors.toSet());
	}

	@Override
	public State toModel(String stateName) {
		if (stateName == null)
			return null;

		return State.getByStateName(stateName);
	}

	@Override
	public Set<State> toModel(Set<StateDto> statesDto) {
		return statesDto.stream().map(stateDto -> toModel(stateDto.getName())).collect(Collectors.toSet());
	}

}
