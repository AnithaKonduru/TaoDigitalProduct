package com.example.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.products.beans.Approvalqueue;

public interface ApprovalRepository extends JpaRepository<Approvalqueue, Long> {

}