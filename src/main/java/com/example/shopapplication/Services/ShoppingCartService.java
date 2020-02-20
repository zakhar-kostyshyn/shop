package com.example.shopapplication.Services;


import com.example.shopapplication.Exceptions.MobilePhoneIdException;
import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Model.ShoppingCart;
import com.example.shopapplication.Model.User;
import com.example.shopapplication.Repositories.MobilePhoneRepository;
import com.example.shopapplication.Repositories.ShoppingCartRepository;
import com.example.shopapplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MobilePhoneRepository mobilePhoneRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private MobilePhoneService mobilePhoneService;

    public Optional<ShoppingCart> saveProduct(String mobileIdentifier, String username) {

        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByShoppingCartIdentifier(username); // exception not fount maybe unreal

        MobilePhone mobilePhone = mobilePhoneService.findMobileByIdentifier(mobileIdentifier);

        if(mobilePhone == null){
            throw new MobilePhoneIdException("MobilePhone ID '" + mobileIdentifier + "' doesn't exist");
        }

        if(shoppingCart.get().getMobilePhoneList().contains(mobilePhone))
            throw new MobilePhoneIdException("MobilePhone ID '" + mobileIdentifier + "' already exist in shopping cart!");

        //shoppingCart.get().getMobilePhoneList().add(mobilePhone);

        shoppingCart.get().getMobilePhoneList().add(mobilePhone);

        shoppingCartRepository.save(shoppingCart.get());

        return shoppingCart;
    }

    public Optional<ShoppingCart> getShoppingCart(String username){

        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByShoppingCartIdentifier(username);

        return shoppingCart;
    }

    public void deleteMobileFromCart(String mobileIdentifier, String username){

        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByShoppingCartIdentifier(username);

        MobilePhone mobilePhone = mobilePhoneService.findMobileByIdentifier(mobileIdentifier);

        shoppingCart.get().getMobilePhoneList().remove(mobilePhone);

        shoppingCartRepository.save(shoppingCart.get());
    }
}
