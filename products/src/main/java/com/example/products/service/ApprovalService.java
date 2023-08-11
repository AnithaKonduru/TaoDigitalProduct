package com.example.products.service;

import java.util.List;

import com.example.products.beans.Approvalqueue;

public interface ApprovalService {
	Approvalqueue createProduct(Approvalqueue approval);

	Approvalqueue getProductById(Long productId);

    List<Approvalqueue> getAllProducts();

    Approvalqueue updateProduct(Approvalqueue approval);
    
    Approvalqueue updateApprovalSts(Long productId, String sts);

    void deleteProduct(Long productId);
}