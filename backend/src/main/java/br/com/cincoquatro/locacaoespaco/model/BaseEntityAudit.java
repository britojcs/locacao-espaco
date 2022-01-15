package br.com.cincoquatro.locacaoespaco.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class BaseEntityAudit extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1544143346116498966L;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	public void setCreation() {
		this.createdBy = getLoggedUsername();
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void setChange() {
		this.updatedBy = getLoggedUsername();
		this.updatedAt = LocalDateTime.now();
	}

	private String getLoggedUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

}
