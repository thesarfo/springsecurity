package dev.thesarfo.springsecurity.model.dto.request.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequest {

    @Size(
            min = 1,
            message = "Product name can't be blank."
    )
    private String name;

    @DecimalMin(
            value = "0.0001",
            message = "Amount must be bigger than 0"
    )
    private BigDecimal amount;

    @DecimalMin(
            value = "0.0001",
            message = "Unit Price must be bigger than 0"
    )
    private BigDecimal unitPrice;

}
