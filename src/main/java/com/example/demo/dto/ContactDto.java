package com.example.demo.dto;

import java.util.List;

public class ContactDto {
    private String email;
    private List<TelDto> tel;


    public ContactDto(String mobile, List<TelDto> tel, String email) {
        this.email = email;
        this.tel=tel;
    }

    public ContactDto() {
    }

    public List<TelDto> getTel() {
        return tel;
    }

    public void setTel(List<TelDto> tel) {
        this.tel = tel;
    }



    public void setMobile(String mobile) {
        this.email = email;

    }




    public String getMobile() {
        return email;
    }




}
