package com.hexi.Cerberus.application.report.service;

import com.hexi.Cerberus.application.report.service.DTO.details.*;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
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

    default Map<ProductID, Integer> toProductItemIdMap(Map<Product, Integer> items) {
        return items.entrySet()
                .stream()
                .map(itemIntegerEntry ->
                        new AbstractMap.SimpleEntry(
                                itemIntegerEntry.getKey().getId(), //getProduction() deleted
                                itemIntegerEntry.getValue()
                        )
                ).collect(Collectors.toMap(o -> (ProductID) o.getKey(), o -> (Integer) o.getValue()));
    }

    default ReportDetails toReportDetails(Report report) {
        if (report instanceof InventarisationReport report1) {
            return DetailsInventarisationReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .creatorId(report1.getCreator().getId())
                    .wareHouseId(report1.getWareHouse().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .build();
        } else if (report instanceof ReleaseReport report1) {
            return DetailsReleaseReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .creatorId(report1.getCreator().getId())
                    .wareHouseId(report1.getWareHouse().getId())
                    .supplyReqReportId(report1.getSupplyReqReport().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .build();
        } else if (report instanceof ReplenishmentReport report1 ) {
            return DetailsReplenishmentReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .creatorId(report1.getCreator().getId())
                    .wareHouseId(report1.getWareHouse().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .build();
        } else if ( report instanceof WorkShiftReplenishmentReport report1) {
            return DetailsWorkShiftReplenishmentReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .creatorId(report1.getCreator().getId())
                    .wareHouseId(report1.getWareHouse().getId())
                    .workShiftReportId(report1.getWorkShiftReport().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .unclaimedRemains(toItemIdMap(report1.getUnclaimedRemains()))
                    .build();
        } else if (report instanceof ShipmentReport report1) {
            return DetailsShipmentReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .creatorId(report1.getCreator().getId())
                    .wareHouseId(report1.getWareHouse().getId())
                    .items(toItemIdMap(report1.getItems()))
                    .build();
        } else if (report instanceof WorkShiftReport report1) {
            return DetailsWorkShiftReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .creatorId(report1.getCreator().getId())
                    .factorySiteId(report1.getFactorySite().getId())
                    .targetWareHouseIds(report1.getTargetWareHouses().stream().map(wareHouse -> wareHouse.getId()).collect(Collectors.toList()))
                    .produced(toProductItemIdMap(report1.getProduced()))
                    .remains(toItemIdMap(report1.getRemains()))
                    .losses(toItemIdMap(report1.getLosses()))
                    .build();
        } else if (report instanceof SupplyRequirementReport report1) {
            return DetailsSupplyRequirementReportDTO.builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .creatorId(report1.getCreator().getId())
                    .factorySiteId(report1.getFactorySite().getId())
                    .targetWareHouseIds(report1.getTargetWareHouses().stream().map(wareHouse -> wareHouse.getId()).collect(Collectors.toList()))
                    .items(toItemIdMap(report1.getRequirements()))
                    .build();
        } else {
            throw new IllegalStateException("Unexpected value: " + report);
        }
    }

}
