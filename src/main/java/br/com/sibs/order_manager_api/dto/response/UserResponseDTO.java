package br.com.sibs.order_manager_api.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
}
