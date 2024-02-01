package com.hexi.Cerberus.domain.services;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.commontypes.FactorySiteID;
import com.hexi.Cerberus.domain.entities.FactorySite;

import java.util.List;

public interface FactorySiteService {
    List<FactorySite> getFactorySites();
    FactorySite getFactorySiteById(FactorySiteID id);
    List<FactorySite> getFactorySiteByDepartmentId(DepartmentID id);
    void createFactorySite(FactorySite factorySite);
    void updateFactorySite(FactorySite department);
    void deleteFactorySite(FactorySiteID id);
}
