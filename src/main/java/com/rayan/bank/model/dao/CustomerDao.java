package com.rayan.bank.model.dao;

import com.rayan.bank.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer,Integer> {


}
