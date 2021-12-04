package br.com.cincoquatro.locacaoespaco.controller;

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

import br.com.cincoquatro.locacaoespaco.dto.AdditionalDto;
import br.com.cincoquatro.locacaoespaco.service.AdditionalService;
import br.com.cincoquatro.locacaoespaco.util.Constants.PAGE;
import br.com.cincoquatro.locacaoespaco.util.ResponseUtil;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@RestController
@RequestMapping("/api/aditionals")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdditionalController {

	@Autowired
	private AdditionalService additionalService;

	@ResponseBody
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADDITIONAL')")
	public ResponseEntity<SimplePage<AdditionalDto>> additionals(@SortDefault(sort = "name") @PageableDefault(size = PAGE.SIZE) Pageable pageable) {
		return ResponseEntity.ok(additionalService.findAll(pageable));
	}

	@ResponseBody
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADDITIONAL')")
	public ResponseEntity<AdditionalDto> getAdditional(@PathVariable("id") Long id) {
		AdditionalDto additionalDto = additionalService.findById(id);
		return new ResponseEntity<AdditionalDto>(additionalDto, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADDITIONAL')")
	public ResponseEntity<AdditionalDto> newAdditional(@RequestBody @Valid AdditionalDto additionalDto) {
		AdditionalDto additionalDtoSaved = additionalService.save(additionalDto);
		return new ResponseEntity<AdditionalDto>(additionalDtoSaved, HttpStatus.CREATED);
	}

	@ResponseBody
	@PutMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADDITIONAL')")
	public ResponseEntity<AdditionalDto> updateAdditional(@RequestBody @Valid AdditionalDto additionalDto) {
		AdditionalDto additionalDtoUpdated = additionalService.update(additionalDto);
		return new ResponseEntity<AdditionalDto>(additionalDtoUpdated, HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADDITIONAL')")
	public ResponseEntity<?> deleteAdditional(@PathVariable("id") Long id) {
		additionalService.delete(id);
		return ResponseUtil.mountResult_200("Adicional excluído com sucesso!");
	}

}
