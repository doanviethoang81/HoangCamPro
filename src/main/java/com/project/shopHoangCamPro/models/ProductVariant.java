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
@Table(name ="product_variants")
public class ProductVariant {

    @Id // tu dong tang len 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name="storage", nullable = false)
    private String storage;

    @Column(name="discount", nullable = false)
    private String discount;

    @Column(name = "price", nullable = false)
    private String price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
