package com.darcan.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@IdClass(UserRoleId.class)
public class UserRoleEntity {
    
    @Id
    @Column(nullable = false, length = 30)
    private String username;

    @Id
    @Column(nullable = false, length = 20)
    private String role;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime grantedDate;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "name",  insertable = false, updatable = false)
    private UserEntity user;
}
