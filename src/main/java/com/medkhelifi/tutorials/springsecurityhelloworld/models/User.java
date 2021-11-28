package com.medkhelifi.tutorials.springsecurityhelloworld.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "USERS")
public class User{

    @Id
    @GeneratedValue(  strategy = GenerationType.AUTO )
    @Column(name = "ID_USER")
    private String userId;

    private String firstname;


    private String lastname;

    private String email;

    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "ID_USER"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE"))
    private List<Role> roles = new ArrayList<>();

}
