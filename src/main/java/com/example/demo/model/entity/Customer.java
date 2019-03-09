package com.example.demo.model.entity;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public class Customer {

    @Id
    @GeneratedValue
    private Integer id;
    @NotNull(message = " اسم وارد کنید :")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
