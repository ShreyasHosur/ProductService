package com.inventory.invoice.Models.Repositories;

import com.inventory.invoice.Models.InvoiceNumberGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface InvoiceNumberGenerationRepository  extends JpaRepository<InvoiceNumberGeneration,String> {

    @Transactional
    @Modifying
    @Query(value = "update invoice_number_generation p set current_number = ?2 where p.inv_id = ?1" , nativeQuery = true)
    void updateCurrentNumber(String InvoiceId , int currentNumber);
}
