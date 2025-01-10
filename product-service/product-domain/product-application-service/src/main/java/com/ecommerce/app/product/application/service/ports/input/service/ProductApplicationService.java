package com.ecommerce.app.product.application.service.ports.input.service;

import com.ecommerce.app.product.application.service.dto.create.*;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductApplicationService {
    CreateProductResponse createProduct(@Valid CreateProductCommand command);
    List<ListProductResponse> findAllProducts();
    DetailProductResponse findProductById(ProductIdQuery productIdQuery);
}
