package br.com.sibs.order_manager_api.controller;

import br.com.sibs.order_manager_api.dto.request.StockMovementRequestDTO;
import br.com.sibs.order_manager_api.dto.response.StockMovementResponseDTO;
import br.com.sibs.order_manager_api.service.IStockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    @Autowired
    private IStockMovementService stockMovementService;

    @PostMapping
    public ResponseEntity<StockMovementResponseDTO> createStockMovement(@Valid @RequestBody StockMovementRequestDTO stockMovementRequestDTO) {
        StockMovementResponseDTO createdStockMovement = stockMovementService.createStockMovement(stockMovementRequestDTO);
        return ResponseEntity.ok(createdStockMovement);
    }

    @GetMapping
    public ResponseEntity<List<StockMovementResponseDTO>> getAllStockMovements() {
        List<StockMovementResponseDTO> stockMovements = stockMovementService.getAllStockMovements();
        return ResponseEntity.ok(stockMovements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponseDTO> getStockMovementById(@PathVariable Long id) {
        StockMovementResponseDTO stockMovement = stockMovementService.getStockMovementById(id);
        return ResponseEntity.ok(stockMovement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockMovement(@PathVariable Long id) {
        stockMovementService.deleteStockMovement(id);
        return ResponseEntity.noContent().build();
    }
}
