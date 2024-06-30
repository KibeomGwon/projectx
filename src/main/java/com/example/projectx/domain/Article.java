package com.example.projectx.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_ID")
    private Member writer;
    private String title;
    private String description;
    private String image;
    private String position;
    private String deadline;

    @ElementCollection @Nullable
    private List<Long> applicants;
}
