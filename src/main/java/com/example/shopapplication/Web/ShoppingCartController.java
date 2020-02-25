package com.example.shopapplication.Web;

import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Model.ShoppingCart;
import com.example.shopapplication.Services.MapValidationErrorService;
import com.example.shopapplication.Services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/addItem/{mobileIdentifier}")
    public ResponseEntity<?> saveItem(@PathVariable String mobileIdentifier, Principal principal){

        System.out.println(principal.getName());

        Optional<ShoppingCart> shoppingCart = shoppingCartService.saveProduct(mobileIdentifier, principal.getName());

        return new ResponseEntity<ShoppingCart>(shoppingCart.get(), HttpStatus.OK);
    }

    @GetMapping("/getCart")
    public ResponseEntity<?> getCart(Principal principal){

        System.out.println(principal.getName());

        Optional<ShoppingCart> shoppingCart = shoppingCartService.getShoppingCart(principal.getName());

        return new ResponseEntity<ShoppingCart>(shoppingCart.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{mobileIdentifier}")
    public ResponseEntity<?> deleteItemFromCart(@PathVariable String mobileIdentifier, Principal principal){

        shoppingCartService.deleteMobileFromCart(mobileIdentifier, principal.getName());

        return new ResponseEntity<String>("Mobile with id: '" + mobileIdentifier + "' was deleted", HttpStatus.OK);
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyOrder(Principal principal) throws Exception {

        shoppingCartService.applyOrder(principal.getName());

        return new ResponseEntity<String>("Your message send" , HttpStatus.OK);
    }
}
