package com.hexi.Cerberus.adapter.web.rest.Department;


import com.hexi.Cerberus.application.department.service.DTO.*;

import com.hexi.Cerberus.application.department.service.DepartmentManagementService;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.command.CreateDepartmentCmd;
import com.hexi.Cerberus.domain.department.command.UpdateDepartmentDetailsCmd;
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
@RequestMapping("/api/department")
//@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class DepartmentController {

    DepartmentManagementService departmentService;

    @GetMapping("/fetch")
    public ResponseEntity<List<DepartmentDetailsDTO>> fetch(@RequestParam(required = false) DepartmentID id) {
        if (id != null) {
            Optional<DepartmentDetailsDTO> department = departmentService.displayBy(id);
            if (department.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(List.of(department.get()));
        } else {
            List<DepartmentDetailsDTO> departments = departmentService.displayAll();
            return ResponseEntity.ok(
                    departments
            );
        }
    }


    @PostMapping("/create")
    public ResponseEntity<DepartmentID> createDepartment(@RequestBody CreateDepartmentDTO dto) {
        log.debug(dto.toString());
        DepartmentID id = departmentService.create(
                CreateDepartmentCmd
                        .builder()
                        .id(CommandId.generate())
                        .name(dto.getName())
                        .build()).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateDepartment(@RequestBody UpdateDepartmentDTO dto) {
        log.debug(dto.toString());
        departmentService.updateDetails(UpdateDepartmentDetailsCmd
                .builder()
                .id(CommandId.generate())
                .departmentId(dto.getId())
                .name(dto.getName())
                .build()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDepartment(@RequestParam DepartmentID id) {
        log.debug(id.toString());
        departmentService.delete(id);
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