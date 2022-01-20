package br.com.cincoquatro.locacaoespaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cincoquatro.locacaoespaco.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
