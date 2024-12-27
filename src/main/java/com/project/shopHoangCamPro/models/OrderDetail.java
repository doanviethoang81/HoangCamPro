package com.project.shopHoangCamPro.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name ="order_details")
public class OrderDetail {
    @Id // tu dong tang len 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant product;

    @Column(name = "number_of_products",nullable = false )
    private int numberOfProducts;

    @Column(name = "price",nullable = false )
    private Float price;

    @Column(name = "total_price",nullable = false )
    private Float totalPrice;
}

