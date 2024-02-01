package com.hexi.Cerberus.adapter.web.rest.Department;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.UserGroupController;
import com.hexi.Cerberus.application.DTO.DepartmentDTO;
import com.hexi.Cerberus.application.DepartmentService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/department")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
public class DepartmentController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserGroupController.class);
    DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<DepartmentDTO>> fetch(@RequestParam(required = false) DepartmentID id) {
        if (id != null) {
            DepartmentDTO department = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(new ArrayList<>(List.of(department)));
        }else{
            List<DepartmentDTO> departments = departmentService.getAllDepartments();
            return ResponseEntity.ok(departments);
        }
    }
    

    @PostMapping("/create")
    public ResponseEntity<Void> createDepartment(@RequestBody DepartmentDTO dto) {
        System.err.println(dto);
        departmentService.createDepartment(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateDepartment(@RequestBody DepartmentDTO dto) {
        System.err.println(dto);
        departmentService.updateDepartment(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDepartment(@RequestParam DepartmentID id) {
        System.err.println(id);
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



//    @ExceptionHandler(FactorySiteNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleDepartmentNotFoundException(FactorySiteNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(FactorySiteAccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ResponseEntity<String> handleDepartmentAccessDeniedException(FactorySiteAccessDeniedException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<String> handleException(Exception ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}