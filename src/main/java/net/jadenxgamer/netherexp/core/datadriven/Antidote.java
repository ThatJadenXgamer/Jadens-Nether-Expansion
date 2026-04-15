package net.jadenxgamer.netherexp.core.datadriven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.core.item.components.AntidoteContents;
import net.jadenxgamer.netherexp.registry.JNEDataComponents;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Antidote(String name, List<MobEffectInstance> effects) {
    public static final Codec<Antidote> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Antidote::name),
            MobEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(Antidote::effects)
    ).apply(instance, Antidote::new));

    public static void createStacksForAllAntidotes(BuildCreativeModeTabContentsEvent event) {
        List<ItemStack> stacks = new ArrayList<>();
        var connection = Minecraft.getInstance().getConnection();
        if (connection == null) return;

        var registries = connection.registryAccess();
        var antidoteRegistry = registries.lookup(JNERegistries.Keys.ANTIDOTE);
        if (antidoteRegistry.isEmpty()) return;

        for (var entry : antidoteRegistry.get().listElements().toList()) {
            ResourceLocation antidoteId = entry.key().location();
            ItemStack stack = new ItemStack(JNEItems.ANTIDOTE.get());
            AntidoteContents contents = new AntidoteContents(
                    Optional.of(antidoteId),
                    Optional.empty(),
                    List.of()
            );
            stack.set(JNEDataComponents.ANTIDOTE_CONTENTS.get(), contents);
            stacks.add(stack);
        }
        event.acceptAll(stacks);
    }
}