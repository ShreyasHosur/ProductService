package com.inventory.invoice.Models.Request;

import com.inventory.invoice.Models.Enums.OfferCodes;
import lombok.Data;
import java.util.List;

@Data
public class CustomerResponse {

    private String mobileNumber;
    private String firstName;
    private String lastName;
    private OfferCodes offerCodeApplied;
}
