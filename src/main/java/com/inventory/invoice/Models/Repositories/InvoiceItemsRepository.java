package com.inventory.invoice.Models.Repositories;

import com.inventory.invoice.Models.InvoiceItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItems,String> {
}
