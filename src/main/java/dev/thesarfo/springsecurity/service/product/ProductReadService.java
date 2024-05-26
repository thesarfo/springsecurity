package dev.thesarfo.springsecurity.service.product;

import dev.thesarfo.springsecurity.model.Product;
import dev.thesarfo.springsecurity.model.dto.request.product.ProductPagingRequest;
import dev.thesarfo.springsecurity.model.dto.response.CustomPage;

public interface ProductReadService {

    Product getProductById(final String productId);

    CustomPage<Product> getProducts(final ProductPagingRequest productPagingRequest);

}
