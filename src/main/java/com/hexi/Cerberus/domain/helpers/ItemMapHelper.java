package com.hexi.Cerberus.domain.helpers;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemMapHelper {
    public static Map<ItemID, Integer> MergeDictionariesWithSum (Map<ItemID, Integer> a, Map<ItemID, Integer> b){
        HashSet<Map.Entry<ItemID, Integer>> generic = (new HashSet<>(a.entrySet()));
        generic.addAll(b.entrySet());

        return generic.stream().collect(Collectors.toMap(
                itemIntegerEntry -> itemIntegerEntry.getKey(),
                item -> item.getValue(),
                Integer::sum
        ));
    }

    public static Map<ItemID, Integer> MergeDictionariesWithSub (Map<ItemID, Integer> a, Map<ItemID, Integer> b){
        b = new HashMap<>(b);
        for(ItemID key : b.keySet()){
            b.put(key,-b.get(key));
        }
        return MergeDictionariesWithSum(a,b);
    }
}
