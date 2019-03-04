package com.example.demo.model.entity;

import com.example.demo.dto.TelType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tel {
    @Id
    @GeneratedValue
    private Integer id;
    private String num;
    private TelType type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public TelType getType() {
        return type;
    }

    public void setType(TelType type) {
        this.type = type;
    }
}
