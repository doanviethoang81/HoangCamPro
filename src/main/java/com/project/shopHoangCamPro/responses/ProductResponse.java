package com.project.shopHoangCamPro.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopHoangCamPro.models.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String name;
    private String brand;
    private String imageUrl;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .brand(product.getBrand())
                .imageUrl(product.getImageUrl())
//                .price(product.getPrice())
                .description(product.getDescription())
                .categoryId(Long.valueOf(product.getCategory().getId()))
                .build();
        return productResponse;
    }
}
