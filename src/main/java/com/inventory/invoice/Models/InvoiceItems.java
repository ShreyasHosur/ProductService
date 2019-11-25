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
@Table(name = "invoice_items")
public class InvoiceItems {

    @Id
    @Column(name = "invoice_item_id")
    private String invoiceItemId;

    @Column(name = "invoice_no")
    private String invoiceNumber;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "quantity")
    private int quantity;

}
