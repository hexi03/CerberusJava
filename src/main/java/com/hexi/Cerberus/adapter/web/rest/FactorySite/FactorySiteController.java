package com.hexi.Cerberus.adapter.web.rest.FactorySite;

import com.hexi.Cerberus.adapter.web.rest.FactorySite.DTO.FactorySiteCreateDTO;
import com.hexi.Cerberus.adapter.web.rest.FactorySite.DTO.FactorySiteDetailsDTO;
import com.hexi.Cerberus.adapter.web.rest.FactorySite.DTO.FactorySiteUpdateDetailsDTO;
import com.hexi.Cerberus.adapter.web.rest.FactorySite.DTO.FactorySiteUpdateSupplyDTO;
import com.hexi.Cerberus.application.factorysite.service.FactorySiteManagementService;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.command.CreateFactorySiteCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteDetailsCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteSupplyCmd;
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
    public final FactorySiteDomainToDtoMapper factorySiteDomainToDtoMapper;


    @GetMapping("/fetch")
    public ResponseEntity<List<FactorySiteDetailsDTO>> fetch(@RequestParam(required = false) FactorySiteID id) {
        log.debug(id.toString());
        if (id != null) {
            log.debug("id == null: fetch all");
            Optional<FactorySite> factorySite = factorySiteService.displayBy(id);
            if (factorySite.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(List.of(factorySiteDomainToDtoMapper.factorySiteToDetailsDTO(factorySite.get())));
        } else {
            log.debug("id != null: fetch id");
            List<FactorySiteDetailsDTO> factorySites =
                    factorySiteService
                            .displayAllBy()
                            .stream()
                            .map(factorySiteDomainToDtoMapper::factorySiteToDetailsDTO)
                            .collect(Collectors.toList());
            return ResponseEntity.ok(factorySites);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createFactorySite(@RequestBody FactorySiteCreateDTO dto) {
        log.debug(dto.toString());
        factorySiteService.create(
                CreateFactorySiteCmd
                        .builder()
                        .id(CommandId.generate())
                        .targetDepartmentId(dto.getDepartmentId())
                        .name(dto.getName())
                        .build()
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFactorySite(@RequestParam FactorySiteID id) {
        log.debug(id.toString());
        factorySiteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/updateSupply")
    public ResponseEntity<Void> updateFactorySiteSupply(@RequestBody FactorySiteUpdateSupplyDTO dto) {
        log.debug(dto.toString());
        factorySiteService.updateSupply(
                UpdateFactorySiteSupplyCmd
                        .builder()
                        .id(CommandId.generate())
                        .factorySiteId(dto.getId())
                        .suppliers(dto.getSuppliers())
                        .build()
        );
        return new ResponseEntity<>(HttpStatus.OK);
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