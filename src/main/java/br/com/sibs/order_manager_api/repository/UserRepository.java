package br.com.sibs.order_manager_api.repository;

import br.com.sibs.order_manager_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
