package br.com.cincoquatro.locacaoespaco.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.com.cincoquatro.locacaoespaco.model.enums.State;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customers")
public class Customer extends BaseEntityAudit implements Serializable {

	private static final long serialVersionUID = -8644871575253710899L;

	@Column(name = "fullname", nullable = false, length = 80)
	private String fullname;

	@Column(name = "cpf_cnpj", length = 20)
	private String cpfCnpj;

	@Column(name = "cep", length = 8)
	private String cep;

	@Column(name = "address", length = 50)
	private String address;

	@Column(name = "address_complement", length = 100)
	private String addressComplement;

	@Column(name = "district", length = 50)
	private String district;

	@Column(name = "city", length = 50)
	private String city;

	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private State state;

}
