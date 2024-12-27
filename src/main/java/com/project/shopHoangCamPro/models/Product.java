package com.project.shopHoangCamPro.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="products")
public class Product {

        @Id // tu dong tang len 1
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @NotBlank(message = "Tên sản phẩm không được để trống! ")
        @Size(max = 200, message = "Tên sản phẩm không được vượt quá 200 ký tự!")
        @Column(name = "name", nullable = false,length = 200)
        private String name;

        @NotBlank(message = "Tên thương hiệu không được để trống! ")
        @Size(max = 100, message = "Tên sản phẩm không được vượt quá 100 ký tự!")
        @Column(name="brand")
        private String brand;

        @Column(name="image_url", length = 300)
        private String imageUrl;

//        private Float price;
        @NotBlank(message = "Mô tả không được để trống! ")
        @Column(name = "description",columnDefinition = "TEXT")
        private String description;

        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;
}
