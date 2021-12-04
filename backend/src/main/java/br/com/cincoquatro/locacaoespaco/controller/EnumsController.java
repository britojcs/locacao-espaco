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
import br.com.cincoquatro.locacaoespaco.dto.LeaseTypeDto;
import br.com.cincoquatro.locacaoespaco.dto.RoleDto;
import br.com.cincoquatro.locacaoespaco.dto.StateDto;
import br.com.cincoquatro.locacaoespaco.model.enums.ContactType;
import br.com.cincoquatro.locacaoespaco.model.enums.LeaseType;
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

		Arrays.asList(contactTypes).forEach(contactType -> {
			contactTypesDto.add(new ContactTypeDto(contactType.name(), contactType.getDescription()));
		});

		return ResponseEntity.ok().body(contactTypesDto);
	}

	@ResponseBody
	@GetMapping("/lease-types")
	public ResponseEntity<?> leaseTypes() {
		LeaseType[] leaseTypes = LeaseType.values();
		List<LeaseTypeDto> leaseTypesDto = new ArrayList<>();

		Arrays.asList(leaseTypes).forEach(leaseType -> {
			leaseTypesDto.add(new LeaseTypeDto(leaseType.name(), leaseType.getDescription()));
		});

		return ResponseEntity.ok().body(leaseTypesDto);
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
