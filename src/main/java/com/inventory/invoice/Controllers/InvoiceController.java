package com.inventory.invoice.Controllers;

import com.inventory.invoice.Models.Request.InvoiceListOfProducts;
import com.inventory.invoice.Models.Response.AddProductResponse;
import com.inventory.invoice.Models.Response.CustomerDetails;
import com.inventory.invoice.Service.InvoiceService;
import com.inventory.products.Model.Request.ProductSellRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class InvoiceController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/get-current-invoice-number")
    public Integer getInvoiceNumber(){
        return invoiceService.getInvoiceNumber();
    }

    @PostMapping("customer-exist")
    public ResponseEntity<CustomerDetails> isCustomerExist(@RequestBody String mobileNumber){
        return ResponseEntity.ok(invoiceService.isCustomerAlreadyPresent(mobileNumber));
    }

    @PostMapping("add-product")
    public ResponseEntity<AddProductResponse> addProduct(@RequestBody ProductSellRequest productSellRequest){
        return ResponseEntity.ok(invoiceService.OnAddingProduct(productSellRequest));
    }

    @PostMapping("sell-all-products")
    public ResponseEntity sellallProducts(InvoiceListOfProducts invoiceListOfProducts){
        invoiceService.processInvoiceRequest(invoiceListOfProducts);
        return ResponseEntity.ok().build();
    }
}
