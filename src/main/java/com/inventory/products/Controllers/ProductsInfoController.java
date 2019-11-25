package com.inventory.products.Controllers;

import com.inventory.products.Model.Repositories.ProductsInfoRepository;
import com.inventory.products.Model.Request.ProductInfoRequest;
import com.inventory.products.Model.Response.PageDataResponse;
import com.inventory.products.Model.Response.ProductIdInfoResponse;
import com.inventory.products.Service.ProductsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductsInfoController {

    @Autowired
    private ProductsInfoService productsInfoService;

    @Autowired
    private ProductsInfoRepository productsInfoRepository;

    public ProductsInfoController(ProductsInfoService productsInfoService, ProductsInfoRepository productsInfoRepository) {
        this.productsInfoService = productsInfoService;
        this.productsInfoRepository = productsInfoRepository;
    }

    @PostMapping("update-product")
    public ResponseEntity updateProduct(@RequestBody ProductInfoRequest productInfoRequest){
        productsInfoService.UpdateProduct(productInfoRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-product")
    public PageDataResponse getproducts(@RequestParam("pageNum") int pageNum , @RequestParam("pageSize") int pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
          return productsInfoService.getPageData(pageable);
    }

    @GetMapping("get-productid-info")
    public ProductIdInfoResponse getproductinfo(@RequestParam("id") int id){
        return productsInfoService.getproductInfoforId(id);
    }

    @PostMapping("post-productid-info")
    public ResponseEntity submitproductinfo(@RequestBody ProductIdInfoResponse productIdInfoResponse ){
        productsInfoService.submitdata(productIdInfoResponse);
        return ResponseEntity.ok().build();
    }

}