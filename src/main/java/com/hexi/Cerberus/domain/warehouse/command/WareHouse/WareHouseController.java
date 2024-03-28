package com.hexi.Cerberus.domain.warehouse.command.WareHouse;

import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.UserGroupController;
import com.hexi.Cerberus.application.warehouse.service.WareHouseManagementService;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.command.CreateWareHouseCmd;
import com.hexi.Cerberus.domain.warehouse.command.UpdateWareHouseDetailsCmd;
import com.hexi.Cerberus.domain.warehouse.command.UpdateWareHouseSupplyCmd;
import com.hexi.Cerberus.domain.warehouse.command.WareHouse.DTO.WareHouseCreateDTO;
import com.hexi.Cerberus.domain.warehouse.command.WareHouse.DTO.WareHouseDetailsDTO;
import com.hexi.Cerberus.domain.warehouse.command.WareHouse.DTO.WareHouseUpdateDetailsDTO;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/warehouse")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@Slf4j
public class WareHouseController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserGroupController.class);
    WareHouseManagementService wareHouseService;
    DomainToDtoMapper domainToDtoMapper;

    public WareHouseController(WareHouseManagementService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<WareHouseDetailsDTO>> fetch(@RequestParam(required = false) WareHouseID id) {
        logger.debug(id.toString());
        if (id != null) {
            logger.debug("id == null: fetch all");
            Optional<WareHouse> wareHouse = wareHouseService.displayBy(id);
            if(wareHouse.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(new ArrayList<>(List.of(domainToDtoMapper.wareHouseToDetailsDTO(wareHouse.get()))));
        }else{
            logger.debug("id != null: fetch id");
            List<WareHouseDetailsDTO> wareHouses =
                    wareHouseService
                            .displayAllBy()
                            .stream()
                            .map(domainToDtoMapper::wareHouseToDetailsDTO)
                            .collect(Collectors.toList());
            return ResponseEntity.ok(wareHouses);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createWareHouse(@RequestBody WareHouseCreateDTO dto) {
        logger.debug(dto.toString());
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
        logger.debug(dto.toString());
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
        logger.debug(id.toString());
        wareHouseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/updateSupply")
    public ResponseEntity<Void> updateWareHouseSupply(@RequestBody WareHouseUpdateSupplyDTO dto) {
        logger.debug(dto.toString());
        wareHouseService.updateSupply(
                UpdateWareHouseSupplyCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseID(dto.getId())
                        .suppliers(dto.getSuppliers())
                        .build()
        );
        return new ResponseEntity<>(HttpStatus.OK);
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