package com.inventory.Service;

import com.inventory.Model.CurrentSellingPrice;
import com.inventory.Model.ProductRemaining;
import com.inventory.Model.ProductSold;
import com.inventory.Model.Repositories.CurrentSellingPriceRepository;
import com.inventory.Model.Repositories.ProductSoldRepository;
import com.inventory.Model.Repositories.ProductsRemainingRepository;
import com.inventory.Model.Request.ProductSellRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductsSellingService {

    //CheckProducts are remaining
    //if yes check check the required quantities the selling Price and insert it in selling Table
    //if not throw exception product is not remaining

    private ProductsRemainingRepository productsRemainingRepository;
    private ProductSoldRepository productSoldRepository;
    private CurrentSellingPriceRepository currentSellingPriceRepository;

    @Autowired
    ProductsSellingService(ProductsRemainingRepository productsRemainingRepository,
                           ProductSoldRepository productSoldRepository,
                           CurrentSellingPriceRepository currentSellingPriceRepository) throws Exception {
        this.productSoldRepository =productSoldRepository;
        this.productsRemainingRepository = productsRemainingRepository;
        this.currentSellingPriceRepository = currentSellingPriceRepository;
    }

    private boolean isProductinRequiredQuantity(ProductSellRequest productSellRequest) {
        Optional<ProductRemaining> pr = productsRemainingRepository.findById(productSellRequest.getProductId());
        return pr.filter(productRemaining -> productRemaining.getQuantities() >= productSellRequest.getQuantities()).isPresent();
    }

    private Optional<CurrentSellingPrice> getCurrentSP(int productId) {
        return currentSellingPriceRepository.findById(productId);
    }

    private void InsertSoldProduct(ProductSellRequest productSellRequest , int currentSP){
        productSoldRepository.save(ProductSold.builder()
        .productId(productSellRequest.getProductId())
        .soldTime(LocalDateTime.now())
        .quantities(productSellRequest.getQuantities())
        .soldPrice(currentSP)
        .totalSP(productSellRequest.getQuantities() * currentSP)
        .build());
    }

    private void MinusRemainingProducts(ProductSellRequest productSellRequest){
        productsRemainingRepository.updateremoveQuantities(productSellRequest.getQuantities() ,
                productSellRequest.getProductId());
    }

    @Transactional
    public void SellProduct(ProductSellRequest productSellRequest) throws Exception {
        Optional<CurrentSellingPrice> currentSP;
        if(isProductinRequiredQuantity(productSellRequest)) {
            currentSP = getCurrentSP(productSellRequest.getProductId());
            if(currentSP.isPresent()){
                InsertSoldProduct(productSellRequest,currentSP.get().getCurrentSP());
                MinusRemainingProducts(productSellRequest);
            }else{
                throw new Exception("Current Selling Price Does Not exist");
            }
        }
        else{
            throw new Exception("Products are not in Required Quantity");
        }


    }
}
