package com.github.hse24.entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
@ApiModel(description = "Details about the parent ")
public class AbstractEntity {
	
	public AbstractEntity() {
		
	}
	
	@Id
	@GeneratedValue
	@ApiModelProperty(notes = "The generated ID")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		return Objects.equals(this.id, AbstractEntity.class.cast(obj).id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
