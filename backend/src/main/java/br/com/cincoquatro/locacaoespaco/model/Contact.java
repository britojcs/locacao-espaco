package br.com.cincoquatro.locacaoespaco.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.cincoquatro.locacaoespaco.model.enums.ContactType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8907603540981713311L;

	@Enumerated(EnumType.STRING)
	@Column(name = "contact_type", length = 30)
	private ContactType contactType;

	@Column(name = "name", nullable = false, length = 80)
	private String name;

	@Column(name = "description", length = 200)
	private String description;

	@ManyToOne
	@JoinTable(name = "customer_contacts", 
		joinColumns = {
			@JoinColumn(name = "contact_id", referencedColumnName = "id", updatable = false) }, 
		inverseJoinColumns = {
			@JoinColumn(name = "customer_id", referencedColumnName = "id", updatable = false) })
	private Customer customer;

}
