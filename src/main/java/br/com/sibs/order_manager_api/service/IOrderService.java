package br.com.sibs.order_manager_api.service;

import br.com.sibs.order_manager_api.dto.request.OrderRequestDTO;
import br.com.sibs.order_manager_api.dto.response.OrderResponseDTO;

import java.util.List;

public interface IOrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Long id);
    OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO);
    void deleteOrder(Long id);
    void checkAndCompleteOrder(Long id); 
}
