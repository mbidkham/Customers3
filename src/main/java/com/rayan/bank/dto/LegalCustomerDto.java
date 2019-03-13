package com.rayan.bank.dto;

public class LegalCustomerDto extends CustomerDto {

    private String legalCode;
    private  String tel;

    public LegalCustomerDto(String name, AddressDto address, String legalCode, String tel) {

        super(name,address);
        this.legalCode = legalCode;
        this.tel = tel;
    }

    public LegalCustomerDto() {

    }

    public String getLegalCode() {
        return legalCode;
    }

    public void setLegalCode(String legalCode) {
        this.legalCode = legalCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel=tel;
    }
}
