package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.models.Card;
import com.davsilvam.pokedecks.models.repositories.CardRepository;
import com.davsilvam.pokedecks.models.repositories.OrderRepository;
import com.davsilvam.pokedecks.services.dtos.CustomerPurchaseReportDTO;
import com.davsilvam.pokedecks.services.dtos.DailyRevenueReportDTO;
import com.davsilvam.pokedecks.services.dtos.OutOfStockProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderRepository orderRepository;
    private final CardRepository cardRepository;

    public List<DailyRevenueReportDTO> getDailyRevenueReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Object[]> results = orderRepository.findDailyRevenue(startDateTime, endDateTime);

        return results.stream()
                .map(result -> new DailyRevenueReportDTO(
                        ((Date) result[0]).toLocalDate(),
                        ((Number) result[1]).doubleValue()
                ))
                .collect(Collectors.toList());
    }

    public List<OutOfStockProductDTO> getOutOfStockProducts() {
        List<Card> outOfStockCards = cardRepository.findByStockQuantity(0);

        return outOfStockCards.stream()
                .map(card -> new OutOfStockProductDTO(
                        card.getId(),
                        card.getName(),
                        card.getPrice()
                ))
                .collect(Collectors.toList());
    }

    public List<CustomerPurchaseReportDTO> getCustomerPurchaseReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Object[]> results = orderRepository.findCustomerPurchases(startDateTime, endDateTime);

        return results.stream()
                .map(result -> new CustomerPurchaseReportDTO(
                        (UUID) result[0],
                        (String) result[1],
                        ((Number) result[2]).longValue()
                ))
                .collect(Collectors.toList());
    }
}
