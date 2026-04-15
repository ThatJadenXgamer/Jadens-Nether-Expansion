package net.jadenxgamer.netherexp.core.item.components;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.elysium_api.api.util.RegistryAccessHelper;
import net.jadenxgamer.netherexp.core.datadriven.Antidote;
import net.jadenxgamer.netherexp.registry.JNEDataComponents;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;

public record AntidoteContents(Optional<ResourceLocation> antidote, Optional<Integer> customColor, List<MobEffectInstance> effects) {

    public static final AntidoteContents EMPTY = new AntidoteContents(Optional.empty(), Optional.empty(), List.of());
    private static final int DEFAULT_COLOR = FastColor.ARGB32.color(56, 93, 198);
    private static final Component NO_EFFECT = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);

    public static final Codec<AntidoteContents> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("antidote").forGetter(AntidoteContents::antidote),
            Codec.INT.optionalFieldOf("custom_color").forGetter(AntidoteContents::customColor),
            MobEffectInstance.CODEC.listOf().optionalFieldOf("effects", List.of()).forGetter(AntidoteContents::effects)
    ).apply(instance, AntidoteContents::new));

    private Optional<Antidote> resolveAntidote() {
        return RegistryAccessHelper.getServer()
                .flatMap(registry -> antidote.flatMap(id ->
                        registry.lookupOrThrow(JNERegistries.Keys.ANTIDOTE)
                                .get(ResourceKey.create(JNERegistries.Keys.ANTIDOTE, id))
                                .map(Holder::value)
                ));
    }

    public List<MobEffectInstance> getAllEffects() {
        List<MobEffectInstance> all = new ArrayList<>();
        resolveAntidote().ifPresent(antidote -> all.addAll(antidote.effects()));
        all.addAll(effects);
        return all;
    }

    public void forEachEffect(Consumer<MobEffectInstance> action) {
        resolveAntidote().ifPresent(antidote -> {
            for (MobEffectInstance effect : antidote.effects()) {
                action.accept(new MobEffectInstance(effect));
            }
        });
        for (MobEffectInstance instance : effects) {
            action.accept(new MobEffectInstance(instance));
        }
    }

    public String getName(String descriptionId) {
        return resolveAntidote()
                .map(antidote -> descriptionId + antidote.name())
                .orElse(descriptionId + "empty");
    }

    public void addAntidoteTooltip(Consumer<Component> tooltipAdder, float durationFactor, float ticksPerSecond) {
        addAntidoteTooltip(this.getAllEffects(), tooltipAdder, durationFactor, ticksPerSecond);
    }

    private static void addAntidoteTooltip(Iterable<MobEffectInstance> effects, Consumer<Component> tooltipAdder, float durationFactor, float ticksPerSecond) {
        List<Pair<Holder<Attribute>, AttributeModifier>> attributeModifiers = Lists.newArrayList();
        boolean hasVisibleEffects = false;

        for (MobEffectInstance effect : effects) {
            hasVisibleEffects = true;
            MutableComponent effectName = Component.translatable(effect.getDescriptionId());
            Holder<MobEffect> effectHolder = effect.getEffect();

            effectHolder.value().createModifiers(effect.getAmplifier(), (attribute, modifier) ->
                    attributeModifiers.add(new Pair<>(attribute, modifier)));

            if (effect.getAmplifier() > 0) {
                effectName = Component.translatable("potion.withAmplifier", effectName,
                        Component.translatable("potion.potency." + effect.getAmplifier()));
            }

            if (!effect.endsWithin(20)) {
                effectName = Component.translatable("potion.withDuration", effectName,
                        MobEffectUtil.formatDuration(effect, durationFactor, ticksPerSecond));
            }

            tooltipAdder.accept(effectName.withStyle(effectHolder.value().getCategory().getTooltipFormatting()));
        }

        if (!hasVisibleEffects) tooltipAdder.accept(NO_EFFECT);

        if (!attributeModifiers.isEmpty()) {
            tooltipAdder.accept(CommonComponents.EMPTY);
            tooltipAdder.accept(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

            for (Pair<Holder<Attribute>, AttributeModifier> pair : attributeModifiers) {
                AttributeModifier modifier = pair.getSecond();
                double amount = modifier.amount();
                double displayAmount;
                if (modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE ||
                        modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                    displayAmount = amount * 100.0;
                } else displayAmount = amount;

                if (amount > 0.0) {
                    tooltipAdder.accept(Component.translatable("attribute.modifier.plus." + modifier.operation().id(),
                                    ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(displayAmount),
                                    Component.translatable(pair.getFirst().value().getDescriptionId()))
                            .withStyle(ChatFormatting.BLUE));
                } else if (amount < 0.0) {
                    displayAmount *= -1.0;
                    tooltipAdder.accept(Component.translatable("attribute.modifier.take." + modifier.operation().id(),
                                    ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(displayAmount),
                                    Component.translatable(pair.getFirst().value().getDescriptionId()))
                            .withStyle(ChatFormatting.RED));
                }
            }
        }
    }

    public static int getColor(ItemStack stack) {
        AntidoteContents contents = stack.get(JNEDataComponents.ANTIDOTE_CONTENTS.get());
        if (contents == null) return DEFAULT_COLOR;
        return contents.customColor().orElseGet(() -> getColorFromEffects(contents.getAllEffects()).orElse(DEFAULT_COLOR));
    }

    public static OptionalInt getColorFromEffects(Iterable<MobEffectInstance> effects) {
        int totalRed = 0, totalGreen = 0, totalBlue = 0, totalWeight = 0;

        for (MobEffectInstance effect : effects) {
            if (effect.isVisible()) {
                int color = effect.getEffect().value().getColor();
                int weight = effect.getAmplifier() + 1;
                totalRed += weight * FastColor.ARGB32.red(color);
                totalGreen += weight * FastColor.ARGB32.green(color);
                totalBlue += weight * FastColor.ARGB32.blue(color);
                totalWeight += weight;
            }
        }

        return totalWeight == 0 ? OptionalInt.empty() : OptionalInt.of(FastColor.ARGB32.color(
                totalRed / totalWeight, totalGreen / totalWeight, totalBlue / totalWeight));
    }
}