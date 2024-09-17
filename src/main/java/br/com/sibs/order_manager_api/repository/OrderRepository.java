package br.com.sibs.order_manager_api.repository;

import br.com.sibs.order_manager_api.entity.Item;
import br.com.sibs.order_manager_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByItemAndIsCompleteFalse(Item item);
}
