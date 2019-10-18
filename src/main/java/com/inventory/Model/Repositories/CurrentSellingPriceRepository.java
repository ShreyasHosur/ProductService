package com.inventory.Model.Repositories;

import com.inventory.Model.CurrentSellingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentSellingPriceRepository extends JpaRepository<CurrentSellingPrice, Integer> {

    @Modifying
    @Query(value = "update current_selling_price p set current_sp = ?1 where p.product_id=?2",nativeQuery = true)
    void update(int currentSP, int productId);
}


