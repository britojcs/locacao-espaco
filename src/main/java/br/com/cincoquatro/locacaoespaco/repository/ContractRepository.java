package br.com.cincoquatro.locacaoespaco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cincoquatro.locacaoespaco.model.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

	List<Contract> findByEnabled(Boolean enabled);

}
