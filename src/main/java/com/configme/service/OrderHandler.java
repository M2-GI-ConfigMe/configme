package com.configme.service;

import com.configme.domain.Order;
import com.configme.domain.User;
import com.configme.service.dto.CartDTO;

public interface OrderHandler {
    Order createOrderFromCart(CartDTO[] cart, User user) throws Exception;

    void validateOrder(Order order) throws Exception;
}
