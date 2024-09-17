package br.com.sibs.order_manager_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {

    @NotNull(message = "Item ID is required")
    private Long itemId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
}
