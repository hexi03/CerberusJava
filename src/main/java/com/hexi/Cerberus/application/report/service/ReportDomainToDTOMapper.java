package com.hexi.Cerberus.application.report.service;

import com.hexi.Cerberus.application.report.service.DTO.details.*;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.warehouse.*;
import org.mapstruct.Mapper;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReportDomainToDTOMapper {

    List<ReportDetails> toReportDetails(List<Report> reports);

    default Map<ItemID, Integer> toItemIdMap(Map<Item, Integer> items) {
        return items.entrySet()
                .stream()
                .map(itemIntegerEntry ->
                        new AbstractMap.SimpleEntry(
                                itemIntegerEntry.getKey().getId(),
                                itemIntegerEntry.getValue()
                        )
                ).collect(Collectors.toMap(o -> (ItemID) o.getKey(), o -> (Integer) o.getValue()));
    }

    default Map<ItemID, Integer> toProductItemIdMap(Map<Product, Integer> items) {
        return items.entrySet()
                .stream()
                .map(itemIntegerEntry ->
                        new AbstractMap.SimpleEntry(
                                itemIntegerEntry.getKey().getProduction().getId(),
                                itemIntegerEntry.getValue()
                        )
                ).collect(Collectors.toMap(o -> (ItemID) o.getKey(), o -> (Integer) o.getValue()));
    }

    default ReportDetails toReportDetails(Report report) {
        if (report instanceof InventarisationReport report1) {
            return DetailsInventarisationReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouse().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .build();
        } else if (report instanceof ReleaseReport report1) {
            return DetailsReleaseReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouse().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .build();
        } else if (report instanceof ReplenishmentReport report1 ) {
            return DetailsReplenishmentReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouse().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .build();
        } else if ( report instanceof WorkShiftReplenishmentReport report1) {
            return DetailsWorkShiftReplenishmentReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouse().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .unclaimedRemains(toItemIdMap(report1.getUnclaimedRemains()))
                    .build();
        } else if (report instanceof ShipmentReport report1) {
            return DetailsShipmentReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouse().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .build();
        } else if (report instanceof WorkShiftReport report1) {
            return DetailsWorkShiftReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .factorySiteId(report1.getFactorySite().getId())
                    .targetWareHouseId(report1.getTargetWareHouse().getId())
                    .produced(toProductItemIdMap(report1.getProduced()))
                    .remains(toItemIdMap(report1.getRemains()))
                    .losses(toItemIdMap(report1.getLosses()))
                    .build();
        } else if (report instanceof SupplyRequirementReport report1) {
            return DetailsSupplyRequirementReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .factorySiteID(report1.getFactorySite().getId())
                    .targetWareHouseId(report1.getTargetWareHouse().getId())
                    .items(toItemIdMap(report1.getRequirements()))
                    .build();
        } else {
            throw new IllegalStateException("Unexpected value: " + report);
        }
    }

}
