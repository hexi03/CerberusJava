package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import com.hexi.Cerberus.domain.report.command.update.*;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.*;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
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
    public final UserRepository userRepository;

    public void updateBy(SupplyRequirementReport supplyRequirementReport, UpdateSupplyRequirementReportCmd cmd) {
        FactorySite factorySite;
        List<WareHouse> targetWareHouses;

        factorySite = supplyRequirementReport.getFactorySite();


        targetWareHouses = wareHouseRepository.findAllById(cmd.getTargetWareHouseIds());
        if (targetWareHouses.isEmpty()) throw new RuntimeException(
                "Error while creating report: " +
                        "There are no warehouses found:"
        );
        List<WareHouse> unregisteredAsSuppliers = targetWareHouses.stream().filter(sup -> !(factorySite.getSuppliers().stream().anyMatch(wareHouseModel -> wareHouseModel.getId().equals(sup.getId())))).collect(Collectors.toList());
        if (!unregisteredAsSuppliers.isEmpty())
            throw new RuntimeException(
                    "Error while creating report: " +
                            String.format("Warehouse %s is not supplier of factory site %s", unregisteredAsSuppliers.get(0).getName().toString(), factorySite.getName().toString()));

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));

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
        supplyRequirementReport.setCreator(creator.get());
        supplyRequirementReport.setTargetWareHouses(targetWareHouses);
        supplyRequirementReport.setRequirements(reqMap);

    }

    public void updateBy(InventarisationReport inventarisationReport, UpdateInventarisationReportCmd cmd) {

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));

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
        inventarisationReport.setCreator(creator.get());
        inventarisationReport.setItems(itMap);
    }

    public void updateBy(ShipmentReport shipmentReport, UpdateShipmentReportCmd cmd) {

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));

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
        shipmentReport.setCreator(creator.get());
        shipmentReport.setItems(itMap);
    }


    public void updateBy(ReleaseReport releaseReport, UpdateReleaseReportCmd cmd) {
        Optional<Report> SRQReport = reportRepository.findById(cmd.getSupplyReqReportId());
        if (!(SRQReport.get() instanceof SupplyRequirementReport))
            throw new RuntimeException("Report id associated with incorrect report type");

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));

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
        releaseReport.setCreator(creator.get());
        releaseReport.setSupplyReqReportId(SRQReport.get());
        releaseReport.setItems(reqMap);
    }

    public void updateBy(ReplenishmentReport replenishmentReport, UpdateReplenishmentReportCmd cmd) {

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));
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
        replenishmentReport.setCreator(creator.get());
        replenishmentReport.setItems(itemMap);
    }


    public void updateBy(WorkShiftReplenishmentReport workShiftReplenishmentReport, UpdateWorkShiftReplenishmentReportCmd cmd) {
        Optional<WorkShiftReport> WHReport = reportRepository.findById(cmd.getWorkShiftReportId());
        if (!(WHReport.get() instanceof WorkShiftReport))
            throw new RuntimeException("Report id associated with incorrect report type");

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));
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
        workShiftReplenishmentReport.setCreator(creator.get());
        workShiftReplenishmentReport.setWorkShiftReport(WHReport.get());
        workShiftReplenishmentReport.setItems(reqMap);

    }

    public void updateBy(WorkShiftReport workShiftReport, UpdateWorkShiftReportCmd cmd) {
        FactorySite factorySite;
        List<WareHouse> targetWareHouses;

        factorySite = workShiftReport.getFactorySite();


        targetWareHouses = wareHouseRepository.findAllById(cmd.getTargetWareHouseIds());
        if (targetWareHouses.isEmpty()) throw new RuntimeException(
                "Error while creating report: " +
                        "There are no warehouses found:"
        );
        List<WareHouse> unregisteredAsSuppliers = targetWareHouses.stream().filter(sup -> !(factorySite.getSuppliers().stream().anyMatch(wareHouseModel -> wareHouseModel.getId().equals(sup.getId())))).collect(Collectors.toList());
        if (!unregisteredAsSuppliers.isEmpty())
            throw new RuntimeException(
                    "Error while creating report: " +
                            String.format("Warehouse %s is not supplier of factory site %s", unregisteredAsSuppliers.get(0).getName().toString(), factorySite.getName().toString()));


        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));

        Map<Product, Integer> producedMap= cmd
                .getProduced()
                .keySet()
                .stream()
                .map(productRepository::findById)
                .filter(Optional::isPresent)
                .map(item -> (Product) item.get())
                .collect(Collectors.toMap(
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



        Map<Item, Integer> unclaimed_remains = cmd
                .getUnclaimedRemains()
                .keySet()
                .stream()
                .map(itemRepository::findById)
                .filter(Optional::isPresent)
                .map(item -> (Item) item.get())
                .collect(Collectors.toMap(
                Function.identity(),
                item -> cmd.getUnclaimedRemains().get(item.getId()),
                Integer::sum
        ));

        workShiftReport.setCreatedAt(new Date());
        workShiftReport.setCreator(creator.get());
        workShiftReport.setProduced(producedMap);
        workShiftReport.setLosses(lossesMap);
        workShiftReport.setRemains(remainsMap);
        workShiftReport.setTargetWareHouses(targetWareHouses);
        workShiftReport.setUnclaimedRemains(unclaimed_remains);

    }
}
