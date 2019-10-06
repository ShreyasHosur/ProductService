package com.inventory.Model.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseResponse {

    private int netIncome;
    private int netExpense;
}
