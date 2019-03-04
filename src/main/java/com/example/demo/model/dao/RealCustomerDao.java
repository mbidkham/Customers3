package com.example.demo.model.dao;

import com.example.demo.model.entity.RealCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealCustomerDao extends JpaRepository<RealCustomer,Integer> {


    public RealCustomer findByNationalCode(String nationalCode);

}
