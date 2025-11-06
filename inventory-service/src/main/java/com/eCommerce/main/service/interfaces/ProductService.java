package com.eCommerce.main.service.interfaces;

import com.eCommerce.main.dto.ItemDto;
import com.eCommerce.main.dto.ProductPayLoadDto;
import com.eCommerce.main.dto.ProductRequestDto;
import com.eCommerce.main.dto.ProductResponseDto;
import com.eCommerce.main.exceptions.InsufficientStockException;
import com.eCommerce.main.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {

    // Create
    ProductResponseDto saveProduct(ProductRequestDto productRequestDto);

    // Read
    List<ProductPayLoadDto> getProductPayLoad(List<ItemDto> itemDtos)throws ProductNotFoundException;
    ProductResponseDto getProductById(Long id) throws ProductNotFoundException;
    List<ProductResponseDto> getAllProducts();
    List<ProductResponseDto> getProductsByCategory(String category);
    List<ProductResponseDto> searchProductsByName(String name);
    List<ProductResponseDto> getLowStockProducts(Long threshold);

    // Update
    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) throws ProductNotFoundException;
    ProductResponseDto updateProductQuantity(Long id, Long quantity) throws ProductNotFoundException;
    ProductResponseDto updateProductPrice(Long id, Double price) throws ProductNotFoundException;
    List<ProductPayLoadDto> updateCanceledProductQuantity(List<ItemDto> itemDtos)throws ProductNotFoundException;
    // Delete
    void deleteProduct(Long id) throws ProductNotFoundException;

    // Business Logic
    boolean isProductAvailable(Long id, Long requiredQuantity) throws ProductNotFoundException;
    ProductResponseDto reduceStock(Long id, Long quantity) throws ProductNotFoundException, InsufficientStockException;
    ProductResponseDto increaseStock(Long id, Long quantity) throws ProductNotFoundException;

}
