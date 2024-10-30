package net.jadenxgamer.netherexp.registry.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import com.mojang.datafixers.util.Pair;

import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * WoodSet
 */
public class WoodSet {

    private Map<WoodBlockType, RegistrySupplier<Block>> blocks;
    private Map<WoodBlockType, RegistrySupplier<Item>> items;

    WoodSet(Map<WoodBlockType, RegistrySupplier<Block>> blocks, Map<WoodBlockType, RegistrySupplier<Item>> items) {
        this.blocks = blocks;
        this.items = items;
    }

    public List<RegistrySupplier<Block>> getBlockSuppliers() {
        return List.copyOf(blocks.values());
    }

    public Optional<RegistrySupplier<Block>> getBlockSafe(WoodBlockType type) {
        return Optional.ofNullable(blocks.get(type));
    }

    public RegistrySupplier<Block> getBlock(WoodBlockType type) {
        return getBlockSafe(type).orElseThrow();
    }

    public List<RegistrySupplier<Item>> getItemSuppliers() {
        return List.copyOf(items.values());
    }

    public Optional<RegistrySupplier<Item>> getItemSafe(WoodBlockType type) {
        return Optional.ofNullable(items.get(type));
    }

    public static Builder overworldBuilder(String name, WoodType woodType) {
        return Builder.forWood(name, woodType)
               .addTypes(WoodBlockType.DEFAULT)
               .addTypes(WoodBlockType.LOGS)
               .noItem(
                   WoodBlockType.SIGN,
                   WoodBlockType.WALL_SIGN,
                   WoodBlockType.HANGING_SIGN,
                   WoodBlockType.WALL_HANGING_SIGN
               );
    }

    public static Builder netherBuilder(String name, WoodType woodType) {
        return Builder.forWood(name, woodType)
               .addTypes(WoodBlockType.DEFAULT)
               .addTypes(WoodBlockType.STEMS)
               .noItem(
                   WoodBlockType.SIGN,
                   WoodBlockType.WALL_SIGN,
                   WoodBlockType.HANGING_SIGN,
                   WoodBlockType.WALL_HANGING_SIGN
               );
    }

    public static class Builder {

        private String name;

        private final List<WoodBlockType> types = new ArrayList<>();
        private final Map<WoodBlockType, BlockTemplate> templates = new HashMap<>();
        private final Map<WoodBlockType, Function<String, String>> nameModifiers = new HashMap<>();
        private final Map<WoodBlockType, Consumer<BlockBehaviour.Properties>> propertiesModifiers = new HashMap<>();
        private final Set<WoodBlockType> noItem = new HashSet<>();

        private WoodType woodType;

        private Consumer<Properties> propertiesModifier = p -> {};

        public Builder(String name, WoodType type) {
            this.name = name;
            this.woodType = type;
        }

        public static Builder forWood(String name, WoodType type) {
            return new Builder(name, type);
        }

        public Builder addTypes(List<Pair<WoodBlockType, BlockTemplate>> types) {
            types.forEach(type -> {
                this.types.add(type.getFirst());
                this.templates.put(type.getFirst(), type.getSecond());
            });
            return this;
        }

        public Builder removeTypes(WoodBlockType... types) {
            for (WoodBlockType type : types) {
                this.types.remove(type);
            }
            return this;
        }

        public Builder withNameModifier(WoodBlockType type, Function<String, String> modifier) {
            nameModifiers.put(type, modifier);
            return this;
        }

        public Builder withPropertiesModifier(WoodBlockType type, Consumer<BlockBehaviour.Properties> modifier) {
            propertiesModifiers.put(type, modifier);
            return this;
        }

        public Builder withPropertiesModifier(Consumer<BlockBehaviour.Properties> modifier) {
            this.propertiesModifier = modifier;
            return this;
        }

        public Builder noItem(WoodBlockType... types) {
            for (WoodBlockType type : types) {
                noItem.add(type);
            }
            return this;
        }

        public WoodSet build() {
            if (this.types.contains(WoodBlockType.STAIRS) && !this.types.contains(WoodBlockType.PLANKS)) {
                throw new RuntimeException(new IllegalStateException("To register Stairs, Planks need to be registered too!"));
            }
            Map<WoodBlockType, RegistrySupplier<Block>> blocks = new LinkedHashMap<>();
            Map<WoodBlockType, RegistrySupplier<Item>> items = new LinkedHashMap<>();
            WoodSet woodSet = new WoodSet(blocks, items);
            types.forEach(type -> {
                String id = nameModifiers.getOrDefault(type, s -> s).apply(type.getName(name));
                RegistrySupplier<Block> block = JNEBlocks.BLOCKS.register(id, () -> {
                    BlockBehaviour.Properties properties = templates.get(type).getProperties();
                    propertiesModifier.accept(properties);
                    propertiesModifiers.getOrDefault(type, t -> {}).accept(properties);
                    return type.make(properties, woodType, woodSet.getBlockSafe(WoodBlockType.PLANKS));
                });
                if (!noItem.contains(type)) {
                    items.put(type, JNEItems.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties())));
                }
                blocks.put(type, block);
            });
            return woodSet;
        }

    }

}
