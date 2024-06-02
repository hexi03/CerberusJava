package com.hexi.Cerberus.application.department.service.impl;

import com.hexi.Cerberus.application.department.service.DTO.DepartmentDetailsDTO;
import com.hexi.Cerberus.application.department.service.DepartmentDomainToDtoMapper;
import com.hexi.Cerberus.application.department.service.DepartmentManagementService;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentFactory;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.DepartmentUpdater;
import com.hexi.Cerberus.application.department.service.command.CreateDepartmentCmd;
import com.hexi.Cerberus.application.department.service.command.UpdateDepartmentDetailsCmd;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class AplDepartmentManagementServiceImpl implements DepartmentManagementService {
    public final DepartmentRepository departmentRepository;
    public final MutableAclService aclService;
    public final MessagePublisher messagePublisher;
    public final DepartmentFactory departmentFactory;
    public final DepartmentUpdater departmentUpdater;
    public final DepartmentDomainToDtoMapper departmentDomainToDtoMapper;

    @Override
    //@PostAuthorize("returnObject.orElse(null) == null or hasPermission(returnObject.get(), 'READ')")
    public Optional<DepartmentDetailsDTO> displayBy(DepartmentID id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(departmentDomainToDtoMapper::departmentToDetailsDTO);
    }

    @Override
    //@PostFilter("hasPermission(returnObject, 'READ')")
    public List<DepartmentDetailsDTO> displayAllBy(Query query) {
        return ((List<Department>)departmentRepository.findAllWithQuery(query)).stream()
                .map(departmentDomainToDtoMapper::departmentToDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    //@PostFilter("hasPermission(returnObject, 'READ')")
    public List<DepartmentDetailsDTO> displayAll() {
        return ((List<Department>)departmentRepository.findAll()).stream()
                .map(departmentDomainToDtoMapper::departmentToDetailsDTO)
                .collect(Collectors.toList());

    }


    @Override
    public DepartmentDetailsDTO create(CreateDepartmentCmd cmd) {
        cmd.validate().onFailedThrow();
        Department department = departmentFactory.from(cmd);

        departmentRepository.append(department);
        department.initAcl(aclService);
        messagePublisher.publish(department.edjectEvents());
        return departmentDomainToDtoMapper.departmentToDetailsDTO(department);

    }

    @Override
    public void updateDetails(UpdateDepartmentDetailsCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<Department> department = departmentRepository.findById(cmd.getDepartmentId());
        department.orElseThrow(() -> new RuntimeException(String.format("There are no department with id %s", cmd.getDepartmentId().toString())));
        departmentUpdater.updateBy(department.get(), cmd);
        departmentRepository.update(department.get());
        messagePublisher.publish(department.get().edjectEvents());
    }

    @Override
    public void delete(DepartmentID id) {
        departmentRepository.deleteById(id);
    }
}
