package br.com.cincoquatro.locacaoespaco.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cincoquatro.locacaoespaco.dto.ContractDto;
import br.com.cincoquatro.locacaoespaco.service.ContractService;
import br.com.cincoquatro.locacaoespaco.util.Constants.PAGE;
import br.com.cincoquatro.locacaoespaco.util.ResponseUtil;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContractController {

	@Autowired
	private ContractService contractService;
	
	@ResponseBody
	@GetMapping("/actives")
	public ResponseEntity<List<ContractDto>> contractsActives() {
		return ResponseEntity.ok(contractService.findAllActives());
	}
	
	@ResponseBody
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONTRACT')")
	public ResponseEntity<SimplePage<ContractDto>> contracts(
			@SortDefault(sort = "name") @PageableDefault(size = PAGE.SIZE) Pageable pageable) {
		return ResponseEntity.ok(contractService.findAll(pageable));
	}

	@ResponseBody
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONTRACT')")
	public ResponseEntity<ContractDto> getContract(@PathVariable("id") Long id) {
		ContractDto contractDto = contractService.findById(id);
		return new ResponseEntity<ContractDto>(contractDto, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONTRACT')")
	public ResponseEntity<ContractDto> newContract(@RequestBody @Valid ContractDto contractDto) {
		ContractDto contractDtoSaved = contractService.save(contractDto);
		return new ResponseEntity<ContractDto>(contractDtoSaved, HttpStatus.CREATED);
	}

	@ResponseBody
	@PutMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONTRACT')")
	public ResponseEntity<ContractDto> updateContract(@RequestBody @Valid ContractDto contractDto) {
		ContractDto contractDtoUpdated = contractService.update(contractDto);
		return new ResponseEntity<ContractDto>(contractDtoUpdated, HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONTRACT')")
	public ResponseEntity<?> deleteContract(@PathVariable("id") Long id) {
		contractService.delete(id);
		return ResponseUtil.result_200("Contrato excluído com sucesso!");
	}

}
