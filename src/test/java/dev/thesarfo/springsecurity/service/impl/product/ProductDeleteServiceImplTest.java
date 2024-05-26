package dev.thesarfo.springsecurity.service.impl.product;


import dev.thesarfo.springsecurity.base.AbstractBaseServiceTest;
import dev.thesarfo.springsecurity.exception.product.ProductNotFoundException;
import dev.thesarfo.springsecurity.model.entity.product.ProductEntity;
import dev.thesarfo.springsecurity.repository.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductDeleteServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private ProductDeleteServiceImpl productDeleteService;

    @Mock
    private ProductRepository productRepository;


    @Test
    void givenProductId_whenDeleteProduct_thenReturnProductDeleted() {

        // Given
        String productId = "1";
        ProductEntity existingProductEntity = new ProductEntity();
        existingProductEntity.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProductEntity));
        doNothing().when(productRepository).delete(existingProductEntity);

        // When
        productDeleteService.deleteProductById(productId);

        // Then
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(existingProductEntity);

    }

    @Test
    void givenProductId_whenProductNotFound_thenThrowProductNotFoundException() {

        // Given
        String productId = "1";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ProductNotFoundException.class, () -> productDeleteService.deleteProductById(productId));

        // Verify
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).delete(any());

    }

}