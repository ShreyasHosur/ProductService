package com.inventory.Service;

import com.inventory.Model.CurrentSellingPrice;
import com.inventory.Model.ProductsInfo;
import com.inventory.Model.Repositories.CurrentSellingPriceRepository;
import com.inventory.Model.Repositories.ProductsInfoRepository;
import com.inventory.Model.Request.ProductInfoRequest;
import com.inventory.Model.Response.ProductIdInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductsInfoService {

    //Update ProductInfo
    private  ProductsInfoRepository productsInfoRepository;
    private  CurrentSellingPriceRepository currentSellingPriceRepository;

    @Autowired
    ProductsInfoService(ProductsInfoRepository productsInfoRepository,
                        CurrentSellingPriceRepository currentSellingPriceRepository){
        this.productsInfoRepository = productsInfoRepository;
        this.currentSellingPriceRepository = currentSellingPriceRepository;
    }

    public void UpdateProduct(ProductInfoRequest productInfoRequest){
        if(productsInfoRepository.existsById(productInfoRequest.getProductId())){
            productsInfoRepository.deleteById(productInfoRequest.getProductId());
        }
        productsInfoRepository.save(ProductsInfo.builder()
                .productId(productInfoRequest.getProductId())
                .productName(productInfoRequest.getProductName())
                .productCategory(productInfoRequest.getProductCategory())
                .build());
    }

    public ProductIdInfoResponse getproductInfoforId(int productId){
        ProductsInfo productsInfo = productsInfoRepository.findById(productId).get();
        int currentSP = getCurrentSP(productId);
        return ProductIdInfoResponse
                .builder()
                .productId(productsInfo.getProductId())
                .productCategory(productsInfo.getProductCategory())
                .productName((productsInfo.getProductName()))
                .currentSP(currentSP)
                .build();
    }

    private int getCurrentSP(int productId){
        Optional<CurrentSellingPrice> currentSellingPrice = currentSellingPriceRepository.findById(productId);
        return currentSellingPrice.map(CurrentSellingPrice::getCurrentSP).orElse(0);
    }

    @Transactional
    public void submitdata(ProductIdInfoResponse productIdInfoResponse){
        insertProductInfo(productIdInfoResponse);
        insertCurrentSelllinPrice(productIdInfoResponse);
        insertAvaialability(productIdInfoResponse);
    }

    private void insertProductInfo(ProductIdInfoResponse productIdInfoResponse){
        ProductsInfo.builder().productId(productIdInfoResponse.getProductId())
                .productCategory(productIdInfoResponse.getProductCategory())
                .productName(productIdInfoResponse.getProductName())
                .build();
    }

    private void insertCurrentSelllinPrice(ProductIdInfoResponse productIdInfoResponse){

    }

    private void insertAvaialability(ProductIdInfoResponse productIdInfoResponse){

    }

}
