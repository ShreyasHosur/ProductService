package com.inventory.products.Model.Response;

import com.inventory.products.Model.ProductStoreData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageDataResponse {
   private List<ProductStoreData> productStoreData;
   private int totalPages;
   private int size;
}
