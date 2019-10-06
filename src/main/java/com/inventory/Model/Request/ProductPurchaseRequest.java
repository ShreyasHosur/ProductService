package com.inventory.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPurchaseRequest {

    private int productId;
    private int quantities;
    private int pricePerQuantity;
}
