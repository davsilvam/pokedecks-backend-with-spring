package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.config.errors.exceptions.InsufficientStockException;
import com.davsilvam.pokedecks.config.errors.exceptions.ResourceNotFoundException;
import com.davsilvam.pokedecks.models.Card;
import com.davsilvam.pokedecks.models.Order;
import com.davsilvam.pokedecks.models.OrderItem;
import com.davsilvam.pokedecks.models.User;
import com.davsilvam.pokedecks.models.repositories.CardRepository;
import com.davsilvam.pokedecks.models.repositories.OrderRepository;
import com.davsilvam.pokedecks.models.repositories.UserRepository;
import com.davsilvam.pokedecks.services.dtos.CreateOrderRequestDTO;
import com.davsilvam.pokedecks.services.dtos.OrderResponseDTO;
import com.davsilvam.pokedecks.services.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderRequestDTO order) {
        User user = userRepository.findById(order.userId()).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("Usuário com ID " + order.userId());
        }

        Order orderEntity = Order.builder()
                .orderTime(order.orderTime())
                .user(user)
                .build();

        order.orderItems().forEach(item -> {
            Card card = cardRepository.findById(item.cardId()).orElse(null);

            if (card == null) {
                throw new ResourceNotFoundException("Carta com ID " + item.cardId());
            }

            if (card.getStockQuantity() < item.quantity()) {
                throw new InsufficientStockException(
                        "Estoque insuficiente para a carta '" + card.getName() + 
                        "'. Disponível: " + card.getStockQuantity() + 
                        ", Solicitado: " + item.quantity()
                );
            }

            card.setStockQuantity(card.getStockQuantity() - item.quantity());
            cardRepository.save(card);

            OrderItem orderItem = OrderItem.builder()
                    .quantity(item.quantity())
                    .card(card)
                    .order(orderEntity)
                    .build();

            orderEntity.getOrderItems().add(orderItem);
        });

        Order savedOrder = orderRepository.save(orderEntity);
        return OrderMapper.toDTO(savedOrder);
    }

    public OrderResponseDTO getOrderById(UUID id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            throw new ResourceNotFoundException("Pedido com ID " + id);
        }

        return OrderMapper.toDTO(order);
    }

    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    public List<OrderResponseDTO> getOrdersByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("Usuário com ID " + userId);
        }

        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    public void deleteOrderById(UUID id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            throw new ResourceNotFoundException("Pedido com ID " + id);
        }

        orderRepository.deleteById(id);
    }
}
