package com.inventory.products.Model.Repositories;

import com.inventory.products.Model.ProductPurchased;
import com.inventory.products.Model.Repositories.PrimaryKey.ProductPurchasedPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductsPurchasedRepository extends JpaRepository<ProductPurchased, ProductPurchasedPrimaryKey> {

    @Query(value = "select * from product_purchased p where p.purchased_date >= ?1 and p.purchased_date <=  ?2" ,nativeQuery = true)
    List<ProductPurchased> productsBetweenDates(LocalDate startDate , LocalDate endDate);
}
