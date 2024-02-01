package com.hexi.Cerberus.adapter.web.rest.FactorySite;

import com.hexi.Cerberus.domain.commontypes.FactorySiteID;
import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.UserGroupController;
import com.hexi.Cerberus.application.DTO.FactorySiteDTO;
import com.hexi.Cerberus.application.FactorySiteService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/factorysite")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
public class FactorySiteController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserGroupController.class);
    FactorySiteService factorySiteService;


    public FactorySiteController(FactorySiteService factorySiteService) {
        this.factorySiteService = factorySiteService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<FactorySiteDTO>> fetch(@RequestParam(required = false) FactorySiteID id) {
        if (id != null) {
            FactorySiteDTO factorySite = factorySiteService.getFactorySiteById(id);
            return ResponseEntity.ok(new ArrayList<>(List.of(factorySite)));
        }else{
            List<FactorySiteDTO> factorySites = factorySiteService.getAllFactorySites();
            return ResponseEntity.ok(factorySites);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createFactorySite(@RequestBody FactorySiteDTO dto) {
        System.err.println(dto);
        factorySiteService.createFactorySite(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateFactorySite(@RequestBody FactorySiteDTO dto) {
        factorySiteService.updateFactorySite(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFactorySite(@RequestParam FactorySiteID id) {
        System.err.println(id);
        factorySiteService.deleteFactorySite(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/updateSupply")
    public ResponseEntity<Void> updateFactorySiteSupply(@RequestBody FactorySiteSupplyDTO dto) {
        System.err.println(dto);
        factorySiteService.updateFactorySiteSupply(dto);
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