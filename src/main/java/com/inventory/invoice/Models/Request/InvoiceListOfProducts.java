package com.inventory.invoice.Models.Request;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceListOfProducts {

    private List<InvoiceSellingProductInfo> invoiceSellingProductInfos;
    private String invoiceNumber;
    private int totalCost;
    private CustomerResponse customerDetails;
}
