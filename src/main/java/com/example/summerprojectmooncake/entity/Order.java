package com.example.summerprojectmooncake.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String address;
    private Date createdDate = new Date();
    private String receiver;
    private String phoneNumber;
    private Integer orderStatus;//1: đã đặt hàng, 0: đã hủy
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> listOrderDetail;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(String address, Date createdDate, String receiver, String phoneNumber, Integer orderStatus) {
        this.address = address;
        this.createdDate = createdDate;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.orderStatus = orderStatus;
    }
}
