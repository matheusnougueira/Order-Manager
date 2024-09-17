package br.com.sibs.order_manager_api.service;

import br.com.sibs.order_manager_api.dto.request.UserRequestDTO;
import br.com.sibs.order_manager_api.dto.response.UserResponseDTO;
import java.util.List;

public interface IUserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
    void deleteUser(Long id);
}
