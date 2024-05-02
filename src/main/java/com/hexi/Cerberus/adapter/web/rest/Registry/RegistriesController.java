package com.hexi.Cerberus.adapter.web.rest.Registry;

import com.hexi.Cerberus.application.product.service.DTO.*;
import com.hexi.Cerberus.application.item.service.DTO.CreateItemDTO;
import com.hexi.Cerberus.application.item.service.DTO.ItemDetailsDTO;
import com.hexi.Cerberus.application.item.service.DTO.UpdateItemDTO;
import com.hexi.Cerberus.application.product.service.ProductManagementService;
import com.hexi.Cerberus.application.item.service.ItemManagementService;
import com.hexi.Cerberus.domain.item.command.UpdateItemCmd;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.product.command.CreateProductCmd;
import com.hexi.Cerberus.domain.product.command.UpdateProductCmd;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.command.CreateItemCmd;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@DrivingAdapter
@RequestMapping("/api/registry")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class RegistriesController {
    public final ItemManagementService itemManagementService;
    public final ProductManagementService productManagementService;


    //USER
    @GetMapping("/fetchItem")
    public ResponseEntity<List<ItemDetailsDTO>> fetchItem(@PathVariable(required = false) ItemID id) {
        if (id != null) {
            return ResponseEntity.ok(itemManagementService
                    .displayAll()
            );
        } else {
            Optional<ItemDetailsDTO> item = itemManagementService.displayBy(id);
            if (item.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    List.of(item.get())
            );
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<ItemID> addItem(CreateItemDTO dto) {
        ItemID id = itemManagementService.create(
                CreateItemCmd
                        .builder()
                        .id(CommandId.generate())
                        .name(dto.getName())

                        .build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);

    }

    @PutMapping("/updateItem")
    public ResponseEntity<Void> updateItem(UpdateItemDTO dto) {
        itemManagementService.updateDetails(
                UpdateItemCmd
                        .builder()
                        .id(CommandId.generate())
                        .itemId(dto.getId())
                        .name(dto.getName())
                        .build()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeItem")
    public ResponseEntity<Void> removeItem(ItemID id) {
        itemManagementService.setDeleted(id);
        return ResponseEntity.ok().build();
    }


    //GROUP
    @GetMapping("/fetchProduct")
    public ResponseEntity<List<ProductDetailsDTO>> fetchProduct(@PathVariable(required = false) ProductID id) {
        if (id != null) {
            return ResponseEntity.ok(productManagementService
                    .displayAll()
            );
        } else {
            Optional<ProductDetailsDTO> item = productManagementService.displayBy(id);
            if (item.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    List.of(item.get())
            );
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ProductID> addProduct(CreateProductDTO dto) {
        ProductID id = productManagementService.create(
                CreateProductCmd
                        .builder()
                        .id(CommandId.generate())
                        .itemId(dto.getProductItemId())
                        .requirements(dto.getRequirements())
                        .build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Void> updateProduct(UpdateProductDTO dto) {
        productManagementService.updateDetails(
                UpdateProductCmd

                        .builder()
                        .id(CommandId.generate())
                        .productId(dto.getId())
                        //.itemId(dto.getProductItemId())
                        .requirements(dto.getRequirements())
                        .build()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<Void> removeProduct(ProductID id) {
        productManagementService.setDeleted(id);
        return ResponseEntity.ok().build();
    }


}