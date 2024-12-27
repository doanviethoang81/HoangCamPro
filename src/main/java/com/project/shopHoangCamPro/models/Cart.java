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
@Table(name ="shopping_carts")
public class Cart {

    @Id // tu dong tang len 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
