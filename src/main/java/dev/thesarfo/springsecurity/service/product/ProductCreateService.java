package dev.thesarfo.springsecurity.service.product;

import dev.thesarfo.springsecurity.model.Product;
import dev.thesarfo.springsecurity.model.dto.request.ProductCreateRequest;

public interface ProductCreateService {

    Product createProduct(final ProductCreateRequest productCreateRequest);

}

