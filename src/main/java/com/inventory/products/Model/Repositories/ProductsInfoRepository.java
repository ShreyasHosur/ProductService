package com.inventory.products.Model.Repositories;

import com.inventory.products.Model.CategoryJoinedData;
import com.inventory.products.Model.ProductStoreData;
import com.inventory.products.Model.ProductsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductsInfoRepository extends JpaRepository<ProductsInfo, Integer> {

    @Query(value = "select p.product_id , p.product_category , DATE(q.sold_time) as sold_time, q.total_sp from product_details p INNER JOIN product_sold q ON p.product_id = q.product_id",nativeQuery = true)
    List<CategoryJoinedData> categoryData();

    @Query(value = "Select * from product_details p" , nativeQuery = true)
    Page<ProductsInfo> findProductInfoBypages(Pageable pageable);

    @Query(value = "select p.product_id , p.product_category , p.product_name , coalesce(q.quantities,'0') as quantities, coalesce(r.current_sp,'-1') as current_sp from product_details p full outer join product_remaining q on q.product_id = p.product_id full outer Join current_selling_price r on r.product_id = q.product_id",
    nativeQuery = true)
    Page<ProductStoreData> getproductStoreData(Pageable pageable);

}
