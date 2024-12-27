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
@Table(name ="cart_details")
public class CartDetail {

    @Id // tu dong tang len 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private Cart cart;

    @JoinColumn(name ="quantity")
    private Integer quantity;

    @JoinColumn(name="price")
    private Float price;
}
