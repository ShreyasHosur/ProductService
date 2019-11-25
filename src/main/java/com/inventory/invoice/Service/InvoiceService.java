package com.inventory.invoice.Service;

import com.inventory.invoice.Models.Enums.PaymentStatus;
import com.inventory.invoice.Models.InvoiceItems;
import com.inventory.invoice.Models.InvoiceNumberGeneration;
import com.inventory.invoice.Models.Invoices;
import com.inventory.invoice.Models.Repositories.InvoiceItemsRepository;
import com.inventory.invoice.Models.Repositories.InvoiceNumberGenerationRepository;
import com.inventory.invoice.Models.Repositories.InvoicesRepository;
import com.inventory.invoice.Models.Request.InvoiceListOfProducts;
import com.inventory.invoice.Models.Request.InvoiceSellingProductInfo;
import com.inventory.invoice.Models.Response.AddProductResponse;
import com.inventory.products.Model.Request.ProductSellRequest;
import com.inventory.products.Service.ProductsSellingService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class InvoiceService {

    private InvoiceNumberGenerationRepository invoiceNumberGenerationRepository;
    private ProductsSellingService productsSellingService;
    private InvoiceItemsRepository invoiceItemsRepository;
    private InvoicesRepository invoicesRepository;

    @Autowired
    InvoiceService(InvoiceNumberGenerationRepository invoiceNumberGenerationRepository,
                   ProductsSellingService productsSellingService,
                   InvoiceItemsRepository invoiceItemsRepository) {
        this.invoiceNumberGenerationRepository = invoiceNumberGenerationRepository;
        this.productsSellingService = productsSellingService;
        this.invoiceItemsRepository = invoiceItemsRepository;
    }

    public Integer getInvoiceNumber(){
        Optional<InvoiceNumberGeneration> invoiceNumber = invoiceNumberGenerationRepository.findById("INV");
        int currentNumber;
        if(invoiceNumber.isPresent()){
            currentNumber = invoiceNumber.get().getCurrentNumber();
            invoiceNumberGenerationRepository.updateCurrentNumber("INV" , currentNumber+1);
        }
        else {
            currentNumber = 0;
            invoiceNumberGenerationRepository.save(InvoiceNumberGeneration.builder().invoiceId("INV").currentNumber(0).build());
        }
        return currentNumber;
    }

    public AddProductResponse OnAddingProduct(ProductSellRequest productSellRequest){
        return productsSellingService.addProductInInvoice(productSellRequest);
    }

    @Transactional
    public void processInvoicerRequest(InvoiceListOfProducts invoiceListOfProducts){
        invoiceListOfProducts.getInvoiceSellingProductInfos()
                .forEach(productInfo -> {
                    productsSellingService.SellProduct(productInfo);
                    this.InsertinInvoiceItems(productInfo);
                });
        InsertIntoInvoice(invoiceListOfProducts);
    }

    private void InsertIntoInvoice(InvoiceListOfProducts invoiceListOfProducts){
        invoicesRepository.save(Invoices.builder()
                .invoiceNumber(invoiceListOfProducts.getInvoiceNumber())
                .totalCost(invoiceListOfProducts.getTotalCost())
                .customerId(invoiceListOfProducts.getCustomerId())
                .paymentStatus(PaymentStatus.FAILED)
                .dateIssued(LocalDateTime.now())
                .build());
    }

    private void InsertinInvoiceItems(InvoiceSellingProductInfo invoiceSellingProductInfo){
        invoiceItemsRepository.save(InvoiceItems.builder()
                .invoiceItemId(invoiceSellingProductInfo.getInvoiceItemId())
                .invoiceNumber(invoiceSellingProductInfo.getInvoiceNumber())
                .productId(invoiceSellingProductInfo.getProductId())
                .quantity(invoiceSellingProductInfo.getQuantities())
                .build());
    }
}
