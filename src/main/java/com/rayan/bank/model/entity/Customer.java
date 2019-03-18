package com.rayan.bank.model.entity;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@MappedSuperclass
public  class Customer {

    @Id
    @GeneratedValue
    private Integer id;
    @NotNull(message = " اسم وارد کنید :")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SavingAccount> savingAccounts;

    public List<SavingAccount> getSavingAccounts() {
        return savingAccounts;
    }

    public void setSavingAccounts(List<SavingAccount> savingAccounts) {
        this.savingAccounts = savingAccounts;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
