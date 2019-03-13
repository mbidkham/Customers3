package com.rayan.bank.dto;

public class RealCustomerDto extends CustomerDto {


    private String lastName;
    private String nationalCode;
    private ContactDto contactDto;


    public RealCustomerDto(String name, String lastName, String nationalCode, ContactDto contactDto, AddressDto address){

        super(name,address);
        this.lastName=lastName;
        this.nationalCode=nationalCode;
        this.contactDto = contactDto;


    }

    public RealCustomerDto() {

        }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;

    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;

    }

    public ContactDto getContactDto() {
        return contactDto;
    }

    public void setContactDto(ContactDto contactDto) {
        this.contactDto = contactDto;

    }
}
