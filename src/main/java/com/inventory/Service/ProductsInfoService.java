package com.inventory.Service;

import com.inventory.Model.CurrentSellingPrice;
import com.inventory.Model.ProductRemaining;
import com.inventory.Model.ProductStoreData;
import com.inventory.Model.ProductsInfo;
import com.inventory.Model.Repositories.CurrentSellingPriceRepository;
import com.inventory.Model.Repositories.ProductsInfoRepository;
import com.inventory.Model.Repositories.ProductsRemainingRepository;
import com.inventory.Model.Request.ProductInfoRequest;
import com.inventory.Model.Response.PageDataResponse;
import com.inventory.Model.Response.ProductIdInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsInfoService {

    //Update ProductInfo
    private ProductsInfoRepository productsInfoRepository;
    private CurrentSellingPriceRepository currentSellingPriceRepository;
    private ProductsRemainingRepository productsRemainingRepository;

    @Autowired
    ProductsInfoService(ProductsInfoRepository productsInfoRepository,
                        CurrentSellingPriceRepository currentSellingPriceRepository,
                        ProductsRemainingRepository productsRemainingRepository){
        this.productsInfoRepository = productsInfoRepository;
        this.currentSellingPriceRepository = currentSellingPriceRepository;
        this.productsRemainingRepository = productsRemainingRepository;
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
        int remaining = getRemaining(productId);
        return ProductIdInfoResponse
                .builder()
                .productId(productsInfo.getProductId())
                .productCategory(productsInfo.getProductCategory())
                .productName((productsInfo.getProductName()))
                .available(remaining)
                .currentSP(currentSP)
                .build();
    }

    public PageDataResponse getPageData(Pageable pageable){
        Page<ProductStoreData> productStoreData = productsInfoRepository.getproductStoreData(pageable);
        return PageDataResponse.builder().productStoreData(productStoreData.getContent())
                .totalPages(productStoreData.getTotalPages())
                .size(productStoreData.getSize()).build();
    }

    private int getCurrentSP(int productId){
        Optional<CurrentSellingPrice> currentSellingPrice = currentSellingPriceRepository.findById(productId);
        return currentSellingPrice.map(CurrentSellingPrice::getCurrentSP).orElse(0);
    }

    private int getRemaining(int productId){
        return productsRemainingRepository.findById(productId).map(ProductRemaining::getQuantities).orElse(0);
    }

    @Transactional
    public void submitdata(ProductIdInfoResponse productIdInfoResponse){
        List<String> changesDone = productIdInfoResponse.getChange();
        if(changesDone.contains("productId") || changesDone.contains("productCategory") || changesDone.contains("productName")) {
            insertProductInfo(productIdInfoResponse);
            insertAvaialability(productIdInfoResponse);
        }
        if(changesDone.contains(("currentSP"))){
            insertCurrentSelllingPrice(productIdInfoResponse);
        }
    }

    private void insertProductInfo(ProductIdInfoResponse productIdInfoResponse){
        UpdateProduct(ProductInfoRequest.builder().
                 productId(productIdInfoResponse.getProductId())
                .productCategory(productIdInfoResponse.getProductCategory())
                .productName(productIdInfoResponse.getProductName())
                .build()
        );

    }

    private void insertCurrentSelllingPrice(ProductIdInfoResponse productIdInfoResponse){
        Optional<CurrentSellingPrice> csr = currentSellingPriceRepository.findById(productIdInfoResponse.getProductId());
        if(csr.isPresent()){
            currentSellingPriceRepository.update(productIdInfoResponse.getCurrentSP(),productIdInfoResponse.getProductId());
            return;
        }
        currentSellingPriceRepository.save(CurrentSellingPrice.builder()
                .productId(productIdInfoResponse.getProductId())
                .currentSP(productIdInfoResponse.getCurrentSP())
                .build()
        );
    }

    private void insertAvaialability(ProductIdInfoResponse productIdInfoResponse){

    }

}
