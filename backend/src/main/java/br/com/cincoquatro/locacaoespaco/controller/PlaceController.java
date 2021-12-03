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

import br.com.cincoquatro.locacaoespaco.dto.PlaceDto;
import br.com.cincoquatro.locacaoespaco.service.PlaceService;
import br.com.cincoquatro.locacaoespaco.util.Constants.PAGE;
import br.com.cincoquatro.locacaoespaco.util.ResponseUtil;
import br.com.cincoquatro.locacaoespaco.util.SimplePage;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlaceController {

	@Autowired
	private PlaceService placeService;

	@ResponseBody
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PLACE')")
	public ResponseEntity<SimplePage<PlaceDto>> places(@SortDefault(sort = "name") @PageableDefault(size = PAGE.SIZE) Pageable pageable) {
		return ResponseEntity.ok(placeService.findAll(pageable));
	}

	@ResponseBody
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PLACE')")
	public ResponseEntity<PlaceDto> getPlace(@PathVariable("id") Long id) {
		PlaceDto placeDto = placeService.findById(id);
		return new ResponseEntity<PlaceDto>(placeDto, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PLACE')")
	public ResponseEntity<PlaceDto> newPlace(@RequestBody @Valid PlaceDto placeDto) {
		PlaceDto placeDtoSaved = placeService.save(placeDto);
		return new ResponseEntity<PlaceDto>(placeDtoSaved, HttpStatus.CREATED);
	}

	@ResponseBody
	@PutMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PLACE')")
	public ResponseEntity<PlaceDto> updatePlace(@RequestBody @Valid PlaceDto placeDto) {
		PlaceDto placeDtoUpdated = placeService.update(placeDto);
		return new ResponseEntity<PlaceDto>(placeDtoUpdated, HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PLACE')")
	public ResponseEntity<?> deletePlace(@PathVariable("id") Long id) {
		placeService.delete(id);
		return ResponseUtil.mountResult_200("Espaço excluído com sucesso!");
	}

}
