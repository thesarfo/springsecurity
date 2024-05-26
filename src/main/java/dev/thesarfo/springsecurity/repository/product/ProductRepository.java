package dev.thesarfo.springsecurity.repository.product;

import dev.thesarfo.springsecurity.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    boolean existsProductEntityByName(final String name);

}
