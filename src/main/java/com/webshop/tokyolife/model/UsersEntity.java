package com.webshop.tokyolife.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity extends BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",insertable = false,updatable = false)
    protected Integer userId;

    @Column(name = "email")
    private String email;

    @Column(name = "UUID")
    private UUID uuid;

    @Column(name = "password")
    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "enable")
    private Boolean enable;

    @PrePersist
    public void prePersistUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> roles ;

    @OneToMany(mappedBy = "usersEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<AddressEntity> address;

}
