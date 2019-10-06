package com.inventory.Model.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductIdInfoResponse {

    private int productId;
    private String productName;
    private String productCategory;
    private int currentSP;
}
