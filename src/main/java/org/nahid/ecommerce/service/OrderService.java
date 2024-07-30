package org.nahid.ecommerce.service;

import org.nahid.ecommerce.dto.OrderDTO;
import org.nahid.ecommerce.dto.OrderItemDTO;
import org.nahid.ecommerce.mapper.OrderMapper;
import org.nahid.ecommerce.models.Order;
import org.nahid.ecommerce.models.OrderItem;
import org.nahid.ecommerce.models.Payment;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.models.User;
import org.nahid.ecommerce.repository.OrderRepository;
import org.nahid.ecommerce.repository.PaymentRepository;
import org.nahid.ecommerce.repository.ProductRepository;
import org.nahid.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public OrderDTO createOrder(Long userId, String paymentMethod, List<OrderItemDTO> orderItems) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("Order Accepted !");

        double totalAmount = 0;
        for (OrderItemDTO itemDTO : orderItems) {
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

        // Save the order first
        orderRepository.save(order);

        // Create and set the payment
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        paymentRepository.save(payment);

        return orderMapper.mapToOrderDTO(order);
    }

    @Transactional
    public OrderDTO updateOrder(Long orderId, String paymentMethod, List<OrderItemDTO> orderItems) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Update order details
        double totalAmount = 0;
        order.getOrderItems().clear();
        for (OrderItemDTO itemDTO : orderItems) {
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

        // Update payment
        Payment payment = paymentRepository.findByOrder(order)
                .orElse(new Payment()); // Create a new Payment if not found
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        paymentRepository.save(payment);

        return orderMapper.mapToOrderDTO(orderRepository.save(order));
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.mapToOrderDTO(order);
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderMapper::mapToOrderDTO).collect(Collectors.toList());
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }
}
