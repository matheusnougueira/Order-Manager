package br.com.sibs.order_manager_api.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {

    private Long id;
    private LocalDateTime creationDate;
    private String itemName;
    private Integer quantity;
    private String userName;
    private Boolean isComplete;
}
