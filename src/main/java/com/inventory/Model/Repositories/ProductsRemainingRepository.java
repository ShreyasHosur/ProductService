package com.inventory.Model.Repositories;

import com.inventory.Model.ProductRemaining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface ProductsRemainingRepository extends JpaRepository<ProductRemaining,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update product_remaining p set quantities = quantities + ?1 where p.product_id = ?2" , nativeQuery = true)
    void updateAddQuantities(int quantity , int productId );

    @Transactional
    @Modifying
    @Query(value = "update product_remaining p set quantities = quantities - ?1 where p.product_id = ?2" , nativeQuery = true)
    void updateremoveQuantities(int quantity , int productId );
}
