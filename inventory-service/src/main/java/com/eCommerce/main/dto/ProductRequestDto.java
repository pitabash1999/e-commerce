package com.eCommerce.main.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Category is required")
    @NotNull(message = "Category is required")
    @Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters")
    private String category;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Max(value = 999999, message = "Quantity cannot exceed 999999")
    private Long quantity;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be zero or positive")
    @DecimalMax(value = "9999999.99", message = "Price cannot exceed 9999999.99")
    @Digits(integer = 7, fraction = 2, message = "Price must have at most 7 digits and 2 decimal places")
    private Double price;

}
