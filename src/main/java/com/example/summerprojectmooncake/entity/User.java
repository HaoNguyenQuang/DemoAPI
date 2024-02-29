package com.example.summerprojectmooncake.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

@Table(name = "user")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phoneNumber;
    @NaturalId(mutable = true)
    private String email;
    private String address;
    private String password;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;
    private boolean isEnable = false;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @ManyToMany(fetch = FetchType.EAGER)// TODO: check
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> listRole = new HashSet<>();

    public User() {
        this.setCreated(new Date());
    }

    public User(String name, String phoneNumber, String email, String address, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
    }
}
