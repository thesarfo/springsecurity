package dev.thesarfo.springsecurity.model.mapper.product;

import dev.thesarfo.springsecurity.model.dto.request.product.ProductCreateRequest;
import dev.thesarfo.springsecurity.model.entity.product.ProductEntity;
import dev.thesarfo.springsecurity.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCreateRequestToProductEntityMapper extends BaseMapper<ProductCreateRequest, ProductEntity> {


    @Named("mapForSaving")
    default ProductEntity mapForSaving(ProductCreateRequest productCreateRequest) {
        return ProductEntity.builder()
                .amount(productCreateRequest.getAmount())
                .name(productCreateRequest.getName())
                .unitPrice(productCreateRequest.getUnitPrice())
                .build();
    }

    static ProductCreateRequestToProductEntityMapper initialize() {
        return Mappers.getMapper(ProductCreateRequestToProductEntityMapper.class);
    }

}
