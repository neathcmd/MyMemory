package com.project.mymemory.entitys;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;          // Final image URL (local file or external link)
    private String fileName;     // For uploaded files only
    private LocalDateTime createdAt;
}
