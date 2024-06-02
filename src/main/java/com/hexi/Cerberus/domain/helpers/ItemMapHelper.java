package com.hexi.Cerberus.domain.helpers;

import com.hexi.Cerberus.domain.item.ItemID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemMapHelper {
    public static Map<ItemID, Integer> MergeDictionariesWithSum(Map<ItemID, Integer> a, Map<ItemID, Integer> b) {
        List<Map.Entry<ItemID, Integer>> generic = a.entrySet().stream().collect(Collectors.toList());
        generic.addAll(b.entrySet());

        return generic.stream().collect(Collectors.toMap(
                itemIntegerEntry -> itemIntegerEntry.getKey(),
                item -> item.getValue(),
                Integer::sum
        ));
    }

    public static Map<ItemID, Integer> MergeDictionariesWithSub(Map<ItemID, Integer> a, Map<ItemID, Integer> b) {
        b = new HashMap<>(b);
        for (ItemID key : b.keySet()) {
            b.put(key, -b.get(key));
        }
        return MergeDictionariesWithSum(a, b);
    }


    public static Map<ItemID, Integer> filterPos(Map<ItemID, Integer> map) {
        return map.entrySet().stream().filter(entry -> entry.getValue() > 0).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    public static Map<ItemID, Integer> filterNeg(Map<ItemID, Integer> map) {
        return map.entrySet().stream().filter(entry -> entry.getValue() < 0).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    public static Map<ItemID, Integer> filterZero(Map<ItemID, Integer> map) {
        return map.entrySet().stream().filter(entry -> entry.getValue() == 0).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }
    public static Map<ItemID, Integer> filterNonZero(Map<ItemID, Integer> map) {
        return map.entrySet().stream().filter(entry -> entry.getValue() != 0).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

}



