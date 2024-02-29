package com.example.summerprojectmooncake.repository;

import com.example.summerprojectmooncake.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
