package com.eCcommerce.main.external;

import com.eCcommerce.main.config.FeignConfig;
import com.eCcommerce.main.dto.ItemDto;
import com.eCcommerce.main.model.Item;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "INVENTORY-SERVICE",configuration = FeignConfig.class)
public interface ProductService {

    @PostMapping("/api/products/payload")
    public List<Item> getItemsDetails(@RequestBody List<ItemDto> itemDtos);
    @PutMapping("/api/products/updateCancelledProduct")
    public List<Item> increaseStock(@RequestBody List<ItemDto> itemDtos);


}
