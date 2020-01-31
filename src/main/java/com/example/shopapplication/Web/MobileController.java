package com.example.shopapplication.Web;


import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Services.MapValidationErrorService;
import com.example.shopapplication.Services.MobilePhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/mobilePhone")
public class MobileController {

    @Autowired
    private MobilePhoneService mobilePhoneService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> addNewMobile(@Valid @RequestBody MobilePhone mobilePhone, BindingResult bindingResult){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);

        if(errorMap != null) return errorMap;

        MobilePhone mobilePhone1 = mobilePhoneService.saveOrUpdateMobile(mobilePhone);
        return new ResponseEntity<MobilePhone>(mobilePhone1, HttpStatus.CREATED);
    }

    @GetMapping("/{mobilePhoneId}")
    public ResponseEntity<?> getMobileById(@PathVariable String mobilePhoneId){

        MobilePhone mobilePhone = mobilePhoneService.findMobileByIdentifier(mobilePhoneId);

        return new ResponseEntity<MobilePhone>(mobilePhone, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<MobilePhone> getAllMobilePhone(){
        return mobilePhoneService.findAllMobilePhone();
    }

    @DeleteMapping("/{mobileIdentifier}")
    public ResponseEntity<?> deleteMobilePhone(@PathVariable String mobileIdentifier){

        mobilePhoneService.deleteMobilePhoneByIdentifier(mobileIdentifier);

        return new ResponseEntity<String>("Mobile with id: " + mobileIdentifier + " was deleted", HttpStatus.OK);
    }
}
