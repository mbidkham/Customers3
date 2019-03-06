package com.example.demo.model.dao;

import com.example.demo.model.entity.LegalCustomer;
import com.example.demo.model.entity.RealCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RealCustomerDao extends JpaRepository<RealCustomer,Integer> {

    @Query("select c from RealCustomer c WHERE UPPER(c.name) LIKE  %:name%")
    public List<RealCustomer> findByName(@Param("name") String name);

    public RealCustomer findByNationalCode(String nationalCode);

}
