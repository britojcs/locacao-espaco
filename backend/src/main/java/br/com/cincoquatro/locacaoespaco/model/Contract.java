package br.com.cincoquatro.locacaoespaco.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contracts")
public class Contract extends BaseEntityAudit implements Serializable {

	private static final long serialVersionUID = 6978066898177332966L;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "description", length = 250)
	private String description;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;

}
