package com.medkhelifi.tutorials.springsecurityhelloworld.models;

import lombok.Data;
//import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
//@Document
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ROLE")
    private String roleId;

    private String role;

    private String description;
}
