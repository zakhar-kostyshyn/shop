package com.example.shopapplication.Services;

import com.example.shopapplication.Exceptions.MobilePhoneIdException;
import com.example.shopapplication.Model.Chat;
import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Repositories.MobilePhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobilePhoneService {

    @Autowired
    private MobilePhoneRepository mobilePhoneRepository;

    public MobilePhone saveOrUpdateMobile(MobilePhone mobilePhone){
        try {

            // Create Chat when creating Phone
            mobilePhone.setChat(new Chat());

            return mobilePhoneRepository.save(mobilePhone);
        }catch (Exception e){
            throw new MobilePhoneIdException("MobilePhone ID '" + mobilePhone.getMobileIdentifier() + "' already exists");
        }
    }

    public MobilePhone findMobileByIdentifier(String mobileId){

        MobilePhone mobilePhone = mobilePhoneRepository.findByMobileIdentifier(mobileId);

        if(mobilePhone == null){
            throw new MobilePhoneIdException("MobilePhone ID '" + mobileId + "' doesn't exist");
        }

        return mobilePhone;
    }

    public Iterable<MobilePhone> findAllMobilePhone(){
        return mobilePhoneRepository.findAll();
    }

    public void deleteMobilePhoneByIdentifier(String identifier){

        MobilePhone mobilePhone = mobilePhoneRepository.findByMobileIdentifier(identifier);

        if(mobilePhone == null) throw new MobilePhoneIdException("Cannot Mobile with Id '" + identifier + "' .This mobile doesn't exist");

        mobilePhoneRepository.delete(mobilePhone);
    }
}
