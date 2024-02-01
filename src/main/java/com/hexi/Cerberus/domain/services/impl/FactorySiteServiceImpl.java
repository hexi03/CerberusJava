package com.hexi.Cerberus.domain.services.impl;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.commontypes.FactorySiteID;
import com.hexi.Cerberus.domain.exceptions.FactorySiteNotFoundException;
import com.hexi.Cerberus.domain.entities.FactorySite;
import com.hexi.Cerberus.domain.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.repository.FactorySiteRepository;
import com.hexi.Cerberus.domain.services.FactorySiteService;

import java.util.List;
import java.util.UUID;

public class FactorySiteServiceImpl implements FactorySiteService {
    private FactorySiteRepository factorySiteRepository;
    private DepartmentRepository departmentRepository;
    public FactorySiteServiceImpl(FactorySiteRepository factorySiteRepository, DepartmentRepository departmentRepository) {
        this.factorySiteRepository = factorySiteRepository;
        this.departmentRepository = departmentRepository;
    }
    @Override
    public List<FactorySite> getFactorySites() {
        return factorySiteRepository.getAll();
    }

    @Override
    public FactorySite getFactorySiteById(FactorySiteID id) {
        if(factorySiteRepository.isExists(id.getId()))
        return factorySiteRepository.getById(id.getId());
        else
            throw new FactorySiteNotFoundException();
    }

    @Override
    public List<FactorySite> getFactorySiteByDepartmentId(DepartmentID id) {
        if(departmentRepository.isExists(id.getId()))
            return factorySiteRepository.getByDepartmentId(id.getId());
        else
            throw new FactorySiteNotFoundException();

    }

    @Override
    public void createFactorySite(FactorySite factorySite) {
        factorySite.setId(new FactorySiteID(UUID.randomUUID().toString()));
        factorySiteRepository.append(factorySite);
    }

    @Override
    public void updateFactorySite(FactorySite factorySite) {

        if(factorySiteRepository.isExists(factorySite.getId().getId())) {
            factorySite.setDepartmentId(factorySiteRepository.getById(factorySite.getId().getId()).getDepartmentId());
            factorySiteRepository.update(factorySite);
        }else
            throw new FactorySiteNotFoundException();


    }

    @Override
    public void deleteFactorySite(FactorySiteID id)
    {
        if(factorySiteRepository.isExists(id.getId()))
            factorySiteRepository.deleteById(id.getId());
        else
            throw new FactorySiteNotFoundException();

    }
}
