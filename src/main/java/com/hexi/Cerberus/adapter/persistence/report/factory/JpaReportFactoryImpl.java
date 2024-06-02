package com.hexi.Cerberus.adapter.persistence.report.factory;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.SupplyRequirementReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.warehouse.*;
import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.application.report.service.command.create.*;
import com.hexi.Cerberus.config.CerberusParameters;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.domain.helpers.ItemMapHelper;
import com.hexi.Cerberus.domain.helpers.ProductMapHelper;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportFactory;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JpaReportFactoryImpl implements ReportFactory {
    public final ItemRepository itemRepository;
    public final ProductRepository productRepository;
    public final FactorySiteRepository factorySiteRepository;
    public final WareHouseRepository wareHouseRepository;
    public final ReportRepository reportRepository;
    public final UserRepository userRepository;


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
        List<WareHouse> unregisteredAsSuppliers = wareHouses.stream().filter(sup -> !(factorySite.get().getSuppliers().stream().anyMatch(wareHouseModel -> wareHouseModel.getId().equals(sup.getId())))).collect(Collectors.toList());
        if (!unregisteredAsSuppliers.isEmpty())
            throw new RuntimeException(
                    "Error while creating report: " +
                            String.format("Warehouse %s is not supplier of factory site %s", unregisteredAsSuppliers.get(0).getName().toString(), factorySite.get().getName().toString()));



        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));

        Map<ProductModel, Integer> produced_map;
        if (cmd.getProduced() == null)
            produced_map = new HashMap<>();
        else
            produced_map = ProductMapHelper.filterPos(cmd.getProduced())
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
            losses_map =
                    ItemMapHelper.filterPos(cmd.getLosses())
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
            remains_map =
                    ItemMapHelper.filterPos(cmd.getRemains())
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
            unclaimed_remains_map =
                    ItemMapHelper.filterPos(cmd.getUnclaimedRemains())
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
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        return new WorkShiftReportModel(
                new ReportID(),
                factorySite.get(),
                createdAt,
                new Date(createdAt.getTime() + CerberusParameters.expirationDuration),
                wareHouses,
                produced_map,
                losses_map,
                remains_map,
                unclaimed_remains_map,
                ((UserModel)creator.get())
        );

    }

    private Report createWorkShiftReplenishmentReport(CreateWorkShiftReplenishmentReportCmd cmd) {
        Optional<FactorySiteModel> factorySite;
        Optional<WareHouseModel> wareHouse;


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

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));

        Map<ItemModel, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
        items_map =
                ItemMapHelper.filterPos(cmd.getItems())
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
            unclaimedRemainsMap =
                    ItemMapHelper.filterPos(cmd.getUnclaimedRemains())
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
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        return new WorkShiftR11tReportModel(
                new ReportID(),
                wareHouse.get(),
                createdAt,
                new Date(createdAt.getTime() + CerberusParameters.expirationDuration),
                (WorkShiftReportModel)whReport.get(),
                items_map,
                unclaimedRemainsMap,
                ((UserModel)creator.get()));

    }

    private Report createReplenishmentReport(CreateReplenishmentReportCmd cmd) {
        Optional<WareHouseModel> wareHouse;

        wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));
        Map<ItemModel, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
        items_map =
                ItemMapHelper.filterPos(cmd.getItems())
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
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        return new ReplenishmentReportModel(
                new ReportID(),
                wareHouse.get(),
                createdAt,
                new Date(createdAt.getTime() + CerberusParameters.expirationDuration),
                items_map,
                ((UserModel)creator.get()));

    }

    private Report createReleaseReport(CreateReleaseReportCmd cmd) {

        Optional<WareHouseModel> wareHouse;

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

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));
        Map<ItemModel, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
        items_map =
                ItemMapHelper.filterPos(cmd.getItems())
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
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        return new ReleaseReportModel(
                new ReportID(),
                wareHouse.get(),
                createdAt,
                new Date(createdAt.getTime() + CerberusParameters.expirationDuration),
                (SupplyRequirementReportModel) sqReport.get(),
                items_map,
                ((UserModel)creator.get()));

    }

    private Report createShipmentReport(CreateShipmentReportCmd cmd) {
        Optional<WareHouseModel> wareHouse;

        wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));
        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));
        Map<ItemModel, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
        items_map =
                ItemMapHelper.filterPos(cmd.getItems())
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
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        return new ShipmentReportModel(
                new ReportID(),
                wareHouse.get(),
                createdAt,
                new Date(createdAt.getTime() + CerberusParameters.expirationDuration),
                items_map,
                ((UserModel)creator.get()));

    }

    private Report createInventarisationReport(CreateInventarisationReportCmd cmd) {
        Optional<WareHouseModel> wareHouse;


        wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));
        Map<ItemModel, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
        items_map =
                ItemMapHelper.filterPos(cmd.getItems())
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
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        return new InventarisationReportModel(
                new ReportID(),
                wareHouse.get(),
                createdAt,
                new Date(createdAt.getTime() + CerberusParameters.expirationDuration),
                items_map, ((UserModel)creator.get()));
    }

    private Report createSupplyRequirementReport(CreateSupplyRequirementReportCmd cmd) {
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
        log.info("Founded warehouses: " + wareHouses.toString());
        log.info("FactorySite suppliers: " + factorySite.get().getSuppliers().toString());
        List<WareHouse> unregisteredAsSuppliers = wareHouses.stream().filter(sup -> !(factorySite.get().getSuppliers().stream().anyMatch(wareHouseModel -> wareHouseModel.getId().equals(sup.getId())))).collect(Collectors.toList());
        log.info("Unregistered suppliers: " + unregisteredAsSuppliers.toString());

        if (!unregisteredAsSuppliers.isEmpty())
            throw new RuntimeException(
                    "Error while creating report: " +
                            String.format("Warehouse %s is not supplier of factory site %s", unregisteredAsSuppliers.get(0).getName().toString(), factorySite.get().getName().toString()));

        Optional<User> creator = userRepository.findById(cmd.getCreatorId());
        creator.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid creator id: %s", cmd.getCreatorId().toString())
        ));
        Map<ItemModel, Integer> items_map;
        if (cmd.getItems() == null)
            items_map = new HashMap<>();
        else
        items_map =
                ItemMapHelper.filterPos(cmd.getItems())
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
        Date createdAt = cmd.getCreatedAt() == null ? new Date() : cmd.getCreatedAt();
        return new SupplyRequirementReportModel(
                new ReportID(),
                factorySite.get(),
                createdAt,
                new Date(createdAt.getTime() + CerberusParameters.expirationDuration),
                wareHouses,
                items_map,
                ((UserModel)creator.get()));

    }


}
