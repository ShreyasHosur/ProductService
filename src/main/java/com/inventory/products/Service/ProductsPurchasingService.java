package com.inventory.products.Service;

import com.inventory.products.Model.ProductPurchased;
import com.inventory.products.Model.ProductRemaining;
import com.inventory.products.Model.Repositories.ProductsPurchasedRepository;
import com.inventory.products.Model.Repositories.ProductsRemainingRepository;
import com.inventory.products.Model.Request.ProductPurchaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class ProductsPurchasingService {

    //Insert in table ProductsPurchased
    //Update productremaining table
    private ProductsPurchasedRepository productsPurchasedRepository;
    private ProductsRemainingRepository productsRemainingRepository;

    @Autowired
    ProductsPurchasingService(ProductsPurchasedRepository productsPurchasedRepository ,
                              ProductsRemainingRepository productsRemainingRepository){
        this.productsPurchasedRepository = productsPurchasedRepository;
        this.productsRemainingRepository = productsRemainingRepository;
    }

    private void InsertProduct(ProductPurchaseRequest productPurchaseRequest){
        productsPurchasedRepository.save(ProductPurchased.builder()
                .productId(productPurchaseRequest.getProductId())
                .purchasedDate(LocalDate.now())
                .quantities(productPurchaseRequest.getQuantities())
                .purchasedPrice(productPurchaseRequest.getPricePerQuantity())
                .totalPrice(productPurchaseRequest.getQuantities() * productPurchaseRequest.getPricePerQuantity()).build()
        );
    }

    private void UpdateRemainingProducts(int productId , int quantities) {
        if(!productsRemainingRepository.existsById(productId)){
            productsRemainingRepository.save(ProductRemaining.builder().productId(productId).quantities(quantities).build());
            return;
        }
        productsRemainingRepository.updateAddQuantities(quantities ,productId);
    }

    @Transactional
    public void addProduct(ProductPurchaseRequest productPurchaseRequest){
        InsertProduct(productPurchaseRequest);
        UpdateRemainingProducts(productPurchaseRequest.getProductId(),productPurchaseRequest.getQuantities());
    }
}
