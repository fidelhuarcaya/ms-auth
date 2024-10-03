package org.copper.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.copper.auth.common.StatusCode;

@Getter
@Setter
@Entity
@Table(name = "status", uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatusCode code;

    private String description;

}