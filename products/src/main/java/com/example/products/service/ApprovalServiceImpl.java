package com.example.products.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.products.beans.Approvalqueue;
import com.example.products.dao.ApprovalRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
    private ApprovalRepository approvalRepository;

    @Override
    public Approvalqueue createProduct(Approvalqueue product) {
        return approvalRepository.save(product);
    }

    @Override
    public Approvalqueue getProductById(Long productId) {
        Optional<Approvalqueue> optionalProduct = approvalRepository.findById(productId);
        return optionalProduct.get();
    }

    @Override
    public List<Approvalqueue> getAllProducts() {
        return approvalRepository.findAll();
    }

    @Override
    public Approvalqueue updateProduct(Approvalqueue approval) {
    	
    	Approvalqueue updatedProduct;
    	long id = Long.valueOf(approval.getProductid());
        Optional<Approvalqueue> existingProduct = approvalRepository.findById(id);
        if(existingProduct.isPresent()) {
            existingProduct.get().setProductid(approval.getProductid());
            existingProduct.get().setProductprice(approval.getProductprice());
            existingProduct.get().setProductname(approval.getProductname());
            existingProduct.get().setProductstatus(approval.getProductstatus());
            updatedProduct = approvalRepository.save(existingProduct.get());      	
        }else {
        	updatedProduct = approvalRepository.save(approval);
        }
        return updatedProduct;
    }
    
    @Override
    public Approvalqueue updateApprovalSts(Long productId, String sts) {
    	
    	Approvalqueue updatedProduct = null;
        Optional<Approvalqueue> existingProduct = approvalRepository.findById(productId);
        if(existingProduct.isPresent()) {
            existingProduct.get().setProductstatus(sts);
            updatedProduct = approvalRepository.save(existingProduct.get());      	
        }
        return updatedProduct;
    }

    @Override
    public void deleteProduct(Long productId) {
        approvalRepository.deleteById(productId);
    }
}