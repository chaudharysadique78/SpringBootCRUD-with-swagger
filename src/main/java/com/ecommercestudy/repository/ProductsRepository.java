package com.ecommercestudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercestudy.model.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {

}
