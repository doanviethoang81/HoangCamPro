package com.project.shopHoangCamPro.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name ="orders")
public class Order {

    @Id // tu dong tang len 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Tên không được để trống! ")
    @Column(name = "recipient_name",length = 100)
    private String recipientName;

    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải bao gồm đúng 10 chữ số!")
    @Pattern(regexp = "^[0-9]*$", message = "Mật khẩu chỉ được chứa các ký tự số!")
    @Column(name = "recipient_phone",length = 10, nullable = false)
    private String recipientPhone;

    @NotBlank(message = "Địa chỉ không được để trống! ")
    @Column(name = "recipient_address",length = 100)
    private String recipientAddress;

    @Column(name = "note",length = 100)
    private String note;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private  String status;

    @Column(name = "active")
    private  Boolean active;

    @Column(name = "total_money")
    private  Float totalMoney;


//    @Column(name = "payment_status")
//    private  String paymentStatus;
//
//    @Column(name = "payment_date")
//    private Date paymentDate;

}
