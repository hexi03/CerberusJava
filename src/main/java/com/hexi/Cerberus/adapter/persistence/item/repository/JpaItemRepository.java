package com.hexi.Cerberus.adapter.persistence.item.repository;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.infrastructure.query.Query;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaItemRepository<T extends ItemModel,ID extends ItemID> extends ItemRepository<T,ID>, JpaRepository<T,ID> {
    @SneakyThrows
    default List<T> findAllWithQuery(Query domainQuery){
        throw new ExecutionControl.NotImplementedException("");
    }

}
