package com.eCommerce.main.controller;

import com.eCommerce.main.dto.ItemDto;
import com.eCommerce.main.dto.ProductPayLoadDto;
import com.eCommerce.main.dto.ProductRequestDto;
import com.eCommerce.main.dto.ProductResponseDto;
import com.eCommerce.main.exceptions.InsufficientStockException;
import com.eCommerce.main.exceptions.ProductNotFoundException;
import com.eCommerce.main.service.interfaces.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ProductResponseDto> saveProduct(
            @RequestBody @Valid ProductRequestDto productRequestDto){
        ProductResponseDto productResponseDto=productService.saveProduct(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        ProductResponseDto response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/payload")
    public ResponseEntity< List<ProductPayLoadDto>> getProductPayLoadById(@RequestBody  List<ItemDto> itemDtos) throws ProductNotFoundException {
        List<ProductPayLoadDto> response = productService.getProductPayLoad(itemDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/updateCancelledProduct")
    public ResponseEntity<List<ProductPayLoadDto>> updateCanceledProduct(
            @RequestBody  List<ItemDto> itemDtos
    ) throws ProductNotFoundException {
        List<ProductPayLoadDto> response = productService.updateCanceledProductQuantity(itemDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponseDto> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(@RequestParam String name) {
        List<ProductResponseDto> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponseDto>> getLowStockProducts(
            @RequestParam @Min(value = 0, message = "Threshold must be positive") Long threshold) {
        List<ProductResponseDto> products = productService.getLowStockProducts(threshold);
        return ResponseEntity.ok(products);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDto productRequestDto) throws ProductNotFoundException {
        ProductResponseDto response = productService.updateProduct(id, productRequestDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<ProductResponseDto> updateProductQuantity(
            @PathVariable Long id,
            @RequestParam @Min(value = 0, message = "Quantity must be positive") Long quantity) throws ProductNotFoundException {
        ProductResponseDto response = productService.updateProductQuantity(id, quantity);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductResponseDto> updateProductPrice(
            @PathVariable Long id,
            @RequestParam @Min(value = 0, message = "Price must be positive") Double price) throws ProductNotFoundException {
        ProductResponseDto response = productService.updateProductPrice(id, price);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkProductAvailability(
            @PathVariable Long id,
            @RequestParam @Min(value = 1, message = "Required quantity must be at least 1") Long quantity) throws ProductNotFoundException {
        boolean isAvailable = productService.isProductAvailable(id, quantity);
        return ResponseEntity.ok(isAvailable);
    }

    @PatchMapping("/{id}/reduce-stock")
    public ResponseEntity<ProductResponseDto> reduceStock(
            @PathVariable Long id,
            @RequestParam @Min(value = 1, message = "Quantity must be at least 1") Long quantity) throws InsufficientStockException, ProductNotFoundException {
        ProductResponseDto response = productService.reduceStock(id, quantity);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/increase-stock")
    public ResponseEntity<ProductResponseDto> increaseStock(
            @PathVariable Long id,
            @RequestParam @Min(value = 1, message = "Quantity must be at least 1") Long quantity) throws ProductNotFoundException {
        ProductResponseDto response = productService.increaseStock(id, quantity);
        return ResponseEntity.ok(response);
    }
}
