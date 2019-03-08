package com.example.demo.model.entity;

import com.example.demo.model.dao.CustomerDao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class LegalCustomer extends Customer {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull(message = "کد ثبت شرکت  را وارد نکرده اید ")
    @Column(unique = true)
    private String legalCode;

    private String tel;

    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLegalCode() {
        return legalCode;
    }

    public void setLegalCode(String legalCode) {
        this.legalCode = legalCode;
    }
    @Override
    public String toString() {
        return "name: "+ this.getName()+"legalCode: "+"\n"+this.legalCode;
    }
}
