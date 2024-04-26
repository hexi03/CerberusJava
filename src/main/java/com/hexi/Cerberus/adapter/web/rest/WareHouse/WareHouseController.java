package com.hexi.Cerberus.adapter.web.rest.WareHouse;

import com.hexi.Cerberus.adapter.web.rest.WareHouse.DTO.WareHouseCreateDTO;
import com.hexi.Cerberus.adapter.web.rest.WareHouse.DTO.WareHouseDetailsDTO;
import com.hexi.Cerberus.adapter.web.rest.WareHouse.DTO.WareHouseUpdateDetailsDTO;
import com.hexi.Cerberus.application.warehouse.service.WareHouseManagementService;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.command.CreateWareHouseCmd;
import com.hexi.Cerberus.domain.warehouse.command.UpdateWareHouseDetailsCmd;

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
@RequestMapping("/api/warehouse")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class WareHouseController {

    public final WareHouseManagementService wareHouseService;
    public final WareHouseDomainToDtoMapper wareHouseDomainToDtoMapper;

    @GetMapping("/fetch")
    public ResponseEntity<List<WareHouseDetailsDTO>> fetch(@RequestParam(required = false) WareHouseID id) {
        log.debug(id.toString());
        if (id != null) {
            log.debug("id == null: fetch all");
            Optional<WareHouse> wareHouse = wareHouseService.displayBy(id);
            if (wareHouse.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(List.of(wareHouseDomainToDtoMapper.wareHouseToDetailsDTO(wareHouse.get())));
        } else {
            log.debug("id != null: fetch id");
            List<WareHouseDetailsDTO> wareHouses =
                    wareHouseService
                            .displayAllBy()
                            .stream()
                            .map(wareHouseDomainToDtoMapper::wareHouseToDetailsDTO)
                            .collect(Collectors.toList());
            return ResponseEntity.ok(wareHouses);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createWareHouse(@RequestBody WareHouseCreateDTO dto) {
        log.debug(dto.toString());
        wareHouseService.create(
                CreateWareHouseCmd
                        .builder()
                        .id(CommandId.generate())
                        .targetDepartmentId(dto.getDepartmentId())
                        .name(dto.getName())
                        .build()
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateWareHouseDetails(@RequestBody WareHouseUpdateDetailsDTO dto) {
        log.debug(dto.toString());
        wareHouseService.updateDetails(
                UpdateWareHouseDetailsCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseId(dto.getId())
                        .name(dto.getName())
                        .build()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteWareHouse(@RequestParam WareHouseID id) {
        log.debug(id.toString());
        wareHouseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


//    @ExceptionHandler(WareHouseNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleWareHouseNotFoundException(WareHouseNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(WareHouseAccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ResponseEntity<String> handleWareHouseAccessDeniedException(WareHouseAccessDeniedException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<String> handleException(Exception ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}