package br.com.cincoquatro.locacaoespaco.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.cincoquatro.locacaoespaco.model.enums.AdditionalRequiredType;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "required_type", length = 30)
	private AdditionalRequiredType requiredType;

	@NotNull
	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;

	@PrimaryKeyJoinColumn
	@OneToOne(mappedBy = "additional", cascade = CascadeType.ALL)
	private AdditionalPackage additionalPackage;

}
