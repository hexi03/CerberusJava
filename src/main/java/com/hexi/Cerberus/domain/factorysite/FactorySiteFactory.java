package com.hexi.Cerberus.domain.factorysite;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.factorysite.command.CreateFactorySiteCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface FactorySiteFactory {
    FactorySite from(CreateFactorySiteCmd cmd);
}
