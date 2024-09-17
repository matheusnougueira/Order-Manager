package br.com.sibs.order_manager_api.service.impl;

import br.com.sibs.order_manager_api.entity.Order;
import br.com.sibs.order_manager_api.entity.User;
import br.com.sibs.order_manager_api.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendOrderCompletionEmail(User user, Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Order Completed");
            message.setText("Your order with ID " + order.getId() + " has been completed.");
            emailSender.send(message);
            logger.info("Email sent to: {} for completed order ID: {}", user.getEmail(), order.getId());
        } catch (Exception e) {
            logger.error("Error sending email to: {}. Exception: {}", user.getEmail(), e.getMessage());
            throw e;
        }
    }
}
