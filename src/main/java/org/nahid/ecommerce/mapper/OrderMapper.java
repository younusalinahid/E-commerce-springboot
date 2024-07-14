package org.nahid.ecommerce.mapper;

import org.nahid.ecommerce.dto.OrderDTO;
import org.nahid.ecommerce.dto.OrderItemDTO;
import org.nahid.ecommerce.models.Order;
import org.nahid.ecommerce.models.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO mapToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderStatus(order.getOrderStatus());

        // Map OrderItems to OrderItemDTOs
        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                .map(this::mapToOrderItemDTO)
                .collect(Collectors.toList());
        orderDTO.setOrderItems(orderItemDTOs);

        // Calculate total amount
        double totalAmount = order.getOrderItems().stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();
        orderDTO.setTotalAmount(totalAmount);

        return orderDTO;
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        return orderItemDTO;
    }
}
