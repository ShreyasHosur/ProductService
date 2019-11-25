package com.inventory.invoice.Models.Repositories;

import com.inventory.invoice.Models.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoices,Integer> {
}
