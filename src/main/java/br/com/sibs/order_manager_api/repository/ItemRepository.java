package br.com.sibs.order_manager_api.repository;

import br.com.sibs.order_manager_api.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByName(String name);
    Optional<Item> findByName(String name);
}
