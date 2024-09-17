package br.com.sibs.order_manager_api.service.impl;

import br.com.sibs.order_manager_api.dto.request.ItemRequestDTO;
import br.com.sibs.order_manager_api.dto.response.ItemResponseDTO;
import br.com.sibs.order_manager_api.entity.Item;
import br.com.sibs.order_manager_api.repository.ItemRepository;
import br.com.sibs.order_manager_api.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Creates a new item if it does not already exist.
     * 
     * @param itemRequestDTO contains the name of the item
     * @return ItemResponseDTO containing the item details
     * @throws ResponseStatusException with status 409 if the item name already
     *                                 exists
     */
    @Override
    public ItemResponseDTO createItem(ItemRequestDTO itemRequestDTO) {

        if (itemRepository.existsByName(itemRequestDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item already exists");
        }

        Item item = new Item();
        item.setName(itemRequestDTO.getName());
        Item savedItem = itemRepository.save(item);
        return convertToResponseDTO(savedItem);
    }

    /**
     * Retrieves all items in the system.
     * 
     * @return List of ItemResponseDTOs containing all items
     */
    @Override
    public List<ItemResponseDTO> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an item by its ID.
     * 
     * @param id the ID of the item
     * @return ItemResponseDTO containing item details
     * @throws ResponseStatusException with status 404 if item not found
     */
    @Override
    public ItemResponseDTO getItemById(Long id) {
        return itemRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    /**
     * Updates an item if it exists.
     * 
     * @param id             the ID of the item to update
     * @param itemRequestDTO contains the updated name of the item
     * @return ItemResponseDTO containing updated item details
     * @throws ResponseStatusException with status 404 if item not found
     * @throws ResponseStatusException with status 409 if item name is already in
     *                                 use
     */
    @Override
    public ItemResponseDTO updateItem(Long id, ItemRequestDTO itemRequestDTO) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        if (!item.getName().equals(itemRequestDTO.getName()) && itemRepository.existsByName(itemRequestDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item name already exists");
        }

        item.setName(itemRequestDTO.getName());
        Item updatedItem = itemRepository.save(item);
        return convertToResponseDTO(updatedItem);
    }

    /**
     * Deletes an item by its ID.
     * 
     * @param id the ID of the item to delete
     * @throws ResponseStatusException with status 404 if item not found
     */
    @Override
    public void deleteItem(Long id) {

        if (!itemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        itemRepository.deleteById(id);
    }

    /**
     * Converts an Item entity to an ItemResponseDTO.
     * 
     * @param item the item entity to convert
     * @return ItemResponseDTO containing the item details
     */
    private ItemResponseDTO convertToResponseDTO(Item item) {
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setId(item.getId());
        itemResponseDTO.setName(item.getName());
        return itemResponseDTO;
    }
}
