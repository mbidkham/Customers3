package com.example.demo.model.dao;

import com.example.demo.model.entity.LegalCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LegalCustomerDao extends JpaRepository<LegalCustomer,Integer> {

    @Query("select c from LegalCustomer c WHERE UPPER(c.name) LIKE  %:name%")
    public List<LegalCustomer> findByName(@Param("name") String name);
    public LegalCustomer findByLegalCode(  String legalCode);



}
