package com.example.shopapplication.Repositories;


import com.example.shopapplication.Model.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByShoppingCartIdentifier(String cartIdentifier);


}
