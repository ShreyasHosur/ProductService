package com.inventory.products.Model;

import org.springframework.beans.factory.annotation.Value;

public interface ProductStoreData {

    @Value("#{target.product_id}")
    Integer getId();

    @Value("#{target.product_category}")
    String getproductCategory();

    @Value("#{target.product_name}")
    String getproductName();

    @Value("#{target.quantities}")
    Integer getquantities();

    @Value("#{target.current_sp}")
    Integer getcurrentSP();

}
