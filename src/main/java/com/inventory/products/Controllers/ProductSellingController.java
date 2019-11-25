package com.inventory.products.Controllers;

import com.inventory.products.Model.Request.ProductSellRequest;
import com.inventory.products.Service.ProductsSellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductSellingController {

    @Autowired
    private ProductsSellingService productsSellingService;

//    @PostMapping("sell-product")
//    public ResponseEntity sellProduct(ProductSellRequest productSellRequest){
//        try {
//            productsSellingService.SellProduct(productSellRequest);
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//        return ResponseEntity.ok().build();
//    }
}
