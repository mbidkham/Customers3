package com.example.demo.model.entity;



import javax.persistence.*;
import java.util.List;
@Entity
public class Contact {

    @Id
    @GeneratedValue
    private Integer id;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Tel> tel;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Tel> getTel() {
        return tel;
    }

    public void setTel(List<Tel> tel) {
        this.tel = tel;
    }
}
