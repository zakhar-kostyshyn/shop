package com.example.shopapplication.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingCart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "filed 'shoppingCartIdentifier' must be filled")
    private String shoppingCartIdentifier;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "carts_mobiles",
            joinColumns = @JoinColumn(name = "shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "mobile_phone_id"))
    private List<MobilePhone> mobilePhoneList = new ArrayList<>();

    @OneToOne(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    public ShoppingCart() { }

    public ShoppingCart(String shoppingCartIdentifier) {
        this.shoppingCartIdentifier = shoppingCartIdentifier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MobilePhone> getMobilePhoneList() {
        return mobilePhoneList;
    }

    public void setMobilePhoneList(List<MobilePhone> mobilePhoneList) {
        this.mobilePhoneList = mobilePhoneList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setShoppingCartIdentifier(String shoppingCartIdentifier) {
        this.shoppingCartIdentifier = shoppingCartIdentifier;
    }

    public String getShoppingCartIdentifier() {
        return shoppingCartIdentifier;
    }

    @Override
    public String toString() {
        String message = "";
        double totalPrice = 0;

        message += "==============================" + "\n";

        for (MobilePhone mobilePhone : mobilePhoneList) {
            totalPrice += mobilePhone.getPrice();
            message += "Brand: " + mobilePhone.getBrand() + " Model: " + mobilePhone.getModel() + " Price: " +  mobilePhone.getPrice() + "\n";
        }

        message += "==============================" + "\n";

        return message + "Total Price : " + Double.toString(totalPrice);
    }


}
