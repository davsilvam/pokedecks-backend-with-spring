package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.ReportService;
import com.davsilvam.pokedecks.services.dtos.CustomerPurchaseReportDTO;
import com.davsilvam.pokedecks.services.dtos.DailyRevenueReportDTO;
import com.davsilvam.pokedecks.services.dtos.OutOfStockProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios administrativos")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/daily-revenue")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Relatório de receita diária",
            description = "Retorna o total de valor recebido por dia (valor monetário total de compras) dentro de um período determinado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Relatório gerado com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = DailyRevenueReportDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros de data inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            )
    })
    public ResponseEntity<List<DailyRevenueReportDTO>> getDailyRevenueReport(
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD)", example = "2024-01-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD)", example = "2024-12-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<DailyRevenueReportDTO> report = reportService.getDailyRevenueReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/out-of-stock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Relatório de produtos sem estoque",
            description = "Retorna todos os produtos que estão faltando em estoque (quantidade zerada), exibindo o ID, descrição e preço."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Relatório gerado com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = OutOfStockProductDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            )
    })
    public ResponseEntity<List<OutOfStockProductDTO>> getOutOfStockProducts() {
        List<OutOfStockProductDTO> report = reportService.getOutOfStockProducts();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/customer-purchases")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Relatório de compras por cliente",
            description = "Retorna o total de compras por cliente dentro de um período determinado, exibindo o ID do cliente, nome e quantidade de compras realizadas."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Relatório gerado com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = CustomerPurchaseReportDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros de data inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            )
    })
    public ResponseEntity<List<CustomerPurchaseReportDTO>> getCustomerPurchaseReport(
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD)", example = "2024-01-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD)", example = "2024-12-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<CustomerPurchaseReportDTO> report = reportService.getCustomerPurchaseReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
