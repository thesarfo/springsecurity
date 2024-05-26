package dev.thesarfo.springsecurity.service.impl.product;

import dev.thesarfo.springsecurity.exception.product.ProductAlreadyExistException;
import dev.thesarfo.springsecurity.model.Product;
import dev.thesarfo.springsecurity.model.dto.request.ProductCreateRequest;
import dev.thesarfo.springsecurity.model.entity.ProductEntity;
import dev.thesarfo.springsecurity.model.mapper.product.ProductCreateRequestToProductEntityMapper;
import dev.thesarfo.springsecurity.model.mapper.product.ProductEntityToProductMapper;
import dev.thesarfo.springsecurity.repository.product.ProductRepository;
import dev.thesarfo.springsecurity.service.product.ProductCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCreateServiceImpl implements ProductCreateService {

    private final ProductRepository productRepository;

    private final ProductCreateRequestToProductEntityMapper productCreateRequestToProductEntityMapper =
            ProductCreateRequestToProductEntityMapper.initialize();

    private final ProductEntityToProductMapper productEntityToProductMapper = ProductEntityToProductMapper.initialize();

    @Override
    public Product createProduct(ProductCreateRequest productCreateRequest) {

        checkUniquenessProductName(productCreateRequest.getName());

        final ProductEntity productEntityToBeSave = productCreateRequestToProductEntityMapper.mapForSaving(productCreateRequest);

        ProductEntity savedProductEntity = productRepository.save(productEntityToBeSave);

        return productEntityToProductMapper.map(savedProductEntity);

    }

    private void checkUniquenessProductName(final String productName) {
        if (productRepository.existsProductEntityByName(productName)) {
            throw new ProductAlreadyExistException("There is another product with given name: " + productName);
        }
    }

}
