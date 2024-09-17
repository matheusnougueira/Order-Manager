package br.com.sibs.order_manager_api.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StockMovementResponseDTO {

    private Long id;
    private LocalDateTime creationDate;
    private String itemName;
    private Integer quantity;
}
