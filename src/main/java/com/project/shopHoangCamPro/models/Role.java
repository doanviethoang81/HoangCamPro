package com.project.shopHoangCamPro.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="roles")
public class Role {
    @Id // tu dong tang len 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;

    @Column(name = "name", nullable = false)
    private String name;

    public static String ADMIN ="ADMIN";
    public static String USER ="USER";
}
