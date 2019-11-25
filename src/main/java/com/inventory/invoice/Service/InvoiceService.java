package com.inventory.invoice.Service;

import com.inventory.invoice.Models.Customer;
import com.inventory.invoice.Models.Enums.OfferCodes;
import com.inventory.invoice.Models.Enums.PaymentStatus;
import com.inventory.invoice.Models.InvoiceItems;
import com.inventory.invoice.Models.InvoiceNumberGeneration;
import com.inventory.invoice.Models.Invoices;
import com.inventory.invoice.Models.Repositories.CustomerRepository;
import com.inventory.invoice.Models.Repositories.InvoiceItemsRepository;
import com.inventory.invoice.Models.Repositories.InvoiceNumberGenerationRepository;
import com.inventory.invoice.Models.Repositories.InvoicesRepository;
import com.inventory.invoice.Models.Request.CustomerResponse;
import com.inventory.invoice.Models.Request.InvoiceListOfProducts;
import com.inventory.invoice.Models.Request.InvoiceSellingProductInfo;
import com.inventory.invoice.Models.Response.AddProductResponse;
import com.inventory.invoice.Models.Response.CustomerDetails;
import com.inventory.products.Model.Request.ProductSellRequest;
import com.inventory.products.Service.ProductsSellingService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InvoiceService {

    private InvoiceNumberGenerationRepository invoiceNumberGenerationRepository;
    private ProductsSellingService productsSellingService;
    private InvoiceItemsRepository invoiceItemsRepository;
    private InvoicesRepository invoicesRepository;
    private CustomerRepository customerRepository;

    @Autowired
    InvoiceService(InvoiceNumberGenerationRepository invoiceNumberGenerationRepository,
                   ProductsSellingService productsSellingService,
                   InvoiceItemsRepository invoiceItemsRepository,
                   InvoicesRepository invoicesRepository,
                   CustomerRepository customerRepository) {
        this.invoiceNumberGenerationRepository = invoiceNumberGenerationRepository;
        this.productsSellingService = productsSellingService;
        this.invoiceItemsRepository = invoiceItemsRepository;
        this.invoicesRepository = invoicesRepository;
        this.customerRepository = customerRepository;
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

    public CustomerDetails isCustomerAlreadyPresent(String mobileNumber){
        Optional<Customer> Customer = customerRepository.findById(mobileNumber);
        if(Customer.isPresent()){
            return CustomerDetails.builder().firstName(Customer.get().getFirstName())
                    .lastName(Customer.get().getLastName())
                    .offerCodesList(Customer.get().getOfferId())
                    .build();
        } else {
            return  CustomerDetails.builder().build();
        }
    }

    public AddProductResponse OnAddingProduct(ProductSellRequest productSellRequest){
        return productsSellingService.addProductInInvoice(productSellRequest);
    }

    @Transactional
    public void processInvoiceRequest(InvoiceListOfProducts invoiceListOfProducts){
        invoiceListOfProducts.getInvoiceSellingProductInfos()
                .forEach(productInfo -> {
                    productsSellingService.SellProduct(productInfo);
                    this.InsertintoInvoiceItems(productInfo);
                });
        InsertIntoInvoice(invoiceListOfProducts);
        InsertintoCustomerTable(invoiceListOfProducts);
    }

    private void InsertIntoInvoice(InvoiceListOfProducts invoiceListOfProducts){
        invoicesRepository.save(Invoices.builder()
                .invoiceNumber(invoiceListOfProducts.getInvoiceNumber())
                .totalCost(invoiceListOfProducts.getTotalCost())
                .customerId(invoiceListOfProducts.getCustomerDetails().getMobileNumber())
                .paymentStatus(PaymentStatus.PENDING)
                .dateIssued(LocalDateTime.now())
                .build());
    }

    private void InsertintoInvoiceItems(InvoiceSellingProductInfo invoiceSellingProductInfo){
        invoiceItemsRepository.save(InvoiceItems.builder()
                .invoiceItemId(invoiceSellingProductInfo.getInvoiceItemId())
                .invoiceNumber(invoiceSellingProductInfo.getInvoiceNumber())
                .productId(invoiceSellingProductInfo.getProductId())
                .quantity(invoiceSellingProductInfo.getQuantities())
                .build());
    }

    private void InsertintoCustomerTable(InvoiceListOfProducts invoiceListOfProducts){
        CustomerResponse customerResponse = invoiceListOfProducts.getCustomerDetails();
        Optional<Customer> customer = customerRepository.findById(customerResponse.getMobileNumber());
        if(customer.isPresent()){
            List<OfferCodes> offerCodes = new ArrayList<>();
            if(!customerResponse.getOfferCodeApplied().toString().isEmpty()){
                OfferCodes offerCodeApplied = customerResponse.getOfferCodeApplied();
                offerCodes = customer.get().getOfferId();
                offerCodes.remove(offerCodeApplied);
            }
            customerRepository.updateCustomerRepository(customer.get().getMobileNumber(),offerCodes);

        }else {
            customerRepository.save(Customer.builder()
                    .mobileNumber(customerResponse.getMobileNumber())
                    .firstName(customerResponse.getFirstName())
                    .lastName(customerResponse.getLastName())
                    .offerId(getOfferCode(invoiceListOfProducts.getTotalCost()))
                    .build()
            );

        }
    }

    private List<OfferCodes> getOfferCode(Integer totalCost){
      if(totalCost > 1000 && totalCost < 2000){
          return Collections.singletonList(OfferCodes.TEN_PERCENT);
      }
      else if(totalCost > 2000 && totalCost < 3000){
          return Collections.singletonList(OfferCodes.TWENTY_FIVE_PERCENT);
      }
      else if(totalCost > 3000){
          return Collections.singletonList(OfferCodes.FIFTY_PERCENT);
      }
      else {
          return Collections.emptyList();
      }
    }
}
