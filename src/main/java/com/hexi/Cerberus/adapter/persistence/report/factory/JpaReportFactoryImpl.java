package com.hexi.Cerberus.adapter.persistence.report.factory;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.SupplyRequirementReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.warehouse.*;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.config.CerberusParameters;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportFactory;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.*;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaReportFactoryImpl implements ReportFactory {
    public final ItemRepository itemRepository;
    public final ProductRepository productRepository;
    public final FactorySiteRepository factorySiteRepository;
    public final WareHouseRepository wareHouseRepository;
    public final ReportRepository reportRepository;


    @SneakyThrows
    public Report from(CreateReportCmd build) {
        switch (build) {
            case CreateSupplyRequirementReportCmd cmd:
                return createSupplyRequirementReport(cmd);

            case CreateInventarisationReportCmd cmd:
                return createInventarisationReport(cmd);

            case CreateShipmentReportCmd cmd:
                return createShipmentReport(cmd);

            case CreateReleaseReportCmd cmd:
                return createReleaseReport(cmd);

            case CreateReplenishmentReportCmd cmd:
                return createReplenishmentReport(cmd);

            case CreateWorkShiftReplenishmentReportCmd cmd:
                return createWorkShiftReplenishmentReport(cmd);

            case CreateWorkShiftReportCmd cmd:
                return createWorkShiftReport(cmd);

            default:
                throw new Exception("Fake CreateReportCmd subtype");
        }
    }

    private Report createWorkShiftReport(CreateWorkShiftReportCmd cmd) {
        Optional<FactorySiteModel> factorySite;
        List<WareHouseModel> wareHouses;

        factorySite = factorySiteRepository.findById(cmd.getFactorySiteId());
        factorySite.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid factory site id: %s", cmd.getFactorySiteId().toString())
        ));


        wareHouses = wareHouseRepository.findAllById(cmd.getTargetWareHouseIds());
        if (wareHouses.isEmpty()) throw new RuntimeException(
                "Error while creating report: " +
                        "There are no warehouses found:"
        );
        List<WareHouse> unregisteredAsSuppliers = factorySite.get().getSuppliers().stream().filter(sup -> !(wareHouses.stream().anyMatch(wareHouseModel -> wareHouseModel.getId().equals(sup.getId())))).collect(Collectors.toList());
        if (!unregisteredAsSuppliers.isEmpty())
            throw new RuntimeException(
                    "Error while creating report: " +
                            String.format("Warehouse %s is not supplier of factory site %s", unregisteredAsSuppliers.get(0).getName().toString(), factorySite.get().getName().toString()));


        Map<ProductModel, Integer> produced_map;
        if (cmd.getProduced() == null)
            produced_map = new HashMap<>();
        else
            produced_map = cmd
                .getProduced()
                .keySet()
                .stream()
                .map(productRepository::findById)
                .filter(Optional::isPresent)
                .map(optional -> (ProductModel)optional.get())
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getProduced().get(item.getId()),
                        Integer::sum
                ));


        Map<ItemModel, Integer> losses_map;
        if (cmd.getLosses() == null)
            losses_map = new HashMap<>();
        else
            losses_map = cmd
                    .getLosses()
                    .keySet()
                    .stream()
                    .map(itemID -> itemRepository.findById(itemID))
                    .filter(Optional::isPresent)
                    .map(optional -> (ItemModel) optional.get())
                    .collect(Collectors.toMap(
                            Function.identity(),
                            item -> cmd.getLosses().get(item.getId()),
                            Integer::sum
                    ));
        Map<ItemModel, Integer>  remains_map;
        if (cmd.getRemains() == null)
            remains_map = new HashMap<>();
        else
            remains_map = cmd
                .getRemains()
                .keySet()
                .stream()
                .map(itemID -> itemRepository.findById(itemID))
                .filter(Optional::isPresent)
                .map(optional -> (ItemModel) optional.get())
                    .collect(Collectors.toMap(
                            Function.identity(),
                            item -> cmd.getRemains().get(item.getId()),
                            Integer::sum
                    ));


        Map<ItemModel, Integer>  unclaimed_remains_map;
        if (cmd.getUnclaimedRemains() == null)
            unclaimed_remains_map = new HashMap<>();
        else
            unclaimed_remains_map = cmd
                    .getUnclaimedRemains()
                    .keySet()
                    .stream()
                    .map(itemID -> itemRepository.findById(itemID))
                    .filter(Optional::isPresent)
                    .map(optional -> (ItemModel) optional.get())
                    .collect(Collectors.toMap(
                            Function.identity(),
                            item -> cmd.getUnclaimedRemains().get(item.getId()),
                            Integer::sum
                    ));

        return new WorkShiftReportModel(
                new ReportID(),
                factorySite.get(),
                new Date(),
                new Date(new Date().getTime() + CerberusParameters.expirationDuration),
                wareHouses,
                produced_map,
                losses_map,
                remains_map,
                unclaimed_remains_map
        );

    }

    private Report createWorkShiftReplenishmentReport(CreateWorkShiftReplenishmentReportCmd cmd) {
        Optional<FactorySiteModel> factorySite;
        Optional<WareHouseModel> wareHouse;
        Map<ItemModel, Integer> items_map;

        Optional<ReportModel> whReport = reportRepository.findById(cmd.getWorkShiftReportId());
        whReport.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid workshift report id: %s", cmd.getWorkShiftReportId().toString())
        ));
        FactorySiteModel whReportFactorySite = (FactorySiteModel) ((WorkShiftReportModel) whReport.get()).getFactorySite();
        factorySite = factorySiteRepository.findById(whReportFactorySite.getId());
        factorySite.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid factory site id: %s", whReportFactorySite.getId().toString())
        ));

        wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items_map = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemID -> itemRepository.findById(itemID))
                .filter(Optional::isPresent)
                .map(optional -> (ItemModel) optional.get())
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));
        Map<ItemModel, Integer> unclaimedRemainsMap;

        if (cmd.getUnclaimedRemains() == null)
            unclaimedRemainsMap = new HashMap<>();
        else
            unclaimedRemainsMap = cmd
                .getUnclaimedRemains()
                .keySet()
                .stream()
                .map(itemID -> itemRepository.findById(itemID))
                .filter(Optional::isPresent)
                .map(optional -> (ItemModel) optional.get())
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getUnclaimedRemains().get(item.getId()),
                        Integer::sum
                ));

        return new WorkShiftR11tReportModel(
                new ReportID(),
                wareHouse.get(),
                new Date(),
                new Date(new Date().getTime() + CerberusParameters.expirationDuration),
                (WorkShiftReportModel)whReport.get(),
                items_map,
                unclaimedRemainsMap
        );

    }

    private Report createReplenishmentReport(CreateReplenishmentReportCmd cmd) {
        Optional<WareHouseModel> wareHouse;
        Map<ItemModel, Integer> items_map;

        wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items_map = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemID -> itemRepository.findById(itemID))
                .filter(Optional::isPresent)
                .map(optional -> (ItemModel) optional.get())
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new ReplenishmentReportModel(
                new ReportID(),
                wareHouse.get(),
                new Date(),
                new Date(new Date().getTime() + CerberusParameters.expirationDuration),
                items_map
        );

    }

    private Report createReleaseReport(CreateReleaseReportCmd cmd) {

        Optional<WareHouseModel> wareHouse;
        Map<ItemModel, Integer> items_map;

        Optional<ReportModel> sqReport = reportRepository.findById(cmd.getSupplyReqReportId());
        sqReport.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid factory site id: %s", cmd.getSupplyReqReportId().toString())
        ));

        wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items_map = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemID -> itemRepository.findById(itemID))
                .filter(Optional::isPresent)
                .map(optional -> (ItemModel) optional.get())
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new ReleaseReportModel(
                new ReportID(),
                wareHouse.get(),
                new Date(),
                new Date(new Date().getTime() + CerberusParameters.expirationDuration),
                (SupplyRequirementReportModel) sqReport.get(),
                items_map
        );

    }

    private Report createShipmentReport(CreateShipmentReportCmd cmd) {
        Optional<WareHouseModel> wareHouse;
        Map<ItemModel, Integer> items_map;

        wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items_map = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemID -> itemRepository.findById(itemID))
                .filter(Optional::isPresent)
                .map(optional -> (ItemModel) optional.get())
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new ShipmentReportModel(
                new ReportID(),
                wareHouse.get(),
                new Date(),
                new Date(new Date().getTime() + CerberusParameters.expirationDuration),
                items_map
        );

    }

    private Report createInventarisationReport(CreateInventarisationReportCmd cmd) {
        Optional<WareHouseModel> wareHouse;
        Map<ItemModel, Integer> items_map;

        wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items_map = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemID -> itemRepository.findById(itemID))
                .filter(Optional::isPresent)
                .map(optional -> (ItemModel) optional.get())
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new InventarisationReportModel(
                new ReportID(),
                wareHouse.get(),
                new Date(),
                new Date(new Date().getTime() + CerberusParameters.expirationDuration),
                items_map)
                ;
    }

    private Report createSupplyRequirementReport(CreateSupplyRequirementReportCmd cmd) {
        Optional<FactorySiteModel> factorySite;
        List<WareHouseModel> wareHouses;
        Map<ItemModel, Integer> items_map;

        factorySite = factorySiteRepository.findById(cmd.getFactorySiteID());
        factorySite.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid factory site id: %s", cmd.getFactorySiteID().toString())
        ));

        wareHouses = wareHouseRepository.findAllById(cmd.getTargetWareHouseIds());
        if (wareHouses.isEmpty()) throw new RuntimeException(
                "Error while creating report: " +
                        "There are no warehouses found:"
        );
        List<WareHouse> unregisteredAsSuppliers = factorySite.get().getSuppliers().stream().filter(sup -> !(wareHouses.stream().anyMatch(wareHouseModel -> wareHouseModel.getId().equals(sup.getId())))).collect(Collectors.toList());
        if (!unregisteredAsSuppliers.isEmpty())
            throw new RuntimeException(
                    "Error while creating report: " +
                            String.format("Warehouse %s is not supplier of factory site %s", unregisteredAsSuppliers.get(0).getName().toString(), factorySite.get().getName().toString()));


        items_map = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemID -> itemRepository.findById(itemID))
                .filter(Optional::isPresent)
                .map(optional -> (ItemModel) optional.get())
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new SupplyRequirementReportModel(
                new ReportID(),
                factorySite.get(),
                new Date(),
                new Date(new Date().getTime() + CerberusParameters.expirationDuration),
                wareHouses,
                items_map
        );

    }


}
