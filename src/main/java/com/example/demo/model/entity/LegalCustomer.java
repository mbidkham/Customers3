package com.example.demo.model.entity;

import com.example.demo.model.dao.CustomerDao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class LegalCustomer extends Customer {

    @NotNull(message = "کد ثبت شرکت  را وارد نکرده اید ")
    @Column(unique = true)
    private String legalCode;

    private String tel;


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
