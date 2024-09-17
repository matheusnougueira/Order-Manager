package br.com.sibs.order_manager_api.service.impl;

import br.com.sibs.order_manager_api.dto.request.StockMovementRequestDTO;
import br.com.sibs.order_manager_api.dto.response.StockMovementResponseDTO;
import br.com.sibs.order_manager_api.entity.Item;
import br.com.sibs.order_manager_api.entity.Order;
import br.com.sibs.order_manager_api.entity.StockMovement;
import br.com.sibs.order_manager_api.repository.ItemRepository;
import br.com.sibs.order_manager_api.repository.OrderRepository;
import br.com.sibs.order_manager_api.repository.StockMovementRepository;
import br.com.sibs.order_manager_api.service.IEmailService;
import br.com.sibs.order_manager_api.service.IStockMovementService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockMovementServiceImpl implements IStockMovementService {

    private static final Logger logger = LoggerFactory.getLogger(StockMovementServiceImpl.class);

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IEmailService emailService;

    @Override
    public StockMovementResponseDTO createStockMovement(StockMovementRequestDTO stockMovementRequestDTO) {
        logger.info("Creating new stock movement for item ID: {}", stockMovementRequestDTO.getItemId());

        Item item = itemRepository.findById(stockMovementRequestDTO.getItemId())
                .orElseThrow(() -> {
                    logger.error("Item not found for ID: {}", stockMovementRequestDTO.getItemId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
                });
        StockMovement stockMovement = new StockMovement();
        stockMovement.setItem(item);
        stockMovement.setQuantity(stockMovementRequestDTO.getQuantity());
        stockMovement.setCreationDate(LocalDateTime.now());

        StockMovement savedStockMovement = stockMovementRepository.save(stockMovement);
        logger.info("Stock movement created with ID: {} for item ID: {}", savedStockMovement.getId(), item.getId());
        allocateStockToOrders(item, stockMovementRequestDTO.getQuantity());

        return convertToResponseDTO(savedStockMovement);
    }

    private void allocateStockToOrders(Item item, Integer stockQuantity) {
        logger.info("Allocating stock for item ID: {} with available quantity: {}", item.getId(), stockQuantity);

        List<Order> pendingOrders = orderRepository.findByItemAndIsCompleteFalse(item);
        logger.info("Found {} pending orders for item ID: {}", pendingOrders.size(), item.getId());

        for (Order order : pendingOrders) {
            int orderQuantity = order.getQuantity() - order.getFulfilledQuantity();
            logger.info("Processing order ID: {} with required quantity: {}", order.getId(), orderQuantity);

            if (orderQuantity <= stockQuantity) {
                order.setIsComplete(true);
                order.setFulfilledQuantity(order.getQuantity());
                orderRepository.save(order);

                logger.info("Order ID: {} completed, sending email to user: {}", order.getId(),
                        order.getUser().getEmail());
                emailService.sendOrderCompletionEmail(order.getUser(), order);
                stockQuantity -= orderQuantity;
                logger.info("Stock quantity updated, remaining stock: {}", stockQuantity);
            } else {
                order.setFulfilledQuantity(order.getFulfilledQuantity() + stockQuantity);
                orderRepository.save(order);

                logger.info("Order ID: {} partially fulfilled, updated fulfilled quantity: {}", order.getId(),
                        order.getFulfilledQuantity());
                stockQuantity = 0;
            }

            if (stockQuantity <= 0) {
                logger.info("Stock depleted, stopping allocation process.");
                break;
            }
        }
    }

    @Override
    public List<StockMovementResponseDTO> getAllStockMovements() {
        logger.info("Fetching all stock movements.");
        return stockMovementRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StockMovementResponseDTO getStockMovementById(Long id) {
        logger.info("Fetching stock movement by ID: {}", id);
        return stockMovementRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> {
                    logger.error("Stock movement not found for ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock movement not found");
                });
    }

    @Override
    public void deleteStockMovement(Long id) {
        logger.info("Deleting stock movement with ID: {}", id);
        if (!stockMovementRepository.existsById(id)) {
            logger.error("Stock movement not found for ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock movement not found");
        }
        stockMovementRepository.deleteById(id);
        logger.info("Stock movement deleted with ID: {}", id);
    }

    private StockMovementResponseDTO convertToResponseDTO(StockMovement stockMovement) {
        StockMovementResponseDTO responseDTO = new StockMovementResponseDTO();
        responseDTO.setId(stockMovement.getId());
        responseDTO.setCreationDate(stockMovement.getCreationDate());
        responseDTO.setItemName(stockMovement.getItem().getName());
        responseDTO.setQuantity(stockMovement.getQuantity());
        return responseDTO;
    }
}
