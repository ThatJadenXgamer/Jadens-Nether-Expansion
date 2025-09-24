package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;

public class JNETrimPatterns {
    public static final ResourceKey<TrimPattern> RIFT = registerKey("rift");
    public static final ResourceKey<TrimPattern> SPIRIT = registerKey("spirit");
    public static final ResourceKey<TrimPattern> VALOR = registerKey("valor");

    private static ResourceKey<TrimPattern> registerKey(String name) {
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
