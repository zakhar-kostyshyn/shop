package com.example.shopapplication.Repositories;

import com.example.shopapplication.Model.Mobile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MobileRepository extends CrudRepository<Mobile, Long> {
    @Override
    Iterable<Mobile> findAllById(Iterable<Long> iterable);

    Optional<Mobile> findMobileByMobileIdentifierAndBrand(String mobileIdentifier, String Brand);
}
