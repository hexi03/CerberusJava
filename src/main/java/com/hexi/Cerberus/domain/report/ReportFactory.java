package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import com.hexi.Cerberus.domain.report.command.create.*;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.*;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportFactory {
    public final ItemRepository itemRepository;
    public final ProductRepository productRepository;
    public final FactorySiteRepository factorySiteRepository;
    public final WareHouseRepository wareHouseRepository;
    public final ReportRepository reportRepository;


    @SneakyThrows
    public Report from(CreateReportCmd build) {
        switch (build){
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
        Optional<FactorySite> factorySite;
        Optional<WareHouse> wareHouse;

        factorySite = factorySiteRepository.displayById(cmd.getFactorySiteId());
        factorySite.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getTargetWareHouseId().toString())
        ));


        wareHouse = wareHouseRepository.displayById(cmd.getTargetWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getTargetWareHouseId().toString())
        ));

        List<Product> produced = cmd
                .getProduced()
                .keySet()
                .stream()
                .map(productRepository::displayByItemId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        Map<Product, Integer> produced_map = produced.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getProduced().get(item.getId()),
                        Integer::sum
                ));

        List<Item> losses = cmd
                .getProduced()
                .keySet()
                .stream()
                .map(itemRepository::displayById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        Map<Item, Integer> losses_map = losses.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getLosses().get(item.getId()),
                        Integer::sum
                ));

        List<Item> remains = cmd
                .getProduced()
                .keySet()
                .stream()
                .map(itemRepository::displayById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        Map<Item, Integer> remains_map = losses.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getLosses().get(item.getId()),
                        Integer::sum
                ));

        return new WorkShiftReport(new ReportID(), factorySite.get(), new Date(), wareHouse.get(), produced_map, losses_map, remains_map);

    }

    private Report createWorkShiftReplenishmentReport(CreateWorkShiftReplenishmentReportCmd cmd) {
        Optional<FactorySite> factorySite;
        Optional<WareHouse> wareHouse;
        List<Item> items;
        Map<Item, Integer> items_map;

        Optional<Report> whReport = reportRepository.displayById(cmd.getWorkShiftReportId());
        whReport.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid factory site id: %s", cmd.getWorkShiftReportId().toString())
        ));
        FactorySite whReportFactorySite = ((WorkShiftReport)whReport.get()).getFactorySite();
        factorySite = factorySiteRepository.displayById(whReportFactorySite.getId());
        factorySite.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid factory site id: %s", whReportFactorySite.getId().toString())
        ));

        wareHouse = wareHouseRepository.displayById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemRepository::displayById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        items_map = items.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        List<Item> unclaimedRemains = cmd
                .getUnclaimedRemains()
                .keySet()
                .stream()
                .map(itemRepository::displayById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        Map<Item, Integer> unclaimedRemainsMap = unclaimedRemains.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getUnclaimedRemains().get(item.getId()),
                        Integer::sum
                ));

        return new WorkShiftReplenishmentReport(new ReportID(), wareHouse.get(), new Date(), whReportFactorySite, whReport.get(), items_map, unclaimedRemainsMap);

    }

    private Report createReplenishmentReport(CreateReplenishmentReportCmd cmd) {
        Optional<WareHouse> wareHouse;
        List<Item> items;
        Map<Item, Integer> items_map;

        wareHouse = wareHouseRepository.displayById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemRepository::displayById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        items_map = items.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new ReplenishmentReport(new ReportID(), wareHouse.get(), new Date(), items_map);

    }

    private Report createReleaseReport(CreateReleaseReportCmd cmd) {

        Optional<WareHouse> wareHouse;
        List<Item> items;
        Map<Item, Integer> items_map;

        Optional<Report> sqReport = reportRepository.displayById(cmd.getSupplyReqReportId());
        sqReport.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid factory site id: %s", cmd.getSupplyReqReportId().toString())
        ));

        wareHouse = wareHouseRepository.displayById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemRepository::displayById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        items_map = items.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new ReleaseReport(new ReportID(), wareHouse.get(), new Date(), sqReport.get(), items_map);

    }

    private Report createShipmentReport(CreateShipmentReportCmd cmd) {
        Optional<WareHouse> wareHouse;
        List<Item> items;
        Map<Item, Integer> items_map;

        wareHouse = wareHouseRepository.displayById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemRepository::displayById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        items_map = items.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new ShipmentReport(new ReportID(), wareHouse.get(), new Date(), items_map);

    }

    private Report createInventarisationReport(CreateInventarisationReportCmd cmd) {
        Optional<WareHouse> wareHouse;
        List<Item> items;
        Map<Item, Integer> items_map;

        wareHouse = wareHouseRepository.displayById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getWareHouseId().toString())
        ));

        items = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemRepository::displayById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        items_map = items.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new InventarisationReport(new ReportID(), wareHouse.get(), new Date(), items_map);
    }

    private Report createSupplyRequirementReport(CreateSupplyRequirementReportCmd cmd) {
        Optional<FactorySite> factorySite;
        Optional<WareHouse> wareHouse;
        List<Item> items;
        Map<Item, Integer> items_map;

        factorySite = factorySiteRepository.displayById(cmd.getFactorySiteID());
        factorySite.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid factory site id: %s", cmd.getFactorySiteID().toString())
        ));

        wareHouse = wareHouseRepository.displayById(cmd.getTargetWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(
                "Error while creating report: " +
                        String.format("Invalid target warehouse id: %s", cmd.getTargetWareHouseId().toString())
        ));

        items = cmd
                .getItems()
                .keySet()
                .stream()
                .map(itemRepository::displayById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        items_map = items.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> cmd.getItems().get(item.getId()),
                        Integer::sum
                ));

        return new SupplyRequirementReport(new ReportID(), factorySite.get(), new Date(), wareHouse.get(), items_map);

    }


}
