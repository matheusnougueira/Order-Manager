package br.com.sibs.order_manager_api.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ItemRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;
}
