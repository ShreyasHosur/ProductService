package com.inventory.Controllers;

import com.inventory.Model.Request.Period;
import com.inventory.Model.Response.ExpenseResponse;
import com.inventory.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@CrossOrigin(origins="http://localhost:3000")
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
