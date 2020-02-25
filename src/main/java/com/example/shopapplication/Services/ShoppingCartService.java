package com.example.shopapplication.Services;


import com.example.shopapplication.Exceptions.MobilePhoneIdException;
import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Model.ShoppingCart;
import com.example.shopapplication.Model.User;
import com.example.shopapplication.Repositories.MobilePhoneRepository;
import com.example.shopapplication.Repositories.ShoppingCartRepository;
import com.example.shopapplication.Repositories.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;


import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailService emailService;

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

    public void applyOrder(String username) throws Exception{

        Optional<User> user = userRepository.findByUsername(username);

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true);


        helper.setFrom("phoneshop.company@gmail.com");
        helper.setTo(user.get().getEmail());
        System.out.println(emailService.generateResponseToOrder(user.get()));
        helper.setText(emailService.generateResponseToOrder(user.get()));
        helper.setSubject("Order (Phone Shop)");

        final byte[] data = emailService.generatePdfBill();

        helper.addAttachment("bill.pdf", new ByteArrayResource(data));

        //message.setContent(multipart);

        javaMailSender.send(message);

    }
}
