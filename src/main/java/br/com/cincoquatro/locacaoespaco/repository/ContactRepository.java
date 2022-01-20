package br.com.cincoquatro.locacaoespaco.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cincoquatro.locacaoespaco.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	Optional<Contact> findByCustomerIdAndId(Long customerId, Long id);

	Page<Contact> findAllByCustomerId(Long customerId, Pageable pageable);

}
