package com.darcan.auth.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
@IdClass(UserRoleId.class)
public class UserRole {

    @Id
    @Column(nullable = false)
    private String username;

    @Id
    @Column(nullable =  false)
    private String role;
    
    @Column(name = "granted_date", nullable = false)
    private LocalDateTime grantedDate;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "name", insertable = false, updatable = false)
    private UserEntity user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getGrantedDate() {
        return grantedDate;
    }

    public void setGrantedDate(LocalDateTime grantedDate) {
        this.grantedDate = grantedDate;
    }

}