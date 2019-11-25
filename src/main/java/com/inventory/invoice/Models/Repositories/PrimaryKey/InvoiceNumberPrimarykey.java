package com.inventory.invoice.Models.Repositories.PrimaryKey;

import lombok.Data;
import java.io.Serializable;

@Data
public class InvoiceNumberPrimarykey implements Serializable {
    private int invoiceId;
    private int currentNumber;
}
