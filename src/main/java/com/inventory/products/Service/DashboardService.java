package com.inventory.products.Service;

import com.inventory.products.Model.CategoryJoinedData;
import com.inventory.products.Model.ProductPurchased;
import com.inventory.products.Model.ProductSold;
import com.inventory.products.Model.Repositories.ProductSoldRepository;
import com.inventory.products.Model.Repositories.ProductsInfoRepository;
import com.inventory.products.Model.Repositories.ProductsPurchasedRepository;
import com.inventory.products.Model.Request.Period;
import com.inventory.products.Model.Response.ExpenseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private ProductsInfoRepository productsInfoRepository;
    private ProductsPurchasedRepository productsPurchasedRepository;
    private ProductSoldRepository productSoldRepository;

    @Autowired
    DashboardService(ProductsInfoRepository productsInfoRepository,
                     ProductsPurchasedRepository productsPurchasedRepository,
                     ProductSoldRepository productSoldRepository){
        this.productsInfoRepository = productsInfoRepository;
        this.productsPurchasedRepository = productsPurchasedRepository;
        this.productSoldRepository = productSoldRepository;
    }

    //getPiechartData
    //getSalesData
    //getExpenseData
    //getStoreData

    public Map<String,Integer> getPiechartData(Period period){
        LocalDate startDate = getStartDate(period);
        LocalDate endDate = LocalDate.now();
       return productsInfoRepository.categoryData().stream()
                .filter(product -> isBetween(endDate , startDate ,product.getsolddate()))
                .collect(
                        Collectors.groupingBy(CategoryJoinedData::getProductCategory,
                                Collectors.summingInt(CategoryJoinedData::gettotalSP)));

    }

    public ExpenseResponse getExpenseData(Period period){
        LocalDate startdate = getStartDate(period);
        LocalDate endDate = LocalDate.now();

        int netIncome = getIncome(startdate,endDate);
        int netExpense = getExpense(startdate,endDate);

        return ExpenseResponse.builder().netIncome(netIncome).netExpense(netExpense).build();


    }

    private int getIncome(LocalDate startDate , LocalDate endDate){
        return productsPurchasedRepository.productsBetweenDates(startDate,endDate)
                .stream()
                .map(ProductPurchased::getTotalPrice)
                .reduce(0, Integer::sum);

    }

    private int getExpense(LocalDate startDate , LocalDate endDate){
        return productSoldRepository.productsBetweenDates(startDate, endDate)
                .stream()
                .map(ProductSold::getTotalSP)
                .reduce(0,Integer::sum);
    }

    private LocalDate getStartDate(Period period){
        return LocalDate.now()
                .minusDays(period.getDays())
                .minusWeeks(period.getWeeks())
                .minusMonths(period.getMonths())
                .minusYears(period.getYears());
    }

    private boolean isBetween(LocalDate endDate , LocalDate startDate , LocalDate givenDate){
        return givenDate.isEqual(endDate) || givenDate.isAfter(startDate) && givenDate.isBefore(endDate);
    }
}
