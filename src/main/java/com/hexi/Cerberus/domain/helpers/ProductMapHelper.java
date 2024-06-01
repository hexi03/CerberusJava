package com.hexi.Cerberus.domain.helpers;

import com.hexi.Cerberus.domain.product.ProductID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ProductMapHelper{
    public static Map<ProductID, Integer> MergeDictionariesWithSum(Map<ProductID, Integer> a, Map<ProductID, Integer> b) {
        List<Map.Entry<ProductID, Integer>> generic = a.entrySet().stream().collect(Collectors.toList());
        generic.addAll(b.entrySet());

        return generic.stream().collect(Collectors.toMap(
                itemIntegerEntry -> itemIntegerEntry.getKey(),
                item -> item.getValue(),
                Integer::sum
        ));
    }

    public static Map<ProductID, Integer> MergeDictionariesWithSub(Map<ProductID, Integer> a, Map<ProductID, Integer> b) {
        b = new HashMap<>(b);
        for (ProductID key : b.keySet()) {
            b.put(key, -b.get(key));
        }
        return MergeDictionariesWithSum(a, b);
    }


    public static Map<ProductID, Integer> filterPos(Map<ProductID, Integer> map) {
        return map.entrySet().stream().filter(entry -> entry.getValue() > 0).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    public static Map<ProductID, Integer> filterNeg(Map<ProductID, Integer> map) {
        return map.entrySet().stream().filter(entry -> entry.getValue() < 0).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    public static Map<ProductID, Integer> filterZero(Map<ProductID, Integer> map) {
        return map.entrySet().stream().filter(entry -> entry.getValue() == 0).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }
    public static Map<ProductID, Integer> filterNonZero(Map<ProductID, Integer> map) {
        return map.entrySet().stream().filter(entry -> entry.getValue() != 0).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

}
