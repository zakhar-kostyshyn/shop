package com.example.shopapplication.Repositories;

import com.example.shopapplication.Model.MobilePhone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MobilePhoneRepository extends CrudRepository<MobilePhone, Long> {

    Optional<MobilePhone> findMobileByMobileIdentifierAndBrand(String mobileIdentifier, String Brand);

    MobilePhone findByMobileIdentifier(String mobileIdentifier);

    Page<MobilePhone> findAll(Pageable page);

    Page<MobilePhone> findByModelIn(List<String> list, Pageable pageable);

    //Page<MobilePhone> findByBrandIn(List<String> list, Pageable pageable);

    List<MobilePhone> findByBrandIn(List<String> list);

    @Query("select m from MobilePhone m WHERE m.price <= ?1")
    List<MobilePhone> findByPriceRange(Double maxPrice);

    @Query("select b.brand from MobilePhone b")
    List<String> findBrands();

}
