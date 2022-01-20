package br.com.cincoquatro.locacaoespaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cincoquatro.locacaoespaco.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

}
