package com.cognixia.FurnitureAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.FurnitureAPI.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
