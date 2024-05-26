package dev.thesarfo.springsecurity.model.mapper.product;

import dev.thesarfo.springsecurity.model.dto.request.ProductUpdateRequest;
import dev.thesarfo.springsecurity.model.entity.ProductEntity;
import dev.thesarfo.springsecurity.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductUpdateRequestToProductEntityMapper extends BaseMapper<ProductUpdateRequest, ProductEntity> {


    @Named("mapForUpdating")
    default void mapForUpdating(ProductEntity productEntityToBeUpdate, ProductUpdateRequest productUpdateRequest) {
        productEntityToBeUpdate.setName(productUpdateRequest.getName());
        productEntityToBeUpdate.setAmount(productUpdateRequest.getAmount());
        productEntityToBeUpdate.setUnitPrice(productUpdateRequest.getUnitPrice());
    }

    static ProductUpdateRequestToProductEntityMapper initialize() {
        return Mappers.getMapper(ProductUpdateRequestToProductEntityMapper.class);
    }

}
