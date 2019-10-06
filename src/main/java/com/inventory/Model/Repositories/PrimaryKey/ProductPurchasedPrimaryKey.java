package com.inventory.Model.Repositories.PrimaryKey;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ProductPurchasedPrimaryKey implements Serializable {

    private int productId;
    private LocalDate purchasedDate;

}
