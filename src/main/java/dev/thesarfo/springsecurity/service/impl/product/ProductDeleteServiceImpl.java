package dev.thesarfo.springsecurity.service.impl.product;

import dev.thesarfo.springsecurity.exception.product.ProductNotFoundException;
import dev.thesarfo.springsecurity.model.entity.product.ProductEntity;
import dev.thesarfo.springsecurity.repository.product.ProductRepository;
import dev.thesarfo.springsecurity.service.product.ProductDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDeleteServiceImpl implements ProductDeleteService {

    private final ProductRepository productRepository;

    @Override
    public void deleteProductById(String productId) {

        final ProductEntity productEntityToBeDelete = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("With given productID = " + productId));

        productRepository.delete(productEntityToBeDelete);

    }

}
