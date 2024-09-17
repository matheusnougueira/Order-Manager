package br.com.sibs.order_manager_api.controller;

import br.com.sibs.order_manager_api.dto.request.ItemRequestDTO;
import br.com.sibs.order_manager_api.dto.response.ItemResponseDTO;
import br.com.sibs.order_manager_api.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private IItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@Valid @RequestBody ItemRequestDTO itemRequestDTO) {
        ItemResponseDTO createdItem = itemService.createItem(itemRequestDTO);
        return ResponseEntity.ok(createdItem);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        List<ItemResponseDTO> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Long id) {
        ItemResponseDTO item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItem(@PathVariable Long id, @Valid @RequestBody ItemRequestDTO itemRequestDTO) {
        ItemResponseDTO updatedItem = itemService.updateItem(id, itemRequestDTO);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
