package com.example.shopapplication.Web;


import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Payload.Request.FilterRequest;
import com.example.shopapplication.Payload.Request.MobileRequest;
import com.example.shopapplication.Services.MapValidationErrorService;
import com.example.shopapplication.Services.MobilePhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/api/mobilePhone")
@CrossOrigin("*")
public class MobileController {

    @Autowired
    private MobilePhoneService mobilePhoneService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping(value = "/createItem")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewMobile(@Valid @RequestBody MobilePhone mobilePhone, BindingResult bindingResult){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);

        if(errorMap != null) return errorMap;

        MobilePhone mobilePhone1 = mobilePhoneService.saveOrUpdateMobile(mobilePhone);
        return new ResponseEntity<MobilePhone>(mobilePhone1, HttpStatus.CREATED);

    }

    @GetMapping("/phone/{mobilePhoneId}")
    public ResponseEntity<?> getMobileById(@PathVariable String mobilePhoneId){

        MobilePhone mobilePhone = mobilePhoneService.findMobileByIdentifier(mobilePhoneId);

        return new ResponseEntity<MobilePhone>(mobilePhone, HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Page<MobilePhone> getAllMobilePhonePaging(@PageableDefault(sort = {"id"}, size = 12, direction = Sort.Direction.DESC) Pageable page){
        return mobilePhoneService.findAllMobilePhonesPaging(page);
    }

    @GetMapping("/allPhone")
    @PreAuthorize("hasRole('USER')")
    public Iterable<MobilePhone> getAllMobilePhone(){
        return mobilePhoneService.findAllMobilePhones();
    }

    @DeleteMapping("/{mobileIdentifier}")
    public ResponseEntity<?> deleteMobilePhone(@PathVariable String mobileIdentifier){

        mobilePhoneService.deleteMobilePhoneByIdentifier(mobileIdentifier);

        return new ResponseEntity<String>("Mobile with id: " + mobileIdentifier + " was deleted", HttpStatus.OK);
    }

    @GetMapping("/filters")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getFilters(){
        return new ResponseEntity<Map<String, Set<String>>>(mobilePhoneService.getFilters(), HttpStatus.OK);
    }

    @PostMapping("/filterByBrand")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> filterByBrand(@RequestBody FilterRequest filterRequest, @PageableDefault(sort = {"id"}, page=0,size = 6, direction = Sort.Direction.DESC) Pageable page){
        return new ResponseEntity<Page<MobilePhone>>(mobilePhoneService.filterByBrand(filterRequest,page),HttpStatus.OK);
    }
}
