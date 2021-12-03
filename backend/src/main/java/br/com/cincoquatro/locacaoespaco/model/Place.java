package br.com.cincoquatro.locacaoespaco.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.com.cincoquatro.locacaoespaco.model.enums.PlaceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "places")
public class Place extends BaseEntityAudit implements Serializable {

	private static final long serialVersionUID = -5789203922692449051L;

	@Enumerated(EnumType.STRING)
	@Column(name = "place_type", length = 30)
	private PlaceType placeType;

	@Column(name = "name", nullable = false, length = 80)
	private String name;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;

}
