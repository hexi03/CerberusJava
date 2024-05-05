package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import com.hexi.Cerberus.domain.report.command.update.*;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.*;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ReportModifier {
    public final WareHouseRepository wareHouseRepository;
    public final ItemRepository itemRepository;
    public final ProductRepository productRepository;
    public final ReportRepository reportRepository;

    public void updateBy(SupplyRequirementReport supplyRequirementReport, UpdateSupplyRequirementReportCmd cmd) {
        Optional<WareHouse> targetWareHouse = wareHouseRepository.findById(cmd.getTargetWareHouseId());

        List<Item> reqs =
                cmd
                        .getItems()
                        .keySet()
                        .stream()
                        .map(itemRepository::findById)
                        .filter(Optional::isPresent)
                        .map(item -> (Item) item.get())
                        .toList();
        Map<Item, Integer> reqMap = reqs.stream().collect(Collectors.toMap(
                Function.identity(),
                item -> cmd.getItems().get(item.getId()),
                Integer::sum
        ));

        supplyRequirementReport.setCreatedAt(new Date());
        supplyRequirementReport.setTargetWareHouse(targetWareHouse.get());
        supplyRequirementReport.setRequirements(reqMap);

    }

    public void updateBy(InventarisationReport inventarisationReport, UpdateInventarisationReportCmd cmd) {

        List<Item> items =
                cmd
                        .getItems()
                        .keySet()
                        .stream()
                        .map(itemRepository::findById)
                        .filter(Optional::isPresent)
                        .map(item -> (Item) item.get())
                        .toList();
        Map<Item, Integer> itMap =
                items
                        .stream()
                        .collect(Collectors.toMap(
                                        Function.identity(),
                                        item -> cmd.getItems().get(item.getId()),
                                        Integer::sum
                                )
                        );

        inventarisationReport.setCreatedAt(new Date());

        inventarisationReport.setItems(itMap);
    }

    public void updateBy(ShipmentReport shipmentReport, UpdateShipmentReportCmd cmd) {
        List<Item> items =
                cmd
                        .getItems()
                        .keySet()
                        .stream()
                        .map(itemRepository::findById)
                        .filter(Optional::isPresent)
                        .map(item -> (Item) item.get())
                        .toList();
        Map<Item, Integer> itMap = items.stream().collect(Collectors.toMap(
                Function.identity(),
                item -> cmd.getItems().get(item.getId()),
                Integer::sum
        ));

        shipmentReport.setCreatedAt(new Date());

        shipmentReport.setItems(itMap);
    }


    public void updateBy(ReleaseReport releaseReport, UpdateReleaseReportCmd cmd) {
        Optional<Report> SRQReport = reportRepository.findById(cmd.getSupplyReqReportId());
        if (!(SRQReport.get() instanceof SupplyRequirementReport))
            throw new RuntimeException("Report id associated with incorrect report type");

        List<Item> reqs =
                cmd
                        .getItems()
                        .keySet()
                        .stream()
                        .map(itemRepository::findById)
                        .filter(Optional::isPresent)
                        .map(item -> (Item) item.get())
                        .toList();
        Map<Item, Integer> reqMap = reqs.stream().collect(Collectors.toMap(
                Function.identity(),
                item -> cmd.getItems().get(item.getId()),
                Integer::sum
        ));

        releaseReport.setCreatedAt(new Date());
        releaseReport.setSupplyReqReportId(SRQReport.get());
        releaseReport.setItems(reqMap);
    }

    public void updateBy(ReplenishmentReport replenishmentReport, UpdateReplenishmentReportCmd cmd) {

        List<Item> items = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemRepository::findById)
                .filter(Optional::isPresent)
                .map(item -> (Item) item.get())
                .toList();
        Map<Item, Integer> itemMap = items.stream().collect(Collectors.toMap(
                Function.identity(),
                item -> cmd.getItems().get(item.getId()),
                Integer::sum
        ));
        replenishmentReport.setCreatedAt(new Date());
        replenishmentReport.setItems(itemMap);
    }


    public void updateBy(WorkShiftReplenishmentReport workShiftReplenishmentReport, UpdateWorkShiftReplenishmentReportCmd cmd) {
        Optional<WorkShiftReport> WHReport = reportRepository.findById(cmd.getWorkShiftReportId());
        if (!(WHReport.get() instanceof WorkShiftReport))
            throw new RuntimeException("Report id associated with incorrect report type");

        List<Item> reqs = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemRepository::findById)
                .filter(Optional::isPresent)
                .map(item -> (Item) item.get())
                .toList();
        Map<Item, Integer> reqMap = reqs.stream().collect(Collectors.toMap(
                Function.identity(),
                item -> cmd.getItems().get(item.getId()),
                Integer::sum
        ));

        workShiftReplenishmentReport.setCreatedAt(new Date());
        workShiftReplenishmentReport.setWorkShiftReport(WHReport.get());
        workShiftReplenishmentReport.setItems(reqMap);

    }

    public void updateBy(WorkShiftReport workShiftReport, UpdateWorkShiftReportCmd cmd) {
        Optional<WareHouse> targetWareHouse = wareHouseRepository.findById(cmd.getTargetWareHouseId());

        List<Product> produced = cmd
                .getProduced()
                .keySet()
                .stream()
                .map(productRepository::findById)
                .filter(Optional::isPresent)
                .map(item -> (Product) item.get())
                .toList();
        Map<Product, Integer> producedMap = produced.stream().collect(Collectors.toMap(
                Function.identity(),
                item -> cmd.getProduced().get(item.getId()),
                Integer::sum
        ));


        List<Item> losses = cmd
                .getLosses()
                .keySet()
                .stream()
                .map(itemRepository::findById)
                .filter(Optional::isPresent)
                .map(item -> (Item) item.get())
                .toList();
        Map<Item, Integer> lossesMap = losses.stream().collect(Collectors.toMap(
                Function.identity(),
                item -> cmd.getLosses().get(item.getId()),
                Integer::sum
        ));

        List<Item> remains = cmd
                .getRemains()
                .keySet()
                .stream()
                .map(itemRepository::findById)
                .filter(Optional::isPresent)
                .map(item -> (Item) item.get())
                .toList();
        Map<Item, Integer> remainsMap = remains.stream().collect(Collectors.toMap(
                Function.identity(),
                item -> cmd.getRemains().get(item.getId()),
                Integer::sum
        ));

        workShiftReport.setCreatedAt(new Date());
        workShiftReport.setProduced(producedMap);
        workShiftReport.setLosses(lossesMap);
        workShiftReport.setRemains(remainsMap);
        workShiftReport.setTargetWareHouse(targetWareHouse.get());

    }
}
