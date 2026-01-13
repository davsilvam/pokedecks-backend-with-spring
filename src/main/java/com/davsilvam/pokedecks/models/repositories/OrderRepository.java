package com.davsilvam.pokedecks.models.repositories;

import com.davsilvam.pokedecks.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);

    @Query("""
            SELECT DATE(o.orderTime) as date, SUM(oi.quantity * c.price) as totalRevenue
            FROM Order o
            JOIN o.orderItems oi
            JOIN oi.card c
            WHERE o.orderTime >= :startDate AND o.orderTime <= :endDate
            GROUP BY DATE(o.orderTime)
            ORDER BY DATE(o.orderTime)
            """)
    List<Object[]> findDailyRevenue(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
            SELECT u.id, u.name, COUNT(o.id)
            FROM Order o
            JOIN o.user u
            WHERE o.orderTime >= :startDate AND o.orderTime <= :endDate
            GROUP BY u.id, u.name
            ORDER BY COUNT(o.id) DESC
            """)
    List<Object[]> findCustomerPurchases(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}