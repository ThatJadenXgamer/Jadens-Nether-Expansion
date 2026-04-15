package net.jadenxgamer.netherexp.core.datadriven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.Optional;

@SuppressWarnings("deprecation")
public record OnDeathGroundConversion(HolderSet<EntityType<?>> entityTypes,
                                      HolderSet<Block> groundBlock, BlockState conversionBlock) {

    public static final Codec<OnDeathGroundConversion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("entity_types").forGetter(OnDeathGroundConversion::entityTypes),
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("ground_block").forGetter(OnDeathGroundConversion::groundBlock),
            BlockState.CODEC.fieldOf("conversion_block").forGetter(OnDeathGroundConversion::conversionBlock)
    ).apply(instance, OnDeathGroundConversion::new));

    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide) return;
        Holder<EntityType<?>> holderEntity = getAsHolder(entity.getType());
        BlockPos pos = entity.getOnPos();
        BlockState conversionBlock = entity.getBlockStateOn();
        Optional<OnDeathGroundConversion> onDeathGroundConversion = level.registryAccess().registryOrThrow(JNERegistries.Keys.ON_DEATH_GROUND_CONVERSION).stream()
                .filter(json -> json.entityTypes.contains(holderEntity) && json.groundBlock.contains(conversionBlock.getBlockHolder())).findFirst();

        if (onDeathGroundConversion.isEmpty()) return;

        level.playSound(null, pos, conversionBlock.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
        level.setBlock(pos, onDeathGroundConversion.get().conversionBlock(), Block.UPDATE_ALL);
    }

    private static Holder<EntityType<?>> getAsHolder(EntityType<?> entity) {
        Optional<ResourceKey<EntityType<?>>> key = BuiltInRegistries.ENTITY_TYPE.getResourceKey(entity);
        return key.map(BuiltInRegistries.ENTITY_TYPE::getHolderOrThrow).orElse(null);
    }
}