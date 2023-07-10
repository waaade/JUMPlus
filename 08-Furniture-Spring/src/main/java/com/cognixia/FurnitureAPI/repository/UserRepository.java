package com.cognixia.FurnitureAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.FurnitureAPI.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
