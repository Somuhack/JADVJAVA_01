package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.exception.ProductNotFoundException;
import com.example.orderservice.exception.UserNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.dto.UserResponse;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestClient restClient;

    public OrderService(OrderRepository orderRepository, RestClient restClient) {
        this.orderRepository = orderRepository;
        this.restClient = restClient;
    }
public Order createOrder(OrderRequest dto) {

    UserResponse user;

    try {
        user = restClient.get()
                .uri("http://localhost:8081/users/" + dto.getUserId())
                .retrieve()
                .body(UserResponse.class);

    } catch (HttpClientErrorException.NotFound ex) {
        throw new UserNotFoundException(
                "User not found with id: " + dto.getUserId()
        );
    }

    if (user == null) {
        throw new UserNotFoundException(
                "User not found with id: " + dto.getUserId()
        );
    }

    ProductResponse product;

    try {
        product = restClient.get()
                .uri("http://localhost:8082/products/" + dto.getProductId())
                .retrieve()
                .body(ProductResponse.class);

    } catch (HttpClientErrorException.NotFound ex) {
        throw new ProductNotFoundException(
                "Product not found with id: " + dto.getProductId()
        );
    }

    if (product == null) {
        throw new ProductNotFoundException(
                "Product not found with id: " + dto.getProductId()
        );
    }

   // 3. Calculate total price
        double totalPrice = product.getPrice() * dto.getQuantity();
        Order order = new Order();
        order.setProductId(dto.getProductId());
        order.setUserId(dto.getUserId());
        order.setQuantity(dto.getQuantity());
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);

}

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Order not found with id: " + id));
    }
}