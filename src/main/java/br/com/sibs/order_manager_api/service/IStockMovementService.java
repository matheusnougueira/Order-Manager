package br.com.sibs.order_manager_api.service;

import br.com.sibs.order_manager_api.dto.request.StockMovementRequestDTO;
import br.com.sibs.order_manager_api.dto.response.StockMovementResponseDTO;

import java.util.List;

public interface IStockMovementService {
    StockMovementResponseDTO createStockMovement(StockMovementRequestDTO stockMovementRequestDTO);
    List<StockMovementResponseDTO> getAllStockMovements();
    StockMovementResponseDTO getStockMovementById(Long id);
    void deleteStockMovement(Long id);
}
