package com.inventory.invoice.Controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class InvoiceController {

    @GetMapping("/get-current-invoice-number")
    public String getInvoiceNumber(){
        return "";
    }
}
