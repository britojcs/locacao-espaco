package br.com.cincoquatro.locacaoespaco.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "additionals")
public class Additional extends BaseEntityAudit implements Serializable {

	private static final long serialVersionUID = 1100390842699323449L;

	@NotBlank
	@Column(name = "name", nullable = false, length = 200)
	private String name;

	@NotNull
	@Column(name = "value")
	private BigDecimal value;

	@NotNull
	@Column(name = "required", nullable = false)
	private Boolean required = true;

	@NotNull
	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;

	@OneToOne(mappedBy = "additional", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private AdditionalPackage additionalPackage;

}
