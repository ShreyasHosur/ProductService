package com.inventory.Controllers;

import com.inventory.Model.ProductStoreData;
import com.inventory.Model.Repositories.ProductsInfoRepository;
import com.inventory.Model.Request.ProductInfoRequest;
import com.inventory.Model.Response.PageDataResponse;
import com.inventory.Model.Response.ProductIdInfoResponse;
import com.inventory.Service.ProductsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:3000")
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