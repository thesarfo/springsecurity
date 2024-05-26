package dev.thesarfo.springsecurity.model.mapper.product;

import dev.thesarfo.springsecurity.model.Product;
import dev.thesarfo.springsecurity.model.entity.ProductEntity;
import dev.thesarfo.springsecurity.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductEntityToProductMapper extends BaseMapper<ProductEntity, Product> {

    @Override
    Product map(ProductEntity source);

    static ProductEntityToProductMapper initialize() {
        return Mappers.getMapper(ProductEntityToProductMapper.class);
    }

}
