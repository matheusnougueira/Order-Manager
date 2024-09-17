package br.com.sibs.order_manager_api.service.impl;

import br.com.sibs.order_manager_api.dto.request.OrderRequestDTO;
import br.com.sibs.order_manager_api.dto.response.OrderResponseDTO;
import br.com.sibs.order_manager_api.entity.Item;
import br.com.sibs.order_manager_api.entity.Order;
import br.com.sibs.order_manager_api.entity.User;
import br.com.sibs.order_manager_api.repository.ItemRepository;
import br.com.sibs.order_manager_api.repository.OrderRepository;
import br.com.sibs.order_manager_api.repository.UserRepository;
import br.com.sibs.order_manager_api.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new order for the specified item and user.
     * 
     * @param orderRequestDTO contains the order details
     * @return OrderResponseDTO containing the created order details
     */
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        Item item = itemRepository.findById(orderRequestDTO.getItemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        User user = userRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Order order = new Order();
        order.setItem(item);
        order.setUser(user);
        order.setQuantity(orderRequestDTO.getQuantity());
        order.setCreationDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        return convertToResponseDTO(savedOrder);
    }

    /**
     * Get all orders in the system.
     * 
     * @return List of OrderResponseDTOs
     */
    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get an order by its ID.
     * 
     * @param id the order ID
     * @return OrderResponseDTO containing the order details
     */
    @Override
    public OrderResponseDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    /**
     * Update an existing order by its ID.
     * 
     * @param id              the order ID
     * @param orderRequestDTO contains the updated order details
     * @return OrderResponseDTO containing the updated order details
     */
    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        Item item = itemRepository.findById(orderRequestDTO.getItemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        User user = userRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        order.setItem(item);
        order.setUser(user);
        order.setQuantity(orderRequestDTO.getQuantity());
        Order updatedOrder = orderRepository.save(order);

        return convertToResponseDTO(updatedOrder);
    }

    /**
     * Delete an order by its ID.
     * 
     * @param id the order ID
     */
    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        orderRepository.deleteById(id);
    }

    /**
     * Check and complete the order logic.
     * 
     * @param id the order ID
     */
    @Override
    public void checkAndCompleteOrder(Long id) {
        // TODO
    }

    /**
     * Convert an Order entity to OrderResponseDTO.
     * 
     * @param order the order entity
     * @return OrderResponseDTO
     */
    private OrderResponseDTO convertToResponseDTO(Order order) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setId(order.getId());
        responseDTO.setCreationDate(order.getCreationDate());
        responseDTO.setItemName(order.getItem().getName());
        responseDTO.setQuantity(order.getQuantity());
        responseDTO.setUserName(order.getUser().getName());
        responseDTO.setIsComplete(order.getIsComplete());
        return responseDTO;
    }
}
