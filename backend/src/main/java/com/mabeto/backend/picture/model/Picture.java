package com.mabeto.backend.picture.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue
    private Long Id;

    private String description = "";

    private LocalDateTime createdAt;

    @Lob
    private byte[] image;
}
