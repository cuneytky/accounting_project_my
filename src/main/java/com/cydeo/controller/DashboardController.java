package com.cydeo.controller;


import com.cydeo.client.ExchangeClient;
import com.cydeo.service.DashboardService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final InvoiceService invoiceService;
    private final ExchangeClient exchangeClient;

    public DashboardController(DashboardService dashboardService, InvoiceService invoiceService, ExchangeClient exchangeClient) {
        this.dashboardService = dashboardService;
        this.invoiceService = invoiceService;
        this.exchangeClient = exchangeClient;
    }

    @GetMapping
    public String dashboardPage(Model model){

      //  model.addAttribute("invoices",invoiceService......);
        model.addAttribute("summaryNumbers",dashboardService.summaryCalculation() );
        model.addAttribute("exchangeRates",exchangeClient.getExchangesRates().getUsd());

        return "/dashboard";
    }

}
