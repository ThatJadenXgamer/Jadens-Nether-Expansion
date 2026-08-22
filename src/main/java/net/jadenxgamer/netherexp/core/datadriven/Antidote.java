package net.jadenxgamer.netherexp.core.datadriven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.elysium_api.api.util.RegistryAccessHelper;
import net.jadenxgamer.netherexp.core.item.components.AntidoteContents;
import net.jadenxgamer.netherexp.registry.JNEDataComponents;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record Antidote(String name, Optional<Integer> color,  List<MobEffectInstance> effects) {
    public static final Codec<Antidote> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Antidote::name),
            Codec.INT.optionalFieldOf("color").forGetter(Antidote::color),
            MobEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(Antidote::effects)
    ).apply(instance, Antidote::new));

    public static void populateCreativeInventoryForAllAntidotes(CreativeModeTab.Output output) {
        List<ItemStack> stacks = new ArrayList<>();
        RegistryAccess registryAccess = RegistryAccessHelper.getServer().orElse(null);
        if (registryAccess == null) return;

        Registry<Antidote> registry = registryAccess.registryOrThrow(JNERegistries.Keys.ANTIDOTE);
        for (Map.Entry<ResourceKey<Antidote>, Antidote> entry : registry.entrySet()) {
            ResourceLocation antidoteId = entry.getKey().location();
            Antidote antidote = entry.getValue();
            Optional<Integer> customColor = antidote.color();

            ItemStack baseStack = new ItemStack(JNEItems.ANTIDOTE.get());
            baseStack.set(JNEDataComponents.ANTIDOTE_CONTENTS.get(), new AntidoteContents(
                    Optional.of(antidoteId), customColor, List.of()));
            stacks.add(baseStack);

            ItemStack grenadeStack = new ItemStack(JNEItems.GRENADE_ANTIDOTE.get());
            grenadeStack.set(JNEDataComponents.ANTIDOTE_CONTENTS.get(), new AntidoteContents(
                    Optional.of(antidoteId), customColor, List.of()));
            stacks.add(grenadeStack);
        }
        output.acceptAll(stacks);
    }
}