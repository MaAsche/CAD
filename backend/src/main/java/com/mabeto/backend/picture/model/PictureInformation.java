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
public class PictureInformation {
    @Id
    @GeneratedValue
    private Long Id;

    private String description = "";

    private LocalDateTime createdAt;
}
