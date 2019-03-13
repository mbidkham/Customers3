package com.rayan.bank.model.dao;

import com.rayan.bank.model.entity.LegalCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LegalCustomerDao extends JpaRepository<LegalCustomer,Integer> {

    @Query("select c from LegalCustomer c WHERE UPPER(c.name) LIKE  %:name%")
    public List<LegalCustomer> findByName(@Param("name") String name);
    public LegalCustomer findByLegalCode(  String legalCode);
    public Optional<LegalCustomer> findById(Integer id);


}
