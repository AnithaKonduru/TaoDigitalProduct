package com.example.products.service;

import java.util.List;

import com.example.products.beans.Product;

public interface ProductService {
	Product createProduct(Product product);

	Product getProductById(Long productId);

    List<Product> getAllProducts();

    String updateProduct(Product product);

    void deleteProduct(Long productId);
}