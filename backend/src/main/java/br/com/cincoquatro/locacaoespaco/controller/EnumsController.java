package br.com.cincoquatro.locacaoespaco.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cincoquatro.locacaoespaco.dto.ContactTypeDto;
import br.com.cincoquatro.locacaoespaco.dto.RoleDto;
import br.com.cincoquatro.locacaoespaco.dto.StateDto;
import br.com.cincoquatro.locacaoespaco.model.enums.ContactType;
import br.com.cincoquatro.locacaoespaco.model.enums.Role;
import br.com.cincoquatro.locacaoespaco.model.enums.State;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/enums")
public class EnumsController {

	@ResponseBody
	@GetMapping("/roles")
	public ResponseEntity<?> roles() {
		Role[] roles = Role.values();
		List<RoleDto> rolesDto = new ArrayList<>();

		Arrays.asList(roles).forEach(role -> {
			rolesDto.add(new RoleDto(role.name(), role.getDescription()));
		});

		return ResponseEntity.ok().body(rolesDto);
	}

	@ResponseBody
	@GetMapping("/contact-types")
	public ResponseEntity<?> contactTypes() {
		ContactType[] contactTypes = ContactType.values();
		List<ContactTypeDto> contactTypesDto = new ArrayList<>();

		Arrays.asList(contactTypes).forEach(role -> {
			contactTypesDto.add(new ContactTypeDto(role.name(), role.getDescription()));
		});

		return ResponseEntity.ok().body(contactTypesDto);
	}
	
	@ResponseBody
	@GetMapping("/states")
	public ResponseEntity<?> states() {
		State[] states = State.values();
		List<StateDto> statesDto = new ArrayList<>();

		Arrays.asList(states).forEach(role -> {
			statesDto.add(new StateDto(role.name(), role.getDescription()));
		});

		return ResponseEntity.ok().body(statesDto);
	}

}
