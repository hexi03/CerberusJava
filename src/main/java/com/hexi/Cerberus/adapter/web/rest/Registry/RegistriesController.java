package com.hexi.Cerberus.adapter.web.rest.Registry;

import com.hexi.Cerberus.application.item.service.DTO.CreateItemDTO;
import com.hexi.Cerberus.application.item.service.DTO.ItemDetailsDTO;
import com.hexi.Cerberus.application.item.service.DTO.UpdateItemDTO;
import com.hexi.Cerberus.application.item.service.ItemManagementService;
import com.hexi.Cerberus.application.product.service.DTO.CreateProductDTO;
import com.hexi.Cerberus.application.product.service.DTO.ProductDetailsDTO;
import com.hexi.Cerberus.application.product.service.DTO.UpdateProductDTO;
import com.hexi.Cerberus.application.product.service.ProductManagementService;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.application.item.service.command.CreateItemCmd;
import com.hexi.Cerberus.application.item.service.command.UpdateItemCmd;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.application.product.service.command.CreateProductCmd;
import com.hexi.Cerberus.application.product.service.command.UpdateProductCmd;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
    public ResponseEntity<List<ItemDetailsDTO>> fetchItem(@RequestParam(required = false) ItemID id) {
        if (id == null) {
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
    public ResponseEntity<ItemID> addItem(@RequestBody CreateItemDTO dto) {
        log.info(dto.toString());
        ItemID id = itemManagementService.create(
                CreateItemCmd
                        .builder()
                        .id(CommandId.generate())
                        .name(dto.getName())
                        .units(dto.getUnits())
                        .build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);

    }

    @PutMapping("/updateItem")
    public ResponseEntity<Void> updateItem(@RequestBody UpdateItemDTO dto) {
        itemManagementService.updateDetails(
                UpdateItemCmd
                        .builder()
                        .id(CommandId.generate())
                        .itemId(dto.getId())
                        .name(dto.getName())
                        .units(dto.getUnits())
                        .build()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/removeItem")
    public ResponseEntity<Void> removeItem(@RequestParam ItemID id) {
        itemManagementService.setDeleted(id);
        return ResponseEntity.ok().build();
    }


    //GROUP
    @GetMapping("/fetchProduct")
    public ResponseEntity<List<ProductDetailsDTO>> fetchProduct(@RequestParam(required = false) ProductID id) {
        if (id == null) {
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
    public ResponseEntity<ProductID> addProduct(@RequestBody CreateProductDTO dto) {
        log.info(dto.toString());
        ProductID id = productManagementService.create(
                CreateProductCmd
                        .builder()
                        .id(CommandId.generate())
                        .itemId(dto.getProducedItemId())
                        .requirements(dto.getRequirements())
                        .build()
        ).getId();
        log.info(id.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Void> updateProduct(@RequestBody UpdateProductDTO dto) {
        productManagementService.updateDetails(
                UpdateProductCmd

                        .builder()
                        .id(CommandId.generate())
                        .productId(dto.getId())
                        //.itemId(dto.getProductItemId())
                        .requirements(dto.getRequirements())
                        .build()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<Void> removeProduct(@RequestParam ProductID id) {
        productManagementService.setDeleted(id);
        return ResponseEntity.ok().build();
    }


}