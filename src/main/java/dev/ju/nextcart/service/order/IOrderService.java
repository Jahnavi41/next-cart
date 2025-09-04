package dev.ju.nextcart.service.order;

import dev.ju.nextcart.dto.OrderDTO;
import dev.ju.nextcart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);
    List<OrderDTO> getUserOrders(Long userId);
}
