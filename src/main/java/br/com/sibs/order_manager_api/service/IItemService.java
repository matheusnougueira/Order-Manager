package br.com.sibs.order_manager_api.service;

import br.com.sibs.order_manager_api.dto.request.ItemRequestDTO;
import br.com.sibs.order_manager_api.dto.response.ItemResponseDTO;
import java.util.List;

public interface IItemService {
    ItemResponseDTO createItem(ItemRequestDTO itemRequestDTO);
    List<ItemResponseDTO> getAllItems();
    ItemResponseDTO getItemById(Long id);
    ItemResponseDTO updateItem(Long id, ItemRequestDTO itemRequestDTO);
    void deleteItem(Long id);
}
