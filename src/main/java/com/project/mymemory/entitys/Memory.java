package com.project.mymemory.entitys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table( name = "memories")
@Data
public class Memory {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Getter
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
