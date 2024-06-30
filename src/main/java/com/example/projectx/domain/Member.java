package com.example.projectx.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phoneNumber;
}
