package com.rayan.bank.model.entity;

import com.rayan.bank.dto.TelType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Phone {
    @Id
    @GeneratedValue
    private Integer id;
    private String number;
    private TelType type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String num) {
        this.number = num;
    }

    public TelType getType() {
        return type;
    }

    public void setType(TelType type) {
        this.type = type;
    }
}
