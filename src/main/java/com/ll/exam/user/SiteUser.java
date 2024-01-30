package com.ll.exam.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class SiteUser { // 스프링 시큐리티에 User란 클래스가 이미 내장되어 있어서 구분하려고

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
}