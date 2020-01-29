package com.example.shopapplication.Services;

import com.example.shopapplication.Model.Mobile;
import com.example.shopapplication.Repositories.MobileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MobileService {

    @Autowired
    private MobileRepository mobileRepository;

    public Mobile saveOrUpdateMobile(Mobile mobile){
        return mobileRepository.save(mobile);
    }

    public Optional<Mobile> getMobile(String identifier, String brand){
        return mobileRepository.findMobileByMobileIdentifierAndBrand(identifier, brand);
    }

}
