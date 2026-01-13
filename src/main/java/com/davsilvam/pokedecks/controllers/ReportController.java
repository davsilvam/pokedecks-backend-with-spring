package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.ReportService;
import com.davsilvam.pokedecks.services.dtos.CustomerPurchaseReportDTO;
import com.davsilvam.pokedecks.services.dtos.DailyRevenueReportDTO;
import com.davsilvam.pokedecks.services.dtos.OutOfStockProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Endpoints for generating reports")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/daily-revenue")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get daily revenue report", description = "Returns total revenue received per day within a date range")
    public ResponseEntity<List<DailyRevenueReportDTO>> getDailyRevenueReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<DailyRevenueReportDTO> report = reportService.getDailyRevenueReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/out-of-stock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get out of stock products", description = "Returns all products that are currently out of stock")
    public ResponseEntity<List<OutOfStockProductDTO>> getOutOfStockProducts() {
        List<OutOfStockProductDTO> report = reportService.getOutOfStockProducts();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/customer-purchases")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get customer purchase report", description = "Returns total purchases per customer within a date range")
    public ResponseEntity<List<CustomerPurchaseReportDTO>> getCustomerPurchaseReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<CustomerPurchaseReportDTO> report = reportService.getCustomerPurchaseReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
