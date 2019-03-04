package com.example.demo.model.dao;

import com.example.demo.model.entity.LegalCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalCustomerDao extends JpaRepository<LegalCustomer,Integer> {
    public LegalCustomer findByLegalCode(String legalCode);
}
