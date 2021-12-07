package br.com.cincoquatro.locacaoespaco.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "additional_packages")
public class AdditionalPackage implements Serializable {

	private static final long serialVersionUID = -96099506416573129L;

	@Id
	@Column(name = "additional_id")
	private Long id;

	@MapsId
	@OneToOne
	private Additional additional;

	@NotBlank
	@Column(name = "description", nullable = false)
	private String description;

}
