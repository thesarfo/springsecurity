package dev.thesarfo.springsecurity.service.product;

import dev.thesarfo.springsecurity.model.Product;
import dev.thesarfo.springsecurity.model.dto.request.product.ProductUpdateRequest;

public interface ProductUpdateService {

    Product updateProductById(final String productId, final ProductUpdateRequest productUpdateRequest);

}

