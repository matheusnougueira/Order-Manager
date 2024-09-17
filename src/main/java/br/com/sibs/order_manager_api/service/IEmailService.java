package br.com.sibs.order_manager_api.service;

import br.com.sibs.order_manager_api.entity.Order;
import br.com.sibs.order_manager_api.entity.User;

public interface IEmailService {
    void sendOrderCompletionEmail(User user, Order order);
}
