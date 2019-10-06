package com.inventory.Model.Repositories;

import com.inventory.Model.ProductSold;
import com.inventory.Model.Repositories.PrimaryKey.ProductSoldPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductSoldRepository extends JpaRepository<ProductSold, ProductSoldPrimaryKey> {

    @Query(value = " select * from product_sold p where DATE(p.sold_time) >= ?1 and DATE(p.sold_time) <= ?2" ,nativeQuery = true)
    List<ProductSold> productsBetweenDates(LocalDate startDate , LocalDate endDate);
}
