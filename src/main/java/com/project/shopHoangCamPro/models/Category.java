package com.project.shopHoangCamPro.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name ="categories")
public class Category {
    @Id // tu dong tang len 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Danh mục sản phẩm không được để trống.")
    @Column(name = "name", nullable = false)
    private String name;
}