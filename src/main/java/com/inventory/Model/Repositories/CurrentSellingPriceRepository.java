package com.inventory.Model.Repositories;

import com.inventory.Model.CurrentSellingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentSellingPriceRepository extends JpaRepository<CurrentSellingPrice, Integer> {
}


