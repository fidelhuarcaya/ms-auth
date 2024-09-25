package org.copper.auth.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable {
  private String createdBy;

  private LocalDateTime createdDate;

  private String updatedBy;

  private LocalDateTime updatedDate;
}