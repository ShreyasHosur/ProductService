package com.inventory.invoice.Models.Repositories;

import com.inventory.invoice.Models.Customer;
import com.inventory.invoice.Models.Enums.OfferCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {

    @Transactional
    @Modifying
    @Query(value = "update customer p set offer_id = ?2 where p.mobile_num = ?1" , nativeQuery = true)
    void updateCustomerRepository(String mobileNumber, List<OfferCodes> offerCodes);
}
