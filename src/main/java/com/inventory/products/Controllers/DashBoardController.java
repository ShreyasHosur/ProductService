package com.inventory.products.Controllers;

import com.inventory.products.Model.Request.Period;
import com.inventory.products.Model.Response.ExpenseResponse;
import com.inventory.products.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class DashBoardController {

    @Autowired
    private DashboardService dashboardService;

    @PostMapping("get-pie-chart-data")
    public ResponseEntity<Map<String,Integer>> getPiechartData(@RequestBody Period period){
        return ResponseEntity.ok(dashboardService.getPiechartData(period));
    }

    @PostMapping("get-expense-data")
    public ResponseEntity<ExpenseResponse> getExpense(@RequestBody Period period){
        return ResponseEntity.ok(dashboardService.getExpenseData(period));
    }
}
