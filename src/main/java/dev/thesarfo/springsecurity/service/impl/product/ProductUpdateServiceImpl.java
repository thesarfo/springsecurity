package dev.thesarfo.springsecurity.service.impl.product;

import dev.thesarfo.springsecurity.exception.product.ProductAlreadyExistException;
import dev.thesarfo.springsecurity.exception.product.ProductNotFoundException;
import dev.thesarfo.springsecurity.model.Product;
import dev.thesarfo.springsecurity.model.dto.request.ProductUpdateRequest;
import dev.thesarfo.springsecurity.model.entity.ProductEntity;
import dev.thesarfo.springsecurity.model.mapper.product.ProductEntityToProductMapper;
import dev.thesarfo.springsecurity.model.mapper.product.ProductUpdateRequestToProductEntityMapper;
import dev.thesarfo.springsecurity.repository.product.ProductRepository;
import dev.thesarfo.springsecurity.service.product.ProductUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdateService {

    private final ProductRepository productRepository;

    private final ProductUpdateRequestToProductEntityMapper productUpdateRequestToProductEntityMapper =
            ProductUpdateRequestToProductEntityMapper.initialize();

    private final ProductEntityToProductMapper productEntityToProductMapper =
            ProductEntityToProductMapper.initialize();

    @Override
    public Product updateProductById(String productId, ProductUpdateRequest productUpdateRequest) {

        checkProductNameUniqueness(productUpdateRequest.getName());

        final ProductEntity productEntityToBeUpdate = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("With given productID = " + productId));

        productUpdateRequestToProductEntityMapper.mapForUpdating(productEntityToBeUpdate, productUpdateRequest);

        ProductEntity updatedProductEntity = productRepository.save(productEntityToBeUpdate);

        return productEntityToProductMapper.map(updatedProductEntity);

    }

    private void checkProductNameUniqueness(final String productName) {
        if (productRepository.existsProductEntityByName(productName)) {
            throw new ProductAlreadyExistException("With given product name = " + productName);
        }
    }

}
