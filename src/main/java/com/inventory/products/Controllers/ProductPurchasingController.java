package com.inventory.products.Controllers;

import com.inventory.products.Model.Request.ProductPurchaseRequest;
import com.inventory.products.Service.ProductsPurchasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductPurchasingController {

    @Autowired
    private ProductsPurchasingService productsPurchasingService;

    @PostMapping("add-Product")
    public ResponseEntity addProduct(ProductPurchaseRequest productPurchaseRequest){
        productsPurchasingService.addProduct(productPurchaseRequest);
         return ResponseEntity.ok().build();
    }
}
