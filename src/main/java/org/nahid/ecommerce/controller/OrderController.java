package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.dto.OrderDTO;
import org.nahid.ecommerce.dto.OrderItemDTO;
import org.nahid.ecommerce.models.Order;
import org.nahid.ecommerce.models.OrderItem;
import org.nahid.ecommerce.models.User;
import org.nahid.ecommerce.repository.UserRepository;
import org.nahid.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderDTO> orderProducts(
            @PathVariable Long userId,
            @RequestBody List<OrderItemDTO> orderItems) {

        OrderDTO order = orderService.createOrder(userId, orderItems);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
