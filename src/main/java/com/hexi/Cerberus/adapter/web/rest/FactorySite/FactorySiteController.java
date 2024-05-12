package com.hexi.Cerberus.adapter.web.rest.FactorySite;

import com.hexi.Cerberus.application.factorysite.service.AplFactorySiteStateService;
import com.hexi.Cerberus.application.factorysite.service.DTO.*;
import com.hexi.Cerberus.application.factorysite.service.FactorySiteDomainToDtoMapper;
import com.hexi.Cerberus.application.factorysite.service.FactorySiteManagementService;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.command.CreateFactorySiteCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteDetailsCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteSupplyCmd;
import com.hexi.Cerberus.domain.service.FactorySiteStateService;
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
@RequestMapping("/api/factorysite")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class FactorySiteController {

    public final FactorySiteManagementService factorySiteService;
    public final AplFactorySiteStateService factorySiteStateService;


    @GetMapping("/fetch")
    public ResponseEntity<List<FactorySiteDetailsDTO>> fetch(@RequestParam(required = false) FactorySiteID id) {

        if (id != null) {
            log.debug(id.toString());
            log.debug("id == null: fetch all");
            Optional<FactorySiteDetailsDTO> factorySite = factorySiteService.displayBy(id);
            if (factorySite.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(List.of(factorySite.get()));
        } else {
            log.debug("id != null: fetch id");
            List<FactorySiteDetailsDTO> factorySites =
                    factorySiteService
                            .displayAll();

            return ResponseEntity.ok(factorySites);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<FactorySiteID> createFactorySite(@RequestBody FactorySiteCreateDTO dto) {
        log.debug(dto.toString());
        FactorySiteID id = factorySiteService.create(
                CreateFactorySiteCmd
                        .builder()
                        .id(CommandId.generate())
                        .targetDepartmentId(dto.getDepartmentId())
                        .name(dto.getName())
                        .build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateFactorySiteDetails(@RequestBody FactorySiteUpdateDetailsDTO dto) {
        log.debug(dto.toString());
        factorySiteService.updateDetails(
                UpdateFactorySiteDetailsCmd
                        .builder()
                        .id(CommandId.generate())
                        .factorySiteId(dto.getId())
                        .name(dto.getName())
                        .build()
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFactorySite(@RequestParam FactorySiteID id) {
        log.debug(id.toString());
        factorySiteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateSupply")
    public ResponseEntity<Void> updateFactorySiteSupply(@RequestBody FactorySiteUpdateSupplyDTO dto) {
        log.info(dto.toString());
        try {
            factorySiteService.updateSupply(
                    UpdateFactorySiteSupplyCmd
                            .builder()
                            .id(CommandId.generate())
                            .factorySiteId(dto.getId())
                            .suppliers(dto.getSuppliers())
                            .build()
            );
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getState")
    public ResponseEntity<FactorySiteStateDTO> getState(@RequestParam(required = false) FactorySiteID id) {


            return ResponseEntity.ok(factorySiteStateService.getFactorySiteState(id));

    }


//    @ExceptionHandler(FactorySiteNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleFactorySiteNotFoundException(FactorySiteNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(FactorySiteAccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ResponseEntity<String> handleFactorySiteAccessDeniedException(FactorySiteAccessDeniedException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<String> handleException(Exception ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}