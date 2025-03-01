package com.nimbleways.springboilerplate.services.impl;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Test Product");
        product.setLeadTime(5);
        product.setAvailable(10);
        product.setSeasonStartDate(LocalDate.now().minusDays(1));
        product.setSeasonEndDate(LocalDate.now().plusDays(10));
        product.setExpiryDate(LocalDate.now().plusDays(20));
    }

    @Test
    void shouldNotifyDelayWhenProductHasLeadTime() {
        // Given
        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        productService.notifyDelay(5, product);

        // Then
        then(productRepository).should().save(product);
        then(notificationService).should().sendDelayNotification(5, product.getName());
    }

    @Test
    void shouldHandleSeasonalProduct_WhenAvailable() {
        // Given
        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        productService.handleSeasonalProduct(product);

        // Then
        assertThat(product.getAvailable()).isEqualTo(9);
        then(productRepository).should().save(product);
    }

    @Test
    void shouldHandleExpiredProduct_WhenExpired() {
        // Given
        product.setExpiryDate(LocalDate.now().minusDays(1));
        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        productService.handleExpiredProduct(product);

        // Then
        assertThat(product.getAvailable()).isEqualTo(0);
        then(notificationService).should().sendExpirationNotification(product.getName(), product.getExpiryDate());
        then(productRepository).should().save(product);
    }

    @Test
    void shouldHandleNormalProduct_WhenAvailable() {
        // Given
        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        productService.handleNormalProduct(product);

        // Then
        assertThat(product.getAvailable()).isEqualTo(9);
        then(productRepository).should().save(product);
    }

    @Test
    void shouldHandleNormalProduct_WhenOutOfStock_WithLeadTime() {
        // Given
        product.setAvailable(0);
        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        productService.handleNormalProduct(product);

        // Then
        then(productService).should().notifyDelay(product.getLeadTime(), product);
    }

}