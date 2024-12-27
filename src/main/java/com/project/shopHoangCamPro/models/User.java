package com.project.shopHoangCamPro.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table( name ="users")
@Builder

public class User {
    @Id // tu dong tang len 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên không được để trống! ")
    @Column(name = "name",length = 300)
    private String name;

    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải bao gồm đúng 10 chữ số!")
    @Pattern(regexp = "^[0-9]*$", message = "Mật khẩu chỉ được chứa các ký tự số!")
    @Column(name = "phone",length = 10, nullable = false)
    private String phone;

    @NotBlank(message = "Địa chỉ không được để trống! ")
    @Column(name = "address",length = 300)
    private String address;

    @NotNull(message = "Ngày sinh không được để trống! ")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ!")
    @Column(name= "date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Column(name= "is_active")
    private Boolean isActive;

//    @NotBlank(message = "Mật khẩu không được để trống! ")
    @Size(min = 3, message = "Mật khẩu phải chứa ít nhất 3 ký tự!")
    @Column(name = "password",length = 100,nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role_id;
}
