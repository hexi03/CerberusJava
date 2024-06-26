package com.hexi.Cerberus.adapter.web.rest.WareHouse;

import com.hexi.Cerberus.application.warehouse.service.AplWareHouseStateService;
import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseCreateDTO;
import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseDetailsDTO;
import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseStateDTO;
import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseUpdateDetailsDTO;
import com.hexi.Cerberus.application.warehouse.service.WareHouseDomainToDtoMapper;
import com.hexi.Cerberus.application.warehouse.service.WareHouseManagementService;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.application.warehouse.service.command.CreateWareHouseCmd;
import com.hexi.Cerberus.application.warehouse.service.command.UpdateWareHouseDetailsCmd;
import com.hexi.Cerberus.domain.warehouse.exception.WareHouseAccessDeniedException;
import com.hexi.Cerberus.domain.warehouse.exception.WareHouseNotFoundException;
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
@RequestMapping("/api/warehouse")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class WareHouseController {

    public final WareHouseManagementService wareHouseService;
    public final WareHouseDomainToDtoMapper wareHouseDomainToDtoMapper;
    public final AplWareHouseStateService wareHouseStateService;

    @GetMapping("/fetch")
    public ResponseEntity<List<WareHouseDetailsDTO>> fetch(@RequestParam(required = false) WareHouseID id) {

        if (id != null) {
            log.debug(id.toString());
            log.debug("id == null: fetch all");
            Optional<WareHouseDetailsDTO> wareHouse = wareHouseService.displayBy(id);
            if (wareHouse.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(List.of(wareHouse.get()));
        } else {
            log.debug("id != null: fetch id");
            List<WareHouseDetailsDTO> wareHouses =
                    wareHouseService
                            .displayAll();
            return ResponseEntity.ok(wareHouses);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<WareHouseID> createWareHouse(@RequestBody WareHouseCreateDTO dto) {
        log.debug(dto.toString());
        WareHouseID id = wareHouseService.create(
                CreateWareHouseCmd
                        .builder()
                        .id(CommandId.generate())
                        .targetDepartmentId(dto.getDepartmentId())
                        .name(dto.getName())
                        .build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteWareHouse(@RequestParam WareHouseID id) {
        log.debug(id.toString());
        wareHouseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getState")
    public ResponseEntity<WareHouseStateDTO> getState(@RequestParam(required = false) WareHouseID id) {
        return ResponseEntity.ok(wareHouseStateService.getWareHouseState(id));

    }

    @ExceptionHandler(WareHouseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleWareHouseNotFoundException(WareHouseNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WareHouseAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleWareHouseAccessDeniedException(WareHouseAccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}