package com.example.shopapplication.Services;

import com.example.shopapplication.Exceptions.MobilePhoneIdException;
import com.example.shopapplication.Model.Chat;
import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Payload.Request.FilterRequest;
import com.example.shopapplication.Payload.Request.MobileRequest;
import com.example.shopapplication.Repositories.MobilePhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MobilePhoneService {

    @Autowired
    private MobilePhoneRepository mobilePhoneRepository;

    public MobilePhone saveOrUpdateMobile(MobilePhone mobilePhone){
        try {

            // Create Chat when creating Phone if it is't
            if (mobilePhone.getChat() == null)
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

    public Page<MobilePhone> findAllMobilePhonesPaging(Pageable pageable)
    {
        /*
        List<String> filters = new ArrayList<>();
        filters.add("s10+");
        filters.add("S20");

        Page<MobilePhone> list = mobilePhoneRepository.findByModelIn(filters, pageable);

        for(MobilePhone phone : list){
            System.out.println("1" + phone.toString());
        }


        return null;

        */
        return mobilePhoneRepository.findAll(pageable);
    }

    public Iterable<MobilePhone> findAllMobilePhones(){
        return mobilePhoneRepository.findAll();
    }

    public void deleteMobilePhoneByIdentifier(String identifier){

        MobilePhone mobilePhone = mobilePhoneRepository.findByMobileIdentifier(identifier);

        if(mobilePhone == null) throw new MobilePhoneIdException("Cannot Mobile with Id '" + identifier + "' .This mobile doesn't exist");

        mobilePhoneRepository.delete(mobilePhone);
    }

    public Map<String, Set<String>> getFilters(){
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("brands", mobilePhoneRepository.findBrands().stream().collect(Collectors.toSet()));
        return filters;
    }

    public Page<MobilePhone> filterByBrand(FilterRequest filterRequest, Pageable pageable){

        List<MobilePhone> filteredByBrand = mobilePhoneRepository.findByBrandIn(filterRequest.getBrands());
        List<MobilePhone> filteredByPrice = mobilePhoneRepository.findByPriceRange(Double.parseDouble(filterRequest.getPriceRange()));

        List<MobilePhone> listIntersection = intersection(filteredByBrand,filteredByPrice);

        for(MobilePhone m : filteredByPrice){
            System.out.println(m.toString());
        }

        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize() > listIntersection.size() ? listIntersection.size() : (start + pageable.getPageSize()));

        Page<MobilePhone> pages = new PageImpl<MobilePhone>(listIntersection.subList((int)start,(int)end), pageable, listIntersection.size());

        return pages;
    }

    public List<MobilePhone> intersection(List<MobilePhone> list1, List<MobilePhone> list2){

        if(list1.isEmpty()) return list2;

        List<MobilePhone> list = new ArrayList<>();

        for(MobilePhone m : list1){
            if(list2.contains(m)){
                list.add(m);
            }
        }

        return list;
    }
}
