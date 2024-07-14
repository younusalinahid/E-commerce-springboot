package org.nahid.ecommerce.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private String orderStatus;
    private List<OrderItemDTO> orderItems;
    private double totalAmount;

}
