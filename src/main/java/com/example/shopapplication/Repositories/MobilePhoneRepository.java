package com.example.shopapplication.Repositories;

import com.example.shopapplication.Model.MobilePhone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MobilePhoneRepository extends CrudRepository<MobilePhone, Long> {

    Optional<MobilePhone> findMobileByMobileIdentifierAndBrand(String mobileIdentifier, String Brand);

    MobilePhone findByMobileIdentifier(String mobileIdentifier);

}
