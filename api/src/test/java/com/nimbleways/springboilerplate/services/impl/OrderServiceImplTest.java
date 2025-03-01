package com.nimbleways.springboilerplate.services.impl;

import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.exceptions.OrderNotFoundException;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.services.strategy.ProductHandlerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private ProductHandlerFactory productHandlerFactory;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Test Product");
        product.setType("NORMAL");

        order = new Order();
        order.setId(1L);
        order.setItems(Set.of(product));
    }

    @Test
    void shouldFindOrderById() {
        // Given
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        // When
        Order foundOrder = orderService.findById(1L);

        // Then
        assertThat(foundOrder).isEqualTo(order);
        then(orderRepository).should().findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        // Given
        given(orderRepository.findById(1L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> orderService.findById(1L))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessageContaining("1");
    }

    @Test
    void shouldProcessOrder() {
        // Given
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        // When
        Long result = orderService.processOrder(1L);

        // Then
        assertThat(result).isEqualTo(1L);
        then(productService).should().handleNormalProduct(product);
    }

    @Test
    void shouldProcessOrderUsingStrategy() {
        // Given

    }
}