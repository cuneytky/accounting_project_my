package com.cydeo.service.impl;


import com.cydeo.service.DashboardService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final InvoiceService invoiceService;

    public DashboardServiceImpl(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
    @Override
    public Map<String, BigDecimal> summaryCalculation() {

        Map<String, BigDecimal> summaryCalculations = new HashMap<>();

        // Total Coast:

        // Total Sales:

        // Profit:


        /*
        summaryCalculations.put("totalCost", totalCost);
        summaryCalculations.put("totalSales",totalSales);
        summaryCalculations.put("profitLoss",profitLoss);
        */

        return summaryCalculations;
    }
}
