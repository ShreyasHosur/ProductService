package com.inventory.invoice.Models.Request;

import lombok.Data;

@Data
public class InvoiceSellingProductInfo {
    private String invoiceNumber;
    private String invoiceItemId;
    private int productId;
    private int sellingPrice;
    private int quantities;
}
