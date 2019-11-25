package com.inventory.products.Service;

import com.inventory.invoice.Models.Request.InvoiceSellingProductInfo;
import com.inventory.invoice.Models.Response.AddProductResponse;
import com.inventory.invoice.Models.Response.ResponseCode;
import com.inventory.products.Model.CurrentSellingPrice;
import com.inventory.products.Model.ProductRemaining;
import com.inventory.products.Model.ProductSold;
import com.inventory.products.Model.Repositories.CurrentSellingPriceRepository;
import com.inventory.products.Model.Repositories.ProductSoldRepository;
import com.inventory.products.Model.Repositories.ProductsRemainingRepository;
import com.inventory.products.Model.Request.ProductSellRequest;
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
        this.productSoldRepository = productSoldRepository;
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

    private void InsertSoldProduct(InvoiceSellingProductInfo invoiceSellingProductInfo, int currentSP) {
        productSoldRepository.save(ProductSold.builder()
                .productId(invoiceSellingProductInfo.getProductId())
                .soldTime(LocalDateTime.now())
                .quantities(invoiceSellingProductInfo.getQuantities())
                .soldPrice(currentSP)
                .totalSP(invoiceSellingProductInfo.getQuantities() * currentSP)
                .build());
    }

    private void MinusRemainingProducts(InvoiceSellingProductInfo invoiceSellingProductInfo) {
        productsRemainingRepository.updateremoveQuantities(invoiceSellingProductInfo.getQuantities(),
                invoiceSellingProductInfo.getProductId());
    }

    @Transactional
    public void SellProduct(InvoiceSellingProductInfo invoiceSellingProductInfo){
        InsertSoldProduct(invoiceSellingProductInfo, invoiceSellingProductInfo.getSellingPrice());
        MinusRemainingProducts(invoiceSellingProductInfo);
    }

    public AddProductResponse addProductInInvoice(ProductSellRequest productSellRequest) {
        Optional<CurrentSellingPrice> currentSP;
        if (isProductinRequiredQuantity(productSellRequest)) {
            currentSP = getCurrentSP(productSellRequest.getProductId());
            if (currentSP.isPresent()) {
                return AddProductResponse.builder().currentSP(currentSP.get().getCurrentSP())
                        .responseCode(ResponseCode.SUCCESS)
                        .build();
            } else {
                return AddProductResponse.builder().currentSP(0)
                        .responseCode(ResponseCode.NO_SELLING_PRICE)
                        .build();
            }
        } else {
            return AddProductResponse.builder().currentSP(0)
                    .responseCode(ResponseCode.NOT_IN_REQUIRED_QUANTITIES)
                    .build();
        }
    }

}
