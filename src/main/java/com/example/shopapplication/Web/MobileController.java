package com.example.shopapplication.Web;


import com.example.shopapplication.Model.Mobile;
import com.example.shopapplication.Model.WrapperRequest;
import com.example.shopapplication.Services.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api/mobile")
public class MobileController {

    @Autowired
    private MobileService mobileService;

    @PostMapping("")
    public ResponseEntity<?> addNewMobile(@Valid @RequestBody Mobile mobile, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<String>("Invalid mobile object", HttpStatus.BAD_REQUEST);
        }

        Mobile mobile1 = mobileService.saveOrUpdateMobile(mobile);
        return new ResponseEntity<Mobile>(mobile1, HttpStatus.CREATED);
    }

    // This is example for zakhar

    @PostMapping("/getMobile")
    public ResponseEntity<?> getMobile(@RequestBody WrapperRequest wrapperRequest){

        System.out.println(wrapperRequest.getMobileIdentifier());
        System.out.println(wrapperRequest.getBrand());

        Optional<Mobile> mobile = mobileService.getMobile(wrapperRequest.getMobileIdentifier(), wrapperRequest.getBrand());

        return new ResponseEntity<Optional<Mobile>>(mobile, HttpStatus.OK);
    }
}
