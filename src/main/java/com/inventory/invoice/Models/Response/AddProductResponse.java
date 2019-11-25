package com.inventory.invoice.Models.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductResponse {
    private int currentSP;
    private ResponseCode responseCode;

}
