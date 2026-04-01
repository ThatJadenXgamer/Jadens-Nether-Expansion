package net.jadenxgamer.netherexp.util;

import net.minecraft.world.item.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Used to store arbitrary data on the client tied to an ItemStack
 * Data is not saved or synced with the server, this is NOT meant to be a replacement for DataComponents,
 * its main purpose is for animations and particles!
 */
//TODO: Move to ElysiumAPI
public class ClientItemData {
    private static final WeakHashMap<ItemStack, Map<String, Object>> dataMap = new WeakHashMap<>();

    public static Map<String, Object> getOrCreate(ItemStack stack) {
        return dataMap.computeIfAbsent(stack, k -> new HashMap<>());
    }

    public static Map<String, Object> get(ItemStack stack) {
        return dataMap.get(stack);
    }

    public static boolean has(ItemStack stack, String key) {
        if (stack == null) return false;
        Map<String, Object> map = dataMap.get(stack);
        return map != null && map.containsKey(key);
    }

    public static void copy(ItemStack from, ItemStack to) {
        Map<String, Object> fromData = dataMap.get(from);
        if (fromData != null) dataMap.put(to, new HashMap<>(fromData));
    }

    public static void remove(ItemStack stack) {
        dataMap.remove(stack);
    }
}