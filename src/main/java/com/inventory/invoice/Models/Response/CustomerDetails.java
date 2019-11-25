package com.inventory.invoice.Models.Response;

import com.inventory.invoice.Models.Enums.OfferCodes;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CustomerDetails {

    private String firstName;
    private String lastName;
    private List<OfferCodes> offerCodesList;
}
