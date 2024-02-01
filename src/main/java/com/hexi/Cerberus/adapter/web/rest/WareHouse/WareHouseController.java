package com.hexi.Cerberus.adapter.web.rest.WareHouse;

import com.hexi.Cerberus.domain.commontypes.WareHouseID;
import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.UserGroupController;
import com.hexi.Cerberus.application.DTO.WareHouseDTO;
import com.hexi.Cerberus.application.WareHouseService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/warehouse")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
public class WareHouseController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserGroupController.class);
    WareHouseService wareHouseService;


    public WareHouseController(WareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<WareHouseDTO>> fetch(@RequestParam(required = false) WareHouseID id) {
        if (id != null) {
            WareHouseDTO wareHouse = wareHouseService.getWareHouseById(id);
            return ResponseEntity.ok(new ArrayList<>(List.of(wareHouse)));
        }else{
            List<WareHouseDTO> wareHouses = wareHouseService.getAllWareHouses();
            return ResponseEntity.ok(wareHouses);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createWareHouse(@RequestBody WareHouseDTO dto) {
        System.err.println(dto);
        wareHouseService.createWareHouse(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateWareHouse(@RequestBody WareHouseDTO dto) {
        System.err.println(dto);
        wareHouseService.updateWareHouse(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteWareHouse(@RequestParam WareHouseID id) {
        System.err.println(id);
        wareHouseService.deleteWareHouse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



//    @ExceptionHandler(FactorySiteNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleWareHouseNotFoundException(FactorySiteNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(FactorySiteAccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ResponseEntity<String> handleWareHouseAccessDeniedException(FactorySiteAccessDeniedException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<String> handleException(Exception ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}