package br.com.sibs.order_manager_api.repository;

import br.com.sibs.order_manager_api.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
}
