package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.TrimPattern;

import java.util.Optional;

public class JNETrimPatterns {
    public static final ResourceKey<TrimPattern> RIFT = key("rift");
    public static final ResourceKey<TrimPattern> SPIRIT = key("spirit");
    public static final ResourceKey<TrimPattern> VALOR = key("valor");

    private static ResourceKey<TrimPattern> key(String name) {
        return ResourceKey.create(Registries.TRIM_PATTERN, NetherExp.id(name));
    }

    public static void bootstrap(BootstrapContext<TrimPattern> context) {
        register(context, JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), RIFT);
        register(context, JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), SPIRIT);
        register(context, JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get(), VALOR);
    }

    // shoutout to John Minecraft, createor of miencraft.
    private static void register(BootstrapContext<TrimPattern> context, Item templateItem, ResourceKey<TrimPattern> trimPatternKey) {
        TrimPattern trimPattern = new TrimPattern(trimPatternKey.location(), BuiltInRegistries.ITEM.wrapAsHolder(templateItem), Component.translatable(Util.makeDescriptionId("trim_pattern", trimPatternKey.location())), false);
        context.register(trimPatternKey, trimPattern);
    }
}
