package com.eCommerce.main.service.impl;

import com.eCommerce.main.dto.ProductRequestDto;
import com.eCommerce.main.dto.ProductResponseDto;
import com.eCommerce.main.exceptions.InsufficientStockException;
import com.eCommerce.main.exceptions.ProductNotFoundException;
import com.eCommerce.main.model.Product;
import com.eCommerce.main.repository.ProductRepository;
import com.eCommerce.main.service.interfaces.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponseDto saveProduct(ProductRequestDto productRequestDto) {
        Product product=modelMapper.map(productRequestDto,Product.class);
        return modelMapper.map(productRepository.save(product),ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getProductById(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getLowStockProducts(Long threshold) {
        return productRepository.findLowStockProducts(threshold)
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }



    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        modelMapper.map(productRequestDto, product);
        Product updatedProduct = productRepository.save(product);

        return modelMapper.map(updatedProduct, ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto updateProductQuantity(Long id, Long quantity) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setQuantity(quantity);
        Product updatedProduct = productRepository.save(product);

        return modelMapper.map(updatedProduct, ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto updateProductPrice(Long id, Double price) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setPrice(price);
        Product updatedProduct = productRepository.save(product);

        return modelMapper.map(updatedProduct, ProductResponseDto.class);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }

        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean isProductAvailable(Long id, Long requiredQuantity) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        return product.getQuantity() >= requiredQuantity;
    }

    @Override
    public ProductResponseDto reduceStock(Long id, Long quantity) throws ProductNotFoundException, InsufficientStockException {
        synchronized (("PRODUCT_" + id).intern()) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

            if (product.getQuantity() < quantity) {
                throw new InsufficientStockException(
                        "Insufficient stock for product: " + product.getName() +
                                ". Available: " + product.getQuantity() + ", Required: " + quantity
                );
            }

            product.setQuantity(product.getQuantity() - quantity);
            Product updatedProduct = productRepository.save(product);

            return modelMapper.map(updatedProduct, ProductResponseDto.class);
        }
    }

    @Override
    public ProductResponseDto increaseStock(Long id, Long quantity) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setQuantity(product.getQuantity() + quantity);
        Product updatedProduct = productRepository.save(product);

        return modelMapper.map(updatedProduct, ProductResponseDto.class);
    }
}
