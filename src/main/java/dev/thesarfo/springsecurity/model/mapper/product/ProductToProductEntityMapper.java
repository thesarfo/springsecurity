package dev.thesarfo.springsecurity.model.mapper.product;

import dev.thesarfo.springsecurity.model.Product;
import dev.thesarfo.springsecurity.model.entity.ProductEntity;
import dev.thesarfo.springsecurity.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductToProductEntityMapper extends BaseMapper<Product, ProductEntity> {

    @Override
    ProductEntity map(Product source);

    static ProductToProductEntityMapper initialize() {
        return Mappers.getMapper(ProductToProductEntityMapper.class);
    }

}
