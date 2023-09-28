package com.webshop.tokyolife.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id",insertable = false,updatable = false)
    protected Integer addressId;
    private String firstName;
    private String lastName;
    private String company;
    private String deliveryAddress;
    private String country;
    private String phone;
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UsersEntity usersEntity;


}
