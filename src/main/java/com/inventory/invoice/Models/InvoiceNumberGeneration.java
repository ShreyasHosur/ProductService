package com.inventory.invoice.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoice_number_generation")
public class InvoiceNumberGeneration {

    @Id
    @Column(name = "inv_id")
    private String invoiceId;

    @Column(name = "current_number")
    private int currentNumber;
}
