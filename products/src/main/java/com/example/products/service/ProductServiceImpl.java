package com.example.products.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.products.beans.Approvalqueue;
import com.example.products.beans.Product;
import com.example.products.dao.ApprovalRepository;
import com.example.products.dao.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductRepository productRepository;

	@Autowired
    private ApprovalRepository approvalRepository;
	 
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public String updateProduct(Product product) {
    	
    	Approvalqueue approval = new Approvalqueue();
    	long id = Long.valueOf(product.getProductid());
        Optional<Product> existingProduct = productRepository.findById(id);
        String result = "";
        if(existingProduct.isPresent()) {
        	int percent = (int)(50 * product.getProductprice()) / 100;
        	if(percent > existingProduct.get().getProductprice()) {         
        		approval.setProductid(product.getProductid());
        		approval.setProductname(product.getProductname());
        		approval.setProductprice(product.getProductprice());
        		approval.setProductstatus(product.getProductstatus());
        		approval = approvalRepository.save(approval);  
        		result = "Updated";
        	}else {
        		 existingProduct.get().setProductid(product.getProductid());
                 existingProduct.get().setProductprice(product.getProductprice());
                 existingProduct.get().setProductname(product.getProductname());
                 existingProduct.get().setProductstatus(product.getProductstatus());
                 productRepository.save(existingProduct.get());
                 result = "Updated";
        	}
              	
        }else {
        	productRepository.save(product);
        	result = "Updated";
        }
        return result;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
       Optional<Product> product = productRepository.findById(productId);
       Approvalqueue approval = new Approvalqueue();
       approval.setProductid(product.get().getProductid());
		approval.setProductname(product.get().getProductname());
		approval.setProductprice(product.get().getProductprice());
		approval.setProductstatus(product.get().getProductstatus());
        approvalRepository.save(approval);
    }
}