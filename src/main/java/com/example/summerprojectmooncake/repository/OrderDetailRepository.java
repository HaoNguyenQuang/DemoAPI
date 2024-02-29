package com.example.summerprojectmooncake.repository;

import com.example.summerprojectmooncake.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
}
