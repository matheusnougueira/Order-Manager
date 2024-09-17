package br.com.sibs.order_manager_api.service.impl;

import br.com.sibs.order_manager_api.dto.request.UserRequestDTO;
import br.com.sibs.order_manager_api.dto.response.UserResponseDTO;
import br.com.sibs.order_manager_api.entity.User;
import br.com.sibs.order_manager_api.repository.UserRepository;
import br.com.sibs.order_manager_api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user if the email is not already in use.
     * 
     * @param userRequestDTO contains the name and email of the user
     * @return UserResponseDTO containing the user details
     * @throws ResponseStatusException with status 409 if email already exists
     */
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        User savedUser = userRepository.save(user);
        return convertToResponseDTO(savedUser);
    }

    /**
     * Retrieves all users in the system.
     * 
     * @return List of UserResponseDTOs containing all users
     */
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id the ID of the user
     * @return UserResponseDTO containing user details
     * @throws ResponseStatusException with status 404 if user not found
     */
    @Override
    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * Updates a user's details if the user exists and the email is not in use by
     * another user.
     * 
     * @param id             the ID of the user to update
     * @param userRequestDTO contains the updated name and email of the user
     * @return UserResponseDTO containing updated user details
     * @throws ResponseStatusException with status 404 if user not found
     * @throws ResponseStatusException with status 409 if email is already in use by
     *                                 another user
     */
    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!user.getEmail().equals(userRequestDTO.getEmail())
                && userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use by another user");
        }

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        User updatedUser = userRepository.save(user);
        return convertToResponseDTO(updatedUser);
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id the ID of the user to delete
     * @throws ResponseStatusException with status 404 if user not found
     */
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Converts a User entity to a UserResponseDTO.
     * 
     * @param user the user entity to convert
     * @return UserResponseDTO containing the user details
     */
    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        return userResponseDTO;
    }
}
