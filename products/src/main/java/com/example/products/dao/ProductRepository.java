package com.example.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.products.beans.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}