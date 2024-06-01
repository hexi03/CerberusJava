package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;

import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.config.CerberusParameters;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.helpers.ItemMapHelper;
import com.hexi.Cerberus.domain.helpers.ProductMapHelper;
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

import java.util.*;
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

        Map<Item, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
            items_map =
                    ItemMapHelper.filterPos(cmd.getItems())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getItems().get(item.getId()),
                                    Integer::sum
                            ));
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        supplyRequirementReport.setCreatedAt(createdAt);
        supplyRequirementReport.setExpirationDate(new Date(createdAt.getTime() + CerberusParameters.expirationDuration));
        supplyRequirementReport.setCreator(creator.get());
        supplyRequirementReport.setTargetWareHouses(targetWareHouses);
        supplyRequirementReport.setRequirements(items_map);

    }

    public void updateBy(InventarisationReport inventarisationReport, UpdateInventarisationReportCmd cmd) {

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));

        Map<Item, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
            items_map =
                    ItemMapHelper.filterPos(cmd.getItems())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getItems().get(item.getId()),
                                    Integer::sum
                            ));
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        inventarisationReport.setCreatedAt(createdAt);
        inventarisationReport.setExpirationDate(new Date(createdAt.getTime() + CerberusParameters.expirationDuration));
        inventarisationReport.setCreator(creator.get());
        inventarisationReport.setItems(items_map);
    }

    public void updateBy(ShipmentReport shipmentReport, UpdateShipmentReportCmd cmd) {

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));

        Map<Item, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
            items_map =
                    ItemMapHelper.filterPos(cmd.getItems())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getItems().get(item.getId()),
                                    Integer::sum
                            ));
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        shipmentReport.setCreatedAt(createdAt);
        shipmentReport.setExpirationDate(new Date(createdAt.getTime() + CerberusParameters.expirationDuration));
        shipmentReport.setCreator(creator.get());
        shipmentReport.setItems(items_map);
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

        Map<Item, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
            items_map =
                    ItemMapHelper.filterPos(cmd.getItems())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getItems().get(item.getId()),
                                    Integer::sum
                            ));
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        releaseReport.setCreatedAt(createdAt);
        releaseReport.setExpirationDate(new Date(createdAt.getTime() + CerberusParameters.expirationDuration));
        releaseReport.setCreator(creator.get());
        releaseReport.setSupplyReqReportId(SRQReport.get());
        releaseReport.setItems(items_map);
    }

    public void updateBy(ReplenishmentReport replenishmentReport, UpdateReplenishmentReportCmd cmd) {

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));
        Map<Item, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
            items_map =
                    ItemMapHelper.filterPos(cmd.getItems())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getItems().get(item.getId()),
                                    Integer::sum
                            ));
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        replenishmentReport.setCreatedAt(createdAt);
        replenishmentReport.setExpirationDate(new Date(createdAt.getTime() + CerberusParameters.expirationDuration));
        replenishmentReport.setCreatedAt(new Date());
        replenishmentReport.setCreator(creator.get());
        replenishmentReport.setItems(items_map);
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
        Map<Item, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
            items_map =
                    ItemMapHelper.filterPos(cmd.getItems())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getItems().get(item.getId()),
                                    Integer::sum
                            ));
        Map<Item, Integer> unclaimedRemainsMap;

        if (cmd.getUnclaimedRemains() == null)
            unclaimedRemainsMap = new HashMap<>();
        else
            unclaimedRemainsMap =
                    ItemMapHelper.filterPos(cmd.getUnclaimedRemains())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getUnclaimedRemains().get(item.getId()),
                                    Integer::sum
                            ));
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        workShiftReplenishmentReport.setCreatedAt(createdAt);
        workShiftReplenishmentReport.setExpirationDate(new Date(createdAt.getTime() + CerberusParameters.expirationDuration));
        workShiftReplenishmentReport.setCreator(creator.get());
        workShiftReplenishmentReport.setWorkShiftReport(WHReport.get());
        workShiftReplenishmentReport.setItems(items_map);
        workShiftReplenishmentReport.setUnclaimedRemains(unclaimedRemainsMap);

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

        Map<Product, Integer> produced_map;
        if (cmd.getProduced() == null)
            produced_map = new HashMap<>();
        else
            produced_map = ProductMapHelper.filterPos(cmd.getProduced())
                    .keySet()
                    .stream()
                    .map(productRepository::findById)
                    .filter(Optional::isPresent)
                    .map(optional -> (Product)optional.get())
                    .collect(Collectors.toMap(
                            Function.identity(),
                            item -> cmd.getProduced().get(item.getId()),
                            Integer::sum
                    ));


        Map<Item, Integer> losses_map;
        if (cmd.getLosses() == null)
            losses_map = new HashMap<>();
        else
            losses_map =
                    ItemMapHelper.filterPos(cmd.getLosses())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getLosses().get(item.getId()),
                                    Integer::sum
                            ));
        Map<Item, Integer>  remains_map;
        if (cmd.getRemains() == null)
            remains_map = new HashMap<>();
        else
            remains_map =
                    ItemMapHelper.filterPos(cmd.getRemains())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getRemains().get(item.getId()),
                                    Integer::sum
                            ));


        Map<Item, Integer>  unclaimed_remains_map;
        if (cmd.getUnclaimedRemains() == null)
            unclaimed_remains_map = new HashMap<>();
        else
            unclaimed_remains_map =
                    ItemMapHelper.filterPos(cmd.getUnclaimedRemains())
                            .keySet()
                            .stream()
                            .map(itemID -> itemRepository.findById(itemID))
                            .filter(Optional::isPresent)
                            .map(optional -> (Item) optional.get())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    item -> cmd.getUnclaimedRemains().get(item.getId()),
                                    Integer::sum
                            ));
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        workShiftReport.setCreatedAt(createdAt);
        workShiftReport.setExpirationDate(new Date(createdAt.getTime() + CerberusParameters.expirationDuration));
        workShiftReport.setCreator(creator.get());
        workShiftReport.setProduced(produced_map);
        workShiftReport.setLosses(losses_map);
        workShiftReport.setRemains(remains_map);
        workShiftReport.setTargetWareHouses(targetWareHouses);
        workShiftReport.setUnclaimedRemains(unclaimed_remains_map);

    }
}
