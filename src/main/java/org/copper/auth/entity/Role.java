package org.copper.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.copper.auth.common.RoleCode;

@Getter
@Setter
@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleCode code;

    private String description;

}