package com.example.products.beans;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Approvalqueue")
public class Approvalqueue {

    @Column(name="productid")
    @Id
    private int productid;
    
    @Column(name="productname")
    private String productname;
    
    @Column(name="productprice")
    private Long productprice;
    
    @Column(name="productstatus")
    private String productstatus;
    
    @Column(name="posteddate")
    @CreationTimestamp
    private String posteddate ;

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Long getProductprice() {
		return productprice;
	}

	public void setProductprice(Long productprice) {
		this.productprice = productprice;
	}

	public String getProductstatus() {
		return productstatus;
	}

	public void setProductstatus(String productstatus) {
		this.productstatus = productstatus;
	}

	public String getPosteddate() {
		return posteddate;
	}

	public void setPosteddate(String posteddate) {
		this.posteddate = posteddate;
	}
    
}
