package com.rayan.bank.model.dao;

import com.rayan.bank.model.entity.RealCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RealCustomerDao extends JpaRepository<RealCustomer,Integer> {

    @Query("select c from RealCustomer c WHERE UPPER(c.name) LIKE  %:name%")
    List<RealCustomer> findByName(@Param("name") String name);

    public Optional<RealCustomer> findById(Integer id);
    public RealCustomer findByNationalCode(String nationalCode);


}
