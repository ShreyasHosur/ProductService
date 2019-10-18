package com.inventory.Model.Response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductIdInfoResponse {

    private int productId;
    private String productName;
    private String productCategory;
    private int available;
    private int currentSP;
    private List<String> change;
}
