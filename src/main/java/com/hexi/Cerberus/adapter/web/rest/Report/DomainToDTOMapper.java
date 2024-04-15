package com.hexi.Cerberus.adapter.web.rest.Report;

import com.hexi.Cerberus.adapter.web.rest.Report.DTO.details.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface DomainToDTOMapper {

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
        return switch (report) {
            case InventarisationReport report1 -> DetailsInventarisationReportDTO
                    .builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouseId())
                    .items(
                            toItemIdMap(report1.getItems())
                    )
                    .build();
            case ReleaseReport report1 -> DetailsReleaseReportDTO
                    .builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouseId())
                    .items(
                            toItemIdMap(report1.getItems())
                    )
                    .build();
            case ReplenishmentReport report1 -> DetailsReplenishmentReportDTO
                    .builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouseId())
                    .items(
                            toItemIdMap(report1.getItems())
                    )
                    .build();

            case ShipmentReport report1 -> DetailsShipmentReportDTO
                    .builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouseId())
                    .items(
                            toItemIdMap(report1.getItems())
                    )
                    .build();
            case WorkShiftReplenishmentReport report1 -> DetailsReplenishmentReportDTO
                    .builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .wareHouseId(report1.getWareHouseId())
                    .items(
                            toItemIdMap(report1.getItems())
                    )
                    .build();

            case WorkShiftReport report1 -> DetailsWorkShiftReportDTO
                    .builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .factorySiteId(report1.getFactorySiteId())
                    .produced(
                            toProductItemIdMap(report1.getProduced())
                    )
                    .remains(
                            toItemIdMap(report1.getRemains())
                    )
                    .losses(
                            toItemIdMap(report1.getLosses())
                    )
                    .build();
            case SupplyRequirementReport report1 -> DetailsSupplyRequirementReportDTO
                    .builder()
                    .id(report1.getId())
                    .createdAt(report1.getCreatedAt())
                    .deletedAt(report1.getDeletedAt())
                    .factorySiteID(report1.getFactorySiteId())
                    .items(
                            toItemIdMap(report1.getItems())
                    )
                    .build();

            default -> throw new IllegalStateException("Unexpected value: " + report);
        };
    }

}
