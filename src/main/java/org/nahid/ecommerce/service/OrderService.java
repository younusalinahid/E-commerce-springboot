package org.nahid.ecommerce.service;

import org.nahid.ecommerce.dto.OrderDTO;
import org.nahid.ecommerce.dto.OrderItemDTO;
import org.nahid.ecommerce.mapper.OrderMapper;
import org.nahid.ecommerce.models.*;
import org.nahid.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderDTO createOrder(Long userId, List<OrderItemDTO> orderItemsDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("Order Accepted !");
        order.setOrderItems(new ArrayList<>());

        Integer totalAmount = 0;

        for (OrderItemDTO itemDTO : orderItemsDTO) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice() * itemDTO.getQuantity());
            order.getOrderItems().add(item);
            totalAmount += item.getPrice();
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapToOrderDTO(savedOrder);
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.mapToOrderDTO(order);
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::mapToOrderDTO)
                .collect(Collectors.toList());
    }
}
