package com.example.demo.model.dao;

import com.example.demo.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.AbstractQuery;

public interface CustomerDao extends JpaRepository<Customer,Integer> {


}
