package com.hexi.Cerberus.adapter.persistence.report.repository;

import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.FactorySiteReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.SupplyRequirementReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.warehouse.*;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.FactorySiteReport;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.query.filter.*;
import com.hexi.Cerberus.domain.report.query.sort.*;
import com.hexi.Cerberus.domain.report.warehouse.*;
import com.hexi.Cerberus.infrastructure.entity.Entity;
import com.hexi.Cerberus.infrastructure.query.PagingCriteria;
import com.hexi.Cerberus.infrastructure.query.Query;
import com.hexi.Cerberus.infrastructure.query.comparation.ComparisonContainer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportSpecificationFactory {

    private static EntityManager manager;

    public static void setManager(EntityManager entityManager){
        manager = entityManager;
    }

    @SneakyThrows
    private static Class<? extends ReportModel> getModelClass(Class<? extends Report> base) {
        if (base == SupplyRequirementReport.class) return SupplyRequirementReportModel.class;
        if (base == WorkShiftReport.class) return WorkShiftReportModel.class;
        if (base == FactorySiteReport.class) return FactorySiteReportModel.class;
        if (base == InventarisationReport.class) return InventarisationReportModel.class;
        if (base == ShipmentReport.class) return ShipmentReportModel.class;
        if (base == ReplenishmentReport.class) return ReplenishmentReportModel.class;
        if (base == WorkShiftReplenishmentReport.class) return WorkShiftR11tReportModel.class;
        if (base == ReleaseReport.class) return ReleaseReportModel.class;
        if (base == WareHouseReport.class) return WareHouseReportModel.class;
        if (base == Report.class) return ReportModel.class;

        throw new ExecutionControl.NotImplementedException("");
    }




    public static Specification<ReportModel> genericReport(Query query) {

        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity());



        // Применяем фильтр
        if (query.getFilterCriteria() != null) {
            spec = spec.and(reportFilter((ReportFilterCriteria)query.getFilterCriteria()));
        }

        // Применяем сортировку
        if (query.getSortCriteria() != null) {
            spec = spec.and(reportSort((ReportSortCriteria)query.getSortCriteria()));
        }
        return spec;
    }

    private static Specification<ReportModel> getPagingSpecification(Query query) {
        if (query.getPagingCriteria() != null && query.getPagingCriteria().getKey() != null) {
            //пейджинг
            PagingCriteria pagingCriteria = query.getPagingCriteria();
            ReportID lastKey = (ReportID) pagingCriteria.getKey();
            PagingCriteria.Direction dir = pagingCriteria.getDirection();
            return getPgsqlTidPointingSpecification(lastKey, dir);

//            ScrollPosition.Direction direction = pagingCriteria.getDirection() == PagingCriteria.Direction.FORWARD ? ScrollPosition.Direction.FORWARD : ScrollPosition.Direction.BACKWARD;
//
//            // Создание KeysetScrollSpecification с указанием последнего ключевого набора и направления пагинации
//            return new KeysetScrollSpecification(
//                    ScrollPosition.of(Map.of("id", lastKey), direction),
//                    Sort.unsorted(),
//                    JpaEntityInformationSupport.getEntityInformation(getModelClass(query.getTargetEntity().asSubclass(Report.class)), manager)
//            );
        }
        return Specification.where(null);
    }


    public static Specification<ReportModel> getPgsqlTidPointingSpecification(ReportID key, PagingCriteria.Direction dir) {
        return (root, query, criteriaBuilder) -> {
                // Создаем подзапрос для получения tid записи с указанным key

            Subquery<Long> subquery = query.subquery(Long.class);
            // Определяем Root для Subquery
            Root<ReportModel> subqueryRoot = subquery.from(ReportModel.class);
            subquery.select(criteriaBuilder.function("get_ctid", Long.class)).where(criteriaBuilder.equal(subqueryRoot.get("id"), key));

            // Возвращаем условие для выбора записей с tid больше полученного tid из подзапроса

            if (dir == PagingCriteria.Direction.FORWARD)
                return criteriaBuilder.greaterThan(criteriaBuilder.function("get_ctid", Long.class), subquery);
            else
                return criteriaBuilder.lessThan(criteriaBuilder.function("get_ctid", Long.class), subquery);

        };
    }

    private static Specification<ReportModel> instanceFilter(Class<? extends Entity> targetEntity) {
        Class targetEntityModel = getModelClass(targetEntity.asSubclass(Report.class));
        return (root, query, criteriaBuilder) -> {
            query.where(criteriaBuilder.equal(criteriaBuilder.treat(root, targetEntityModel), root));
            return query.getRestriction();
        };
    }


    public static Specification<ReportModel> genericWareHouseReport(Query query) {


        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity()).and(genericReport(query));

        if (query.getFilterCriteria() != null) {
            spec = spec.and(wareHouseReportFilter((WareHouseReportFilterCriteria)query.getFilterCriteria()));
        }

//        // Применяем сортировку
//        if (query.getSortCriteria() != null) {
//            spec = (Specification) spec.and(wareHouseReportSort((WareHouseSortFilterCriteria)query.getSortCriteria()));
//        }

        return spec;
    }


    public static Specification<ReportModel> genericReleaseReport(Query query) {
        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity()).and(genericWareHouseReport(query));

        // Применяем фильтр
        if (query.getFilterCriteria() != null) {
            spec = spec.and(releaseReportFilter((ReleaseReportFilterCriteria)query.getFilterCriteria()));
        }

//        // Применяем сортировку
//        if (query.getSortCriteria() != null) {
//            spec = spec.and(releaseReportSort((ReleaseReportSortFilterCriteria)query.getSortCriteria()));
//        }

        return spec;
    }

    public static Specification<ReportModel> genericWorkShiftReplenishmentReport(Query query) {
        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity()).and(genericWareHouseReport(query));

        // Применяем фильтр
        if (query.getFilterCriteria() != null) {
            spec = spec.and(workShiftReplenishmentReportFilter((WorkShiftReplenishmentReportFilterCriteria) query.getFilterCriteria()));
        }

//        // Применяем сортировку
//        if (query.getSortCriteria() != null) {
//            spec = spec.and(workShiftReplenishmentReportSort(query.getSortCriteria()));
//        }

        return spec;
    }



    public static Specification<ReportModel> genericReplenishmentReport(Query query) {
        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity()).and(genericWareHouseReport(query));

//        // Применяем фильтр
//        if (query.getFilterCriteria() != null) {
//            spec = spec.and(replenishmentReportFilter(query.getFilterCriteria()));
//        }
//
//        // Применяем сортировку
//        if (query.getSortCriteria() != null) {
//            spec = spec.and(replenishmentReportSort(query.getSortCriteria()));
//        }

        return spec;
    }


    public static Specification<ReportModel> genericShipmentReport(Query query) {
        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity()).and(genericWareHouseReport(query));

//        // Применяем фильтр
//        if (query.getFilterCriteria() != null) {
//            spec = spec.and(shipmentReportFilter(query.getFilterCriteria()));
//        }
//
//        // Применяем сортировку
//        if (query.getSortCriteria() != null) {
//            spec = spec.and(shipmentReportSort(query.getSortCriteria()));
//        }

        return spec;
    }



    public static Specification<ReportModel> genericInventarisationReport(Query query) {
        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity()).and(genericWareHouseReport(query));

//        // Применяем фильтр
//        if (query.getFilterCriteria() != null) {
//            spec = spec.and(inventarisationReportFilter(query.getFilterCriteria()));
//        }
//
//        // Применяем сортировку
//        if (query.getSortCriteria() != null) {
//            spec = spec.and(inventarisationReportSort(query.getSortCriteria()));
//        }

        return spec;
    }



    public static Specification<ReportModel> genericFactorySiteReport(Query query) {
        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity()).and(genericReport(query));

        // Применяем фильтр
        if (query.getFilterCriteria() != null) {
            spec = spec.and(factorySiteReportFilter((FactorySiteReportFilterCriteria) query.getFilterCriteria()));
        }

//        // Применяем сортировку
//        if (query.getSortCriteria() != null) {
//            spec = spec.and(factorySiteReportSort(query.getSortCriteria()));
//        }

        return spec;
    }



    public static Specification<ReportModel> genericSupplyRequirementReport(Query query) {
        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity()).and(genericFactorySiteReport(query));

        // Применяем фильтр
//        if (query.getFilterCriteria() != null) {
//            spec = spec.and(supplyRequirementReportFilter(query.getFilterCriteria()));
//        }

//        // Применяем сортировку
//        if (query.getSortCriteria() != null) {
//            spec = spec.and(supplyRequirementReportSort((SupplyRequirementReportSortCriteria)query.getSortCriteria()));
//        }

        return spec;
    }



    public static Specification<ReportModel> genericWorkShiftReport(Query query) {
        System.out.println("at genericWorkShiftReport: " + query);
        Specification<ReportModel> spec = instanceFilter(query.getTargetEntity()).and(genericFactorySiteReport(query));

        // Применяем фильтр
        if (query.getFilterCriteria() != null) {
            spec = spec.and(workShiftReportFilter((WorkShiftReportFilterCriteria) query.getFilterCriteria()));
        }

//        // Применяем сортировку
//        if (query.getSortCriteria() != null) {
//            spec = spec.and(workShiftReportSort(query.getSortCriteria()));
//        }

        return spec;
    }




    private static Specification<ReportModel> reportFilter(ReportFilterCriteria filterCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterCriteria.getCreatedDate() != null) {
                ComparisonContainer<Date> date = filterCriteria.getCreatedDate();
                Predicate datePredicate;
                switch (date.getSign()) {
                    case GREATER:
                        datePredicate = criteriaBuilder.greaterThan(root.get("createdAt"), date.getValue());
                        break;
                    case LESS:
                        datePredicate = criteriaBuilder.lessThan(root.get("createdAt"), date.getValue());
                        break;
                    case EQUAL:
                        datePredicate = criteriaBuilder.equal(root.get("createdAt"), date.getValue());
                        break;
                    case GREATEREQUAL:
                        datePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), date.getValue());
                        break;
                    case LESSEQUAL:
                        datePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), date.getValue());
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported sign: " + date.getSign());
                }
                predicates.add(datePredicate);
            }

            if (filterCriteria.getDeletedDate() != null) {
                ComparisonContainer<Date> date = filterCriteria.getDeletedDate();
                Predicate datePredicate;
                switch (date.getSign()) {
                    case GREATER:
                        datePredicate = criteriaBuilder.greaterThan(root.get("deletedAt"), date.getValue());
                        break;
                    case LESS:
                        datePredicate = criteriaBuilder.lessThan(root.get("deletedAt"), date.getValue());
                        break;
                    case EQUAL:
                        datePredicate = criteriaBuilder.equal(root.get("deletedAt"), date.getValue());
                        break;
                    case GREATEREQUAL:
                        datePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("deletedAt"), date.getValue());
                        break;
                    case LESSEQUAL:
                        datePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("deletedAt"), date.getValue());
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported sign: " + date.getSign());
                }
                predicates.add(datePredicate);
            }



//            if (filterCriteria.getStatus() != null) {
//                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), filterCriteria.getStatus());
//                predicates.add(statusPredicate);
//            }

            if (filterCriteria.getDepartmentId() != null) {
                Predicate departmentPredicate = criteriaBuilder.equal(root.get("department").get("id"), filterCriteria.getDepartmentId());
                predicates.add(departmentPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Метод для создания спецификации сортировки
    private static Specification<ReportModel> reportSort(ReportSortCriteria sortCriteria) {
        return (root, query, criteriaBuilder) -> {

            List<ReportSortCriteria.SortBy> sorts = sortCriteria.getSortBy();
            List<ReportSortCriteria.SortType> types = sortCriteria.getSortType();
            assert sorts.size() != types.size() : "Invalid ReportSortCriteria: sorts.size() != types.size()";
            for (int i = 0; i < sorts.size(); i++) {
                switch (sorts.get(i)){
                    case CREATED:
                        if (types.get(i) == ReportSortCriteria.SortType.DESCENDING) {
                            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
                        } else {
                            query.orderBy(criteriaBuilder.asc(root.get("createdAt")));
                        }
                        break;
                    case DELETED:
                        if (types.get(i) == ReportSortCriteria.SortType.DESCENDING) {
                            query.orderBy(criteriaBuilder.desc(root.get("deletedAt")));
                        } else {
                            query.orderBy(criteriaBuilder.asc(root.get("deletedAt")));
                        }
                        break;
                }

            }


            return query.getRestriction();
        };
    }

    private static Specification<ReportModel> wareHouseReportFilter(WareHouseReportFilterCriteria filterCriteria) {
        return (root, query, criteriaBuilder) -> {
            Root<WareHouseReportModel> root1 = criteriaBuilder.treat(root, WareHouseReportModel.class);
            List<Predicate> predicates = new ArrayList<>();

            if (filterCriteria.getWarehouseId() != null) {
                Predicate warehousePredicate = criteriaBuilder.equal(root1.get("wareHouse").get("id"), filterCriteria.getWarehouseId());
                predicates.add(warehousePredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

//    private Specification<ReportModel> wareHouseReportSort(WareHouseReportSortCriteria sortCriteria) {
//        // Ваша реализация сортировки для отчета
//    }

    private static Specification<ReportModel> releaseReportFilter(ReleaseReportFilterCriteria filterCriteria) {
        return (root, query, criteriaBuilder) -> {
            Root<ReleaseReportModel> root1 = criteriaBuilder.treat(root, ReleaseReportModel.class);
            List<Predicate> predicates = new ArrayList<>();

            if (filterCriteria.getTargetFactorySiteId() != null) {
                Predicate factorySitePredicate = criteriaBuilder.equal(root1.get("supplyReqReport").get("factorySite").get("id"), filterCriteria.getTargetFactorySiteId());
                predicates.add(factorySitePredicate);
            }

            if (filterCriteria.getSupplyReqReportId() != null) {
                Predicate supplyReqReportPredicate = criteriaBuilder.equal(root1.get("supplyReqReport").get("id"), filterCriteria.getSupplyReqReportId());
                predicates.add(supplyReqReportPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
//    private Specification<ReportModel> releaseReportSort(ReleaseReportSortCriteria sortCriteria) {
//        // Ваша реализация сортировки для отчета о выпуске
//    }

//    private Specification<ReportModel> workShiftReplenishmentReportSort(WorkShiftReplenishmentReportSortCriteria sortCriteria) {
//    }

    private static Specification<ReportModel> workShiftReplenishmentReportFilter(WorkShiftReplenishmentReportFilterCriteria filterCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Root<WorkShiftR11tReportModel> root1 = criteriaBuilder.treat(root, WorkShiftR11tReportModel.class);
            if (filterCriteria.getTargetFactorySiteId() != null) {
                Predicate factorySitePredicate = criteriaBuilder.equal(root1.get("workShiftReport").get("factorySite").get("id"), filterCriteria.getTargetFactorySiteId());
                predicates.add(factorySitePredicate);
            }

            if (filterCriteria.getWorkShiftReportId() != null) {
                Predicate workShiftReportPredicate = criteriaBuilder.equal(root1.get("workShiftReport").get("id"), filterCriteria.getWorkShiftReportId());
                predicates.add(workShiftReportPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
//    private Specification<ReportModel> replenishmentReportSort(ReplenishmentReportSortCriteria sortCriteria) {
//    }
//
//    private Specification<ReportModel> replenishmentReportFilter(ReplenishmentReportFilterCriteria filterCriteria) {
//    }


//    private Specification<ReportModel> shipmentReportSort(ShipmentReportSortCriteria sortCriteria) {
//    }
//
//    private Specification<ReportModel> shipmentReportFilter(ShipmentReportFilterCriteria filterCriteria) {
//    }
//
//    private Specification<ReportModel> inventarisationReportSort(InventarisationReportSortCriteria sortCriteria) {
//    }
//
//    private Specification<ReportModel> inventarisationReportFilter(InventarisationReportFilterCriteria filterCriteria) {
//    }

//    private Specification<ReportModel> factorySiteReportSort(FactorySiteReportSortCriteria sortCriteria) {
//    }

    private static Specification<ReportModel> factorySiteReportFilter(FactorySiteReportFilterCriteria filterCriteria) {
        return (root, query, criteriaBuilder) -> {
            Root<FactorySiteReportModel> root1 = criteriaBuilder.treat(root, FactorySiteReportModel.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filterCriteria.getFactorySiteId() != null) {
                Predicate factorySitePredicate = criteriaBuilder.equal(root1.get("factorySite").get("id"), filterCriteria.getFactorySiteId());
                predicates.add(factorySitePredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @SneakyThrows
    public static Specification<ReportModel> getSpec(Query domainQuery) {
        Specification res;
        if (domainQuery.getTargetEntity() == SupplyRequirementReport.class) res =  ( ReportSpecificationFactory.genericSupplyRequirementReport(domainQuery)); else
        if (domainQuery.getTargetEntity() == WorkShiftReport.class) res =  ( ReportSpecificationFactory.genericWorkShiftReport(domainQuery)); else
        if (domainQuery.getTargetEntity() == FactorySiteReport.class) res =  ( ReportSpecificationFactory.genericFactorySiteReport(domainQuery)); else
        if (domainQuery.getTargetEntity() == InventarisationReport.class) res =  ( ReportSpecificationFactory.genericInventarisationReport(domainQuery)); else
        if (domainQuery.getTargetEntity() == ShipmentReport.class) res =  ( ReportSpecificationFactory.genericShipmentReport(domainQuery)); else
        if (domainQuery.getTargetEntity() == ReplenishmentReport.class) res =  ( ReportSpecificationFactory.genericReplenishmentReport(domainQuery)); else
        if (domainQuery.getTargetEntity() == WorkShiftReplenishmentReport.class) res =  ( ReportSpecificationFactory.genericWorkShiftReplenishmentReport(domainQuery)); else
        if (domainQuery.getTargetEntity() == ReleaseReport.class) res =   ( ReportSpecificationFactory.genericReleaseReport(domainQuery)); else
        if (domainQuery.getTargetEntity() == WareHouseReport.class) res =  ( ReportSpecificationFactory.genericWareHouseReport(domainQuery)); else
        if (domainQuery.getTargetEntity() == Report.class) res =  ( ReportSpecificationFactory.genericReport(domainQuery)); else
        throw new ExecutionControl.NotImplementedException("");

        res = res.and(getPagingSpecification(domainQuery));
        return res;
    }

//    private Specification<ReportModel> supplyRequirementReportSort(SupplyRequirementReportSortCriteria sortCriteria) {
//    }

//    private Specification<ReportModel> supplyRequirementReportFilter(SupplyRequirementReportFilterCriteria filterCriteria) {
//
//    }

//    private Specification<ReportModel> workShiftReportSort(WorkShiftReportSortCriteria sortCriteria) {
//    }

    private static Specification<ReportModel> workShiftReportFilter(WorkShiftReportFilterCriteria filterCriteria) {
        System.out.println("adding workShiftReportFilter: " + filterCriteria.toString());
        return (root, query, criteriaBuilder) -> {
            Root<WorkShiftReportModel> root1 = criteriaBuilder.treat(root, WorkShiftReportModel.class);


            if (filterCriteria.getHasLosses() != null) {
                if (filterCriteria.getHasLosses()){
                    Predicate predicate = criteriaBuilder.isNotNull(root1.get("losses").get("id"));
                    query.where(predicate);
                }else{
                    Predicate predicate = criteriaBuilder.isNull(root1.get("losses").get("id"));
                    query.where(predicate);
                }
            }

            if (filterCriteria.getHasRemains() != null) {
                if (filterCriteria.getHasRemains()) {
                    Predicate predicate = criteriaBuilder.isNotNull(root1.get("remains").get("id"));
                    query.where(predicate);
                }else {
                    Predicate predicate = criteriaBuilder.isNull(root1.get("remains").get("id"));
                    query.where(predicate);
                }
            }


            return query.getRestriction();
        };
    }


}
