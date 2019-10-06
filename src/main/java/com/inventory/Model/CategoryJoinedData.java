package com.inventory.Model;

import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDate;

public interface CategoryJoinedData {

    @Value("#{target.product_id}")
    Integer getId();

    @Value("#{target.product_category}")
    String getProductCategory();

    @Value("#{target.sold_time}")
    LocalDate getsolddate();

    @Value("#{target.total_sp}")
    Integer gettotalSP();


}
