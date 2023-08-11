package com.example.products.controller;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.products.beans.Approvalqueue;
import com.example.products.beans.Product;
import com.example.products.service.ApprovalService;
import com.example.products.service.ProductService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ProductController {

	@Autowired
    private ProductService productService;

	@Autowired
    private ApprovalService approvalService;

    
    //1.	API to List Active Products:    
    @GetMapping("api/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<Product> sortedProducts = products.stream().filter(n-> n.getProductstatus().equals("Active")).sorted(Comparator.comparing(Product::getPosteddate)
                .reversed()).collect(Collectors.toList());
        return new ResponseEntity<>(sortedProducts, HttpStatus.OK);
    }
    
    
    //2.	API to Search Products by ID:
    @GetMapping("api/products/{productid}")
    public ResponseEntity<Product> getProductById(@PathVariable("productid") String productId){
    	long id = Integer.valueOf(productId);
        Product user = productService.getProductById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
   
    //2.	API to Search Products:
    @GetMapping(value = "api/products/search")
    public ResponseEntity<List<Product>> getProductSearch(@RequestParam(required = false) Optional<String> productname,
    		@RequestParam(required = false) Optional<Long> minPrice, 
    		@RequestParam(required = false) Optional<Long> maxPrice ,
    		@RequestParam(required = false) Optional<String> minPostedDate,
    		@RequestParam(required = false) Optional<String> maxPostedDate){
    	 
        List<Product> products = productService.getAllProducts();
        List<Product> sortedProducts = new ArrayList<Product>();
        if(productname.isPresent()) {
          sortedProducts = products.stream().filter(n-> n.getProductname().equals(productname))
            		.sorted(Comparator.comparing(Product::getPosteddate).reversed()).collect(Collectors.toList());
        }else if(minPrice.isPresent()){
            sortedProducts = products.stream().filter(n-> n.getProductprice() > minPrice.get())
            		.sorted(Comparator.comparing(Product::getPosteddate).reversed()).collect(Collectors.toList());
        	
        }else if(maxPrice.isPresent()){
            sortedProducts = products.stream().filter(n-> n.getProductprice() < maxPrice.get())
            		.sorted(Comparator.comparing(Product::getPosteddate).reversed()).collect(Collectors.toList());      	
        }else {
        	sortedProducts = products.stream().filter(n-> n.getProductstatus().equals("Active")).sorted(Comparator.comparing(Product::getPosteddate)
                    .reversed()).collect(Collectors.toList());       	
        }
        return new ResponseEntity<>(sortedProducts, HttpStatus.OK);
    }   

	//3.	API to Create a Product:
    @PostMapping("api/products/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product){
    	
    	Product savedProduct = new Product();
		Approvalqueue approval = new Approvalqueue();
    	if(product.getProductprice()>10000) {
    		return new ResponseEntity<>("Product price must not exceed $10,000", HttpStatus.BAD_REQUEST);
    	}else if(product.getProductprice()< 10000 && product.getProductprice()>5000){
    		approval.setProductid(product.getProductid());
    		approval.setProductname(product.getProductname());
    		approval.setProductprice(product.getProductprice());
    		approval.setProductstatus(product.getProductstatus());
        	approval = approvalService.createProduct(approval);
    	}else {
    		 savedProduct = productService.createProduct(product);
    	}
    	
        if(null != savedProduct.getProductname()) {
        	return new ResponseEntity<>("Product Saved successfully !", HttpStatus.CREATED);       
        }else if(null != approval.getProductname()) {
        	return new ResponseEntity<>("Product added into Approval Queue successfully !", HttpStatus.CREATED);   
        }else {
        	 return new ResponseEntity<>("Error in input details ", HttpStatus.BAD_REQUEST);	
        }
    }

    //4.	API to Update a Product:
    @PutMapping("api/products/{productid}")
    public ResponseEntity<String> updateProduct(@PathVariable("productid") String productId,
                                           @RequestBody Product product){
    	int id = Integer.valueOf(productId);
        product.setProductid(id);
        String updatedProduct = productService.updateProduct(product);
        if(updatedProduct.length()>0) {
        	return new ResponseEntity<>("Product Updated successfully !", HttpStatus.CREATED);
        }else {
        	 return new ResponseEntity<>("Error in input details ", HttpStatus.BAD_REQUEST);	
        }
    }
    
    //5.	API to Delete a Product:
    @DeleteMapping("api/products/{productid}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productid") String productId){
    	Long id = Long.valueOf(productId);
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);
    }

    //6.	API to Get Products in Approval Queue:   
    @GetMapping("api/products/approval-queue")
    public ResponseEntity<List<Approvalqueue>> getAllApprovalqueue(){
        List<Approvalqueue> products = approvalService.getAllProducts();
        List<Approvalqueue> sortedProducts = products.stream().filter(n-> n.getProductstatus().equals("Active")).sorted(Comparator.comparing(Approvalqueue::getPosteddate)
                .reversed()).collect(Collectors.toList());
        return new ResponseEntity<>(sortedProducts, HttpStatus.OK);
    }
  
    //7. API to Approve a Product:
    @PutMapping("api/products/approval-queue/{approvalId}/approve")
    public ResponseEntity<String> updateApprovalApp(@PathVariable("approvalId") int productId){
    	System.out.println(" before  id::::::::::::::::::::::::"+productId);
    	Long id = Long.valueOf(productId);
    	System.out.println("id::::::::::::::::::::::::"+id);
        Approvalqueue updatedProduct = approvalService.updateApprovalSts(id, "Approve");
        if(null != updatedProduct) {
        	return new ResponseEntity<>("Product Approved successfully !", HttpStatus.CREATED);
        }else {
        	 return new ResponseEntity<>("Error in input details ", HttpStatus.BAD_REQUEST);	
        }
    }

    //8. API to Reject a Product:
    @PutMapping("api/products/approval-queue/{approvalId}/reject")
    public ResponseEntity<String> updateApprovalRej(@PathVariable("approvalId") int productId){
    	Long id = Long.valueOf(productId);
        Approvalqueue updatedProduct = approvalService.updateApprovalSts(id, "Reject");
        if(null != updatedProduct) {
        	return new ResponseEntity<>("Product Rejected successfully !", HttpStatus.CREATED);
        }else {
        	 return new ResponseEntity<>("Error in input details ", HttpStatus.BAD_REQUEST);	
        }
    }

}