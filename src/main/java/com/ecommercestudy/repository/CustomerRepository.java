package com.ecommercestudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercestudy.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
