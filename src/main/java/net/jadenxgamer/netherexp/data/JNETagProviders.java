package net.jadenxgamer.netherexp.data;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNEDamageTypes;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public interface JNETagProviders {

    final class BlockTagProvider extends BlockTagsProvider {

        public BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, NetherExp.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            tag(JNETags.Blocks.Sounds.BLACKSTONE).add(Blocks.BLACKSTONE, Blocks.BLACKSTONE_STAIRS, Blocks.BLACKSTONE_SLAB, Blocks.BLACKSTONE_WALL);
            tag(JNETags.Blocks.Sounds.GLOWSTONE).add(Blocks.GLOWSTONE, Blocks.REDSTONE_LAMP);
            tag(JNETags.Blocks.Sounds.MAGMA_BLOCK).add(Blocks.MAGMA_BLOCK).addOptional(ResourceLocation.fromNamespaceAndPath("rubinated_nether", "molten_ruby_ore"));
            tag(JNETags.Blocks.Sounds.POLISHED_BLACKSTONE_BRICKS).add(Blocks.CHISELED_POLISHED_BLACKSTONE, JNEBlocks.POLISHED_BLACKSTONE_PILLAR.get(), Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_STAIRS, Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE_WALL, JNEBlocks.POLISHED_BLACKSTONE_FENCE.get(), Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE, Blocks.POLISHED_BLACKSTONE_BUTTON, Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_WALL, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_WALL.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_STAIRS.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_WALL.get());
            tag(JNETags.Blocks.Sounds.QUARTZ_BLOCK).add(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_STAIRS, Blocks.QUARTZ_SLAB, Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BRICKS, JNEBlocks.CRACKED_QUARTZ_BRICKS.get(), Blocks.QUARTZ_PILLAR, JNEBlocks.CHISELED_QUARTZ_PILLAR.get(), Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.SMOOTH_QUARTZ_STAIRS);
            tag(JNETags.Blocks.ANCIENT_FIRE_BASE_BLOCKS).add(JNEBlocks.ANCIENT_WAX_BLOCK.get());
            tag(JNETags.Blocks.BLACK_ICE_REPLACEABLE).add(Blocks.NETHERRACK, Blocks.BASALT, Blocks.BLACKSTONE, Blocks.SOUL_SAND, Blocks.SOUL_SOIL, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.SOUL_PERMAFROST.get(), JNEBlocks.THIN_BLACK_ICE.get());
            tag(JNETags.Blocks.BLACK_ICES).add(JNEBlocks.BLACK_ICE.get(), JNEBlocks.THIN_BLACK_ICE.get());
            tag(JNETags.Blocks.BONE_FENCES).add(JNEBlocks.BONE_FENCE.get());
            tag(JNETags.Blocks.CLARET_STEMS).add(JNEBlocks.CEREBRAGE_CLARET_STEM.get(), JNEBlocks.CEREBRAGE_CLARET_HYPHAE.get(), JNEBlocks.STRIPPED_CLARET_STEM.get(), JNEBlocks.STRIPPED_CLARET_HYPHAE.get());
            tag(JNETags.Blocks.FROGMISTS).add(JNEBlocks.OCHRE_FROGMIST.get(), JNEBlocks.VERDANT_FROGMIST.get(), JNEBlocks.PEARLESCENT_FROGMIST.get()).addOptional(NetherExp.id("carmine_frogmist"));
            tag(JNETags.Blocks.MAGMA_BLOCKS).add(Blocks.MAGMA_BLOCK, JNEBlocks.SOUL_MAGMA_BLOCK.get());
            tag(JNETags.Blocks.MOB_HEADS).add(Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_EGG, Blocks.SKELETON_WALL_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL, Blocks.ZOMBIE_WALL_HEAD, Blocks.PLAYER_WALL_HEAD, Blocks.CREEPER_WALL_HEAD, Blocks.DRAGON_WALL_HEAD, JNEBlocks.SKELETON_SKULL_CANDLE.get(), JNEBlocks.SOUL_SKELETON_SKULL_CANDLE.get(), JNEBlocks.CEREBRAGE_SKULL.get());
            tag(JNETags.Blocks.MOUND_BLOCKS).add(Blocks.SOUL_SAND, Blocks.SOUL_SOIL, JNEBlocks.PALE_SOUL_SLATE.get());
            tag(JNETags.Blocks.SHROOMLIGHTS).add(Blocks.SHROOMLIGHT, JNEBlocks.SHROOMNIGHT.get());
            tag(JNETags.Blocks.SORROWEED_REPLACEABLE).add(Blocks.SOUL_SAND, Blocks.SOUL_SOIL, JNEBlocks.ECTO_SOUL_SAND.get());
            tag(JNETags.Blocks.SOUL_CROP_MUTATION_BLOCKS).add(Blocks.SOUL_SAND, JNEBlocks.ECTO_SOUL_SAND.get(), JNEBlocks.SORROWEED.get());
            tag(JNETags.Blocks.SOUL_LAYER_CAN_SURVIVE_ON).add(Blocks.HONEY_BLOCK, Blocks.SOUL_SAND, JNEBlocks.ECTO_SOUL_SAND.get(), Blocks.MUD);
            tag(JNETags.Blocks.SOUL_LAYER_CANNOT_SURVIVE_ON).add(Blocks.BARRIER, JNEBlocks.BASALTIC_GEYSER.get(), JNEBlocks.SOULED_GEYSER.get());
            tag(JNETags.Blocks.SOUL_SANDS).add(Blocks.SOUL_SAND, JNEBlocks.ECTO_SOUL_SAND.get());
            tag(JNETags.Blocks.SOUL_SLATE_REPLACEABLE).add(Blocks.SOUL_SAND, Blocks.SOUL_SOIL, JNEBlocks.ECTO_SOUL_SAND.get());
            tag(JNETags.Blocks.UNBOUNDED_SPEED_BLOCKS).add(Blocks.SOUL_SAND, JNEBlocks.ECTO_SOUL_SAND.get(), JNEBlocks.SOUL_PERMAFROST.get());
            tag(JNETags.Blocks.WART_BEARD_FEATURE_VALID).add(Blocks.WARPED_WART_BLOCK);
            tag(JNETags.Blocks.WEEPING_VINES_FEATURE_VALID).add(Blocks.NETHERRACK, Blocks.NETHER_WART_BLOCK);
            tag(JNETags.Blocks.GEYSERS).add(JNEBlocks.BASALTIC_GEYSER.get(), JNEBlocks.SOULED_GEYSER.get());

            // Minecraft Tags
            tag(BlockTags.MINEABLE_WITH_AXE).add(JNEBlocks.CEREBRAGE_CLARET_STEM.get(), JNEBlocks.CEREBRAGE_CLARET_HYPHAE.get(), JNEBlocks.STRIPPED_CLARET_STEM.get(), JNEBlocks.STRIPPED_CLARET_HYPHAE.get(), JNEBlocks.CLARET_PLANKS.get(), JNEBlocks.CLARET_SLAB.get(), JNEBlocks.CLARET_STAIRS.get(), JNEBlocks.CLARET_FENCE.get(), JNEBlocks.CLARET_FENCE_GATE.get(), JNEBlocks.CLARET_DOOR.get(), JNEBlocks.CLARET_TRAPDOOR.get(), JNEBlocks.CLARET_BUTTON.get(), JNEBlocks.CLARET_PRESSURE_PLATE.get(), JNEBlocks.SORROWSQUASH.get(), JNEBlocks.CARVED_SORROWSQUASH.get(), JNEBlocks.GHOUL_O_LANTERN.get(), JNEBlocks.ANCIENT_CAMPFIRE.get());
            tag(BlockTags.MINEABLE_WITH_HOE).add(JNEBlocks.SORROWEED.get(), JNEBlocks.SHROOMNIGHT.get(), JNEBlocks.CRIMSON_SPORESHROOM.get(), JNEBlocks.WARPED_SPORESHROOM.get(), JNEBlocks.OCHRE_FROGMIST.get(), JNEBlocks.VERDANT_FROGMIST.get(), JNEBlocks.PEARLESCENT_FROGMIST.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(JNEBlocks.SOUL_SLATE.get(), JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_SLAB.get(), JNEBlocks.SOUL_SLATE_STAIRS.get(), JNEBlocks.SOUL_SLATE_WALL.get(), JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_SLAB.get(), JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), JNEBlocks.SOUL_SLATE_BRICK_WALL.get(), JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get(), JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get(), JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILE_SLAB.get(), JNEBlocks.SOUL_SLATE_TILE_STAIRS.get(), JNEBlocks.SOUL_SLATE_TILE_WALL.get(), JNEBlocks.ETCHED_SOUL_SLATE_TILES.get(), JNEBlocks.CHISELED_SOUL_SLATE_TILES.get(), JNEBlocks.INSCRIBED_PANEL.get(), JNEBlocks.SHOTGUN_BARREL.get(), JNEBlocks.BLACK_ICE.get(), JNEBlocks.BLACK_ICICLE.get(), JNEBlocks.THIN_BLACK_ICE.get(), JNEBlocks.SOUL_PERMAFROST.get(), JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.SMOOTH_NETHERRACK_SLAB.get(), JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get(), JNEBlocks.SMOOTH_NETHERRACK_WALL.get(), JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.NETHERRACK_BRICK_SLAB.get(), JNEBlocks.NETHERRACK_BRICK_STAIRS.get(), JNEBlocks.NETHERRACK_BRICK_WALL.get(), JNEBlocks.NETHERRACK_TILES.get(), JNEBlocks.NETHERRACK_PILLAR.get(), JNEBlocks.BASALT_SLAB.get(), JNEBlocks.BASALT_STAIRS.get(), JNEBlocks.BASALT_WALL.get(), JNEBlocks.POLISHED_BASALT_SLAB.get(), JNEBlocks.POLISHED_BASALT_STAIRS.get(), JNEBlocks.POLISHED_BASALT_WALL.get(), JNEBlocks.POLISHED_BASALT_BRICKS.get(), JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get(), JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get(), JNEBlocks.POLISHED_BASALT_BRICK_WALL.get(), JNEBlocks.NETHERITE_PLATED_BLOCK.get(), JNEBlocks.NETHERITE_GRATE.get(), JNEBlocks.CUT_NETHERITE_BLOCK.get(), JNEBlocks.CUT_NETHERITE_SLAB.get(), JNEBlocks.CUT_NETHERITE_STAIRS.get(), JNEBlocks.CUT_NETHERITE_PILLAR.get(), JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get(), JNEBlocks.RUSTY_NETHERITE_GRATE.get(), JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get(), JNEBlocks.RUSTY_CUT_NETHERITE_SLAB.get(), JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS.get(), JNEBlocks.RUSTY_CUT_NETHERITE_PILLAR.get(), JNEBlocks.QUARTZ_CRYSTAL_BLOCK.get(), JNEBlocks.QUARTZ_CRYSTAL.get(), JNEBlocks.CRACKED_QUARTZ_BRICKS.get(), JNEBlocks.CHISELED_QUARTZ_PILLAR.get(), JNEBlocks.NETHER_BRICK_PILLAR.get(), JNEBlocks.RED_MIXED_NETHER_BRICKS.get(), JNEBlocks.BLUE_MIXED_NETHER_BRICKS.get(), JNEBlocks.BLUE_NETHER_BRICKS.get(), JNEBlocks.BLUE_NETHER_BRICK_SLAB.get(), JNEBlocks.BLUE_NETHER_BRICK_STAIRS.get(), JNEBlocks.BLUE_NETHER_BRICK_WALL.get(), JNEBlocks.BONE_PIKE.get(), JNEBlocks.BONE_FENCE.get(), JNEBlocks.STACKED_BONES.get(), JNEBlocks.STACKED_BONE_SLAB.get(), JNEBlocks.STACKED_BONE_STAIRS.get(), JNEBlocks.SKULL_BLOCK.get(), JNEBlocks.BURNING_SKULL_BLOCK.get(), JNEBlocks.SOUL_BURNING_SKULL_BLOCK.get(), JNEBlocks.ANCIENT_BURNING_SKULL_BLOCK.get(), JNEBlocks.WITHER_BONE_BLOCK.get(), JNEBlocks.STACKED_WITHER_BONES.get(), JNEBlocks.STACKED_WITHER_BONE_SLAB.get(), JNEBlocks.STACKED_WITHER_BONE_STAIRS.get(), JNEBlocks.WITHER_SKULL_BLOCK.get(), JNEBlocks.BURNING_WITHER_SKULL_BLOCK.get(), JNEBlocks.SOUL_BURNING_WITHER_SKULL_BLOCK.get(), JNEBlocks.ANCIENT_BURNING_WITHER_SKULL_BLOCK.get(), JNEBlocks.CRIMSON_NYLIUM_PATH.get(), JNEBlocks.WARPED_NYLIUM_PATH.get(), JNEBlocks.SOULED_GEYSER.get(), JNEBlocks.BASALTIC_GEYSER.get(), JNEBlocks.POLISHED_BLACKSTONE_PILLAR.get(), JNEBlocks.POLISHED_BLACKSTONE_FENCE.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_WALL.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_STAIRS.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_WALL.get(), Blocks.GLOWSTONE, Blocks.REDSTONE_LAMP, JNEBlocks.ANCIENT_LANTERN.get()).addTag(JNETags.Blocks.MOB_HEADS);
            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(JNEBlocks.ECTO_SOUL_SAND.get(), JNEBlocks.SOUL_MAGMA_BLOCK.get(), JNEBlocks.SOUL_SOIL_LAYER.get(), JNEBlocks.SOUL_PATH.get(), JNEBlocks.FOSSIL_ORE.get(), JNEBlocks.FOSSIL_FUEL_ORE.get(), JNEBlocks.SUSPICIOUS_SOUL_SAND.get(), JNEBlocks.SOUL_PERMAFROST.get());
            tag(BlockTags.BASE_STONE_NETHER).add(JNEBlocks.SOUL_SLATE.get(), JNEBlocks.PALE_SOUL_SLATE.get());
            tag(BlockTags.CAMPFIRES).add(JNEBlocks.ANCIENT_CAMPFIRE.get());
            tag(BlockTags.CANDLES).add(JNEBlocks.SOUL_CANDLE.get(), JNEBlocks.ANCIENT_CANDLE.get());
            tag(BlockTags.CEILING_HANGING_SIGNS).add(JNEBlocks.CLARET_HANGING_SIGN.get());
            tag(BlockTags.CLIMBABLE).add(JNEBlocks.SORROWSQUASH_STEM.get(), JNEBlocks.SORROWSQUASH_STEM_PLANT.get());
            tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(JNEBlocks.CRIMSON_SPROUTS.get(), JNEBlocks.BONE_PIKE.get(), JNEBlocks.SOUL_SWIRLS.get(), JNEBlocks.SOUL_SOIL_LAYER.get());
            tag(BlockTags.CROPS).add(JNEBlocks.SORROWSQUASH_STEM.get(), JNEBlocks.SORROWSQUASH_STEM_PLANT.get(), JNEBlocks.SOUL_TORCHFLOWER_CROP.get());
            tag(BlockTags.FENCE_GATES).add(JNEBlocks.CLARET_FENCE_GATE.get());
            tag(BlockTags.FENCES).add(JNEBlocks.CLARET_FENCE.get());
            tag(BlockTags.FLOWER_POTS).add(JNEBlocks.POTTED_SOUL_SWIRLS.get(), JNEBlocks.POTTED_CRIMSON_SPORESHROOM.get(), JNEBlocks.POTTED_WARPED_SPORESHROOM.get(), JNEBlocks.POTTED_SOUL_TORCHFLOWER.get());
            tag(BlockTags.HOGLIN_REPELLENTS).add(JNEBlocks.WARPED_SPORESHROOM.get());
            tag(BlockTags.ICE).addTag(JNETags.Blocks.BLACK_ICES);
            tag(BlockTags.INFINIBURN_END).add(JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.SMOOTH_NETHERRACK_SLAB.get(), JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get(), JNEBlocks.SMOOTH_NETHERRACK_WALL.get(), JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.NETHERRACK_BRICK_SLAB.get(), JNEBlocks.NETHERRACK_BRICK_STAIRS.get(), JNEBlocks.NETHERRACK_BRICK_WALL.get(), JNEBlocks.NETHERRACK_TILES.get(), JNEBlocks.NETHERRACK_PILLAR.get());
            tag(BlockTags.INFINIBURN_NETHER).add(JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.SMOOTH_NETHERRACK_SLAB.get(), JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get(), JNEBlocks.SMOOTH_NETHERRACK_WALL.get(), JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.NETHERRACK_BRICK_SLAB.get(), JNEBlocks.NETHERRACK_BRICK_STAIRS.get(), JNEBlocks.NETHERRACK_BRICK_WALL.get(), JNEBlocks.NETHERRACK_TILES.get(), JNEBlocks.NETHERRACK_PILLAR.get());
            tag(BlockTags.INFINIBURN_OVERWORLD).add(JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.SMOOTH_NETHERRACK_SLAB.get(), JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get(), JNEBlocks.SMOOTH_NETHERRACK_WALL.get(), JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.NETHERRACK_BRICK_SLAB.get(), JNEBlocks.NETHERRACK_BRICK_STAIRS.get(), JNEBlocks.NETHERRACK_BRICK_WALL.get(), JNEBlocks.NETHERRACK_TILES.get(), JNEBlocks.NETHERRACK_PILLAR.get());
            tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(JNEBlocks.CRIMSON_SPROUTS.get(), JNEBlocks.BONE_PIKE.get(), JNEBlocks.SOUL_SWIRLS.get(), JNEBlocks.SOUL_SOIL_LAYER.get());
            tag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE).add(JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_SLAB.get(), JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), JNEBlocks.SOUL_SLATE_BRICK_WALL.get(), JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get(), JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILE_SLAB.get(), JNEBlocks.SOUL_SLATE_TILE_STAIRS.get(), JNEBlocks.SOUL_SLATE_TILE_WALL.get(), JNEBlocks.CHISELED_SOUL_SLATE_TILES.get(), JNEBlocks.ETCHED_SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_CANDLE.get(), JNEBlocks.SOUL_PATH.get());
            tag(BlockTags.LOGS).addTag(JNETags.Blocks.CLARET_STEMS);
            tag(BlockTags.NEEDS_IRON_TOOL).add(JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_SLAB.get(), JNEBlocks.SOUL_SLATE_STAIRS.get(), JNEBlocks.SOUL_SLATE_WALL.get(), JNEBlocks.SOULED_GEYSER.get(), JNEBlocks.WITHER_BONE_BLOCK.get());
            tag(BlockTags.NETHER_CARVER_REPLACEABLES).add(JNEBlocks.SORROWEED.get(), JNEBlocks.SOUL_SLATE.get(), JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.ECTO_SOUL_SAND.get(), JNEBlocks.SOUL_MAGMA_BLOCK.get(), JNEBlocks.SOULED_GEYSER.get());
            tag(BlockTags.PIGLIN_REPELLENTS).add(JNEBlocks.SOUL_CANDLE.get(), JNEBlocks.SOUL_SKELETON_SKULL_CANDLE.get(), JNEBlocks.GHOUL_O_LANTERN.get());
            tag(BlockTags.PLANKS).add(JNEBlocks.CLARET_PLANKS.get());
            tag(BlockTags.REPLACEABLE).add(JNEBlocks.SOUL_SWIRLS.get());
            tag(BlockTags.SLABS).add(JNEBlocks.SOUL_SLATE_SLAB.get(), JNEBlocks.SOUL_SLATE_BRICK_SLAB.get(), JNEBlocks.SOUL_SLATE_TILE_SLAB.get(), JNEBlocks.SMOOTH_NETHERRACK_SLAB.get(), JNEBlocks.NETHERRACK_BRICK_SLAB.get(), JNEBlocks.BASALT_SLAB.get(), JNEBlocks.POLISHED_BASALT_SLAB.get(), JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get(), JNEBlocks.BLUE_NETHER_BRICK_SLAB.get(), JNEBlocks.STACKED_BONE_SLAB.get(), JNEBlocks.STACKED_WITHER_BONE_SLAB.get(), JNEBlocks.CUT_NETHERITE_SLAB.get(), JNEBlocks.RUSTY_CUT_NETHERITE_SLAB.get());
            tag(BlockTags.SMALL_FLOWERS).add(JNEBlocks.SOUL_TORCHFLOWER.get());
            tag(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON).add(JNEBlocks.ECTO_SOUL_SAND.get());
            tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON).addTag(JNETags.Blocks.GEYSERS);
            tag(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(JNEBlocks.SORROWEED.get(), JNEBlocks.SOUL_SOIL_LAYER.get(), JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_SLAB.get(), JNEBlocks.SOUL_SLATE_STAIRS.get(), JNEBlocks.SOUL_SLATE_WALL.get(), JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_SLAB.get(), JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), JNEBlocks.SOUL_SLATE_BRICK_WALL.get(), JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get(), JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get(), JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILE_SLAB.get(), JNEBlocks.SOUL_SLATE_TILE_STAIRS.get(), JNEBlocks.SOUL_SLATE_TILE_WALL.get(), JNEBlocks.ETCHED_SOUL_SLATE_TILES.get(), JNEBlocks.CHISELED_SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_GLASS.get(), JNEBlocks.ECTO_SOUL_SAND.get(), JNEBlocks.SOUL_MAGMA_BLOCK.get(), JNEBlocks.SOULED_GEYSER.get(), JNEBlocks.BLACK_ICE.get(), JNEBlocks.THIN_BLACK_ICE.get(), JNEBlocks.FOSSIL_ORE.get(), JNEBlocks.FOSSIL_FUEL_ORE.get(), JNEBlocks.SOUL_PATH.get(), JNEBlocks.WRAITHING_LESION.get(), JNEBlocks.SOUL_PERMAFROST.get());
            tag(BlockTags.SOUL_SPEED_BLOCKS).add(JNEBlocks.SORROWEED.get(), JNEBlocks.SOUL_SOIL_LAYER.get(), JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_SLAB.get(), JNEBlocks.SOUL_SLATE_STAIRS.get(), JNEBlocks.SOUL_SLATE_WALL.get(), JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_SLAB.get(), JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), JNEBlocks.SOUL_SLATE_BRICK_WALL.get(), JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get(), JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get(), JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILE_SLAB.get(), JNEBlocks.SOUL_SLATE_TILE_STAIRS.get(), JNEBlocks.SOUL_SLATE_TILE_WALL.get(), JNEBlocks.ETCHED_SOUL_SLATE_TILES.get(), JNEBlocks.CHISELED_SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_GLASS.get(), JNEBlocks.ECTO_SOUL_SAND.get(), JNEBlocks.SOUL_MAGMA_BLOCK.get(), JNEBlocks.SOULED_GEYSER.get(), JNEBlocks.BLACK_ICE.get(), JNEBlocks.THIN_BLACK_ICE.get(), JNEBlocks.FOSSIL_ORE.get(), JNEBlocks.FOSSIL_FUEL_ORE.get(), JNEBlocks.SOUL_PATH.get(), JNEBlocks.WRAITHING_LESION.get(), JNEBlocks.SOUL_PERMAFROST.get());
            tag(BlockTags.STAIRS).add(JNEBlocks.SOUL_SLATE_STAIRS.get(), JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), JNEBlocks.SOUL_SLATE_TILE_STAIRS.get(), JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get(), JNEBlocks.NETHERRACK_BRICK_STAIRS.get(), JNEBlocks.BLUE_NETHER_BRICK_STAIRS.get(), JNEBlocks.STACKED_BONE_STAIRS.get(), JNEBlocks.STACKED_WITHER_BONE_STAIRS.get(), JNEBlocks.BASALT_STAIRS.get(), JNEBlocks.POLISHED_BASALT_STAIRS.get(), JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get(), JNEBlocks.CUT_NETHERITE_STAIRS.get(), JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS.get());
            tag(BlockTags.STANDING_SIGNS).add(JNEBlocks.CLARET_SIGN.get());
            tag(BlockTags.SWORD_EFFICIENT).add(JNEBlocks.SORROWSQUASH_STEM.get(), JNEBlocks.SORROWSQUASH_STEM_PLANT.get(), JNEBlocks.SORROWSQUASH.get(), JNEBlocks.CARVED_SORROWSQUASH.get(), JNEBlocks.GHOUL_O_LANTERN.get());
            tag(BlockTags.WALL_HANGING_SIGNS).add(JNEBlocks.CLARET_WALL_HANGING_SIGN.get());
            tag(BlockTags.WALL_SIGNS).add(JNEBlocks.CLARET_WALL_SIGN.get());
            tag(BlockTags.WALLS).add(JNEBlocks.SOUL_SLATE_WALL.get(), JNEBlocks.SOUL_SLATE_BRICK_WALL.get(), JNEBlocks.SOUL_SLATE_TILE_WALL.get(), JNEBlocks.BLUE_NETHER_BRICK_WALL.get(), JNEBlocks.SMOOTH_NETHERRACK_WALL.get(), JNEBlocks.NETHERRACK_BRICK_WALL.get(), JNEBlocks.BASALT_WALL.get(), JNEBlocks.POLISHED_BASALT_WALL.get(), JNEBlocks.POLISHED_BASALT_BRICK_WALL.get());
            tag(BlockTags.WITHER_SUMMON_BASE_BLOCKS).add(JNEBlocks.SORROWEED.get(), JNEBlocks.ECTO_SOUL_SAND.get(), JNEBlocks.SOUL_PERMAFROST.get());
            tag(BlockTags.WOODEN_BUTTONS).add(JNEBlocks.CLARET_BUTTON.get());
            tag(BlockTags.WOODEN_DOORS).add(JNEBlocks.CLARET_DOOR.get());
            tag(BlockTags.WOODEN_FENCES).add(JNEBlocks.CLARET_FENCE.get());
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(JNEBlocks.CLARET_PRESSURE_PLATE.get());
            tag(BlockTags.WOODEN_SLABS).add(JNEBlocks.CLARET_SLAB.get());
            tag(BlockTags.WOODEN_STAIRS).add(JNEBlocks.CLARET_STAIRS.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(JNEBlocks.CLARET_TRAPDOOR.get());
        }
    }

    final class DamageTypeTagProvider extends DamageTypeTagsProvider {

        public DamageTypeTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, NetherExp.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(JNETags.DamageTypes.CAN_DISRUPT_UNDERGROUND_ECTO_SLABS).add(DamageTypes.EXPLOSION, DamageTypes.ARROW, DamageTypes.TRIDENT);
            tag(JNETags.DamageTypes.IS_SUFFOCATION);
            tag(JNETags.DamageTypes.OVERKILL_VALID).add(DamageTypes.PLAYER_ATTACK);

            tag(DamageTypeTags.BYPASSES_ARMOR).add(JNEDamageTypes.SHOTGUN_EXPLOSION);
            tag(DamageTypeTags.BYPASSES_COOLDOWN).add(JNEDamageTypes.SHOTGUN_PELLET);
            tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(JNEDamageTypes.SHOTGUN_PELLET, JNEDamageTypes.SHOTGUN_EXPLOSION);
            tag(DamageTypeTags.BYPASSES_SHIELD).add(JNEDamageTypes.SHOTGUN_EXPLOSION);
            tag(DamageTypeTags.IS_EXPLOSION).add(JNEDamageTypes.SHOTGUN_EXPLOSION);
            tag(DamageTypeTags.IS_PROJECTILE).add(JNEDamageTypes.SHOTGUN_PELLET, JNEDamageTypes.WILL_O_WISP);
            tag(DamageTypeTags.NO_KNOCKBACK).add(JNEDamageTypes.SHOTGUN_PELLET, JNEDamageTypes.SHOTGUN_EXPLOSION);
        }
    }

    final class EntityTypeTagProvider extends EntityTypeTagsProvider {

        public EntityTypeTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, NetherExp.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(JNETags.EntityTypes.CAN_PHASE_THROUGH_SOUL_GLASS).add(EntityType.PLAYER, JNEEntityType.WISP.get(), JNEEntityType.APPARITION.get(), EntityType.ENDERMAN, EntityType.ENDERMITE).addTag(EntityTypeTags.IMPACT_PROJECTILES);
            tag(JNETags.EntityTypes.CANT_ACTIVATE_SWIRLS).add(JNEEntityType.WISP.get(), JNEEntityType.APPARITION.get()).addTag(JNETags.EntityTypes.POSSESSED);
            tag(JNETags.EntityTypes.CANT_SHATTER_THIN_BLACK_ICE).add(EntityType.STRAY, EntityType.RABBIT, EntityType.SILVERFISH, EntityType.ENDERMITE, EntityType.COD, EntityType.SALMON, EntityType.PUFFERFISH, EntityType.TROPICAL_FISH, EntityType.TADPOLE, EntityType.ARMADILLO, EntityType.ALLAY, EntityType.VEX, EntityType.CAT, EntityType.CHICKEN, EntityType.PARROT, EntityType.FROG, EntityType.SHULKER, EntityType.BLAZE, EntityType.BREEZE, EntityType.WITHER, EntityType.ENDER_DRAGON, JNEEntityType.WISP.get());
            tag(JNETags.EntityTypes.ECTO_SLAB_POUNCE_DAMAGES).add(EntityType.PLAYER, EntityType.PIGLIN, EntityType.IRON_GOLEM);
            tag(JNETags.EntityTypes.FOSSIL_FUEL_ORE_CONVERTING_SKELETONS).add(EntityType.WITHER_SKELETON);
            tag(JNETags.EntityTypes.FOSSIL_ORE_CONVERTING_SKELETONS).addTag(EntityTypeTags.SKELETONS).remove(EntityType.WITHER_SKELETON);
            tag(JNETags.EntityTypes.IGNORES_BLOCK_COLLISION)
                    // TODO: IMPLEMENT PHASMO ARROW
                    // .add(JNEEntityType.PHASMO_ARROW.get())
            ;
            tag(JNETags.EntityTypes.IGNORES_SOUL_SAND_SLOWNESS).addTag(JNETags.EntityTypes.POSSESSED);
            tag(JNETags.EntityTypes.INGORES_TREACHEROUS_CANDLE).add(EntityType.WITHER, EntityType.WARDEN, EntityType.ENDER_DRAGON, EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER, EntityType.WANDERING_TRADER, EntityType.ELDER_GUARDIAN, JNEEntityType.WISP.get(), EntityType.CAT, EntityType.WOLF, EntityType.PARROT, EntityType.ALLAY, EntityType.HORSE, EntityType.CAMEL, EntityType.DONKEY, EntityType.MULE, EntityType.AXOLOTL, EntityType.SKELETON_HORSE, EntityType.ZOMBIE_HORSE, EntityType.FOX);
            tag(JNETags.EntityTypes.PHANTASM_HULL_PROTECTS_BLACKLIST).add(
                    // TODO: IMPLEMENT PHASMO ARROW
                    // JNEEntityType.PHASMO_ARROW.get(),
                    EntityType.DRAGON_FIREBALL,
                    EntityType.WITHER_SKULL);
            tag(JNETags.EntityTypes.POSSESSED).add(
                    JNEEntityType.VESSEL.get()
                    /*
                    ,
                    TODO: IMPLEMET ENTITIES
                    JNEEntityType.ECTO_SLAB.get(),
                    JNEEntityType.BANSHEE.get(),
                    JNEEntityType.STAMPEDE.get()
                    */
            );
            tag(JNETags.EntityTypes.PROJECTILES_PASS_THROUGH).add(JNEEntityType.WISP.get(), JNEEntityType.APPARITION.get());
            tag(JNETags.EntityTypes.STAMPEDE_CANNOT_RUN_OVER).add(
                    // TODO: IMPLEMENT STAMPEDE
                    // JNEEntityType.STAMPEDE.get()
                    EntityType.STRIDER,
                    EntityType.WITHER,
                    EntityType.ENDER_DRAGON,
                    EntityType.SILVERFISH,
                    EntityType.ENDERMITE
            );
            tag(JNETags.EntityTypes.TARGET_REGARDLESS_OF_BETRAYED).add(
                    // TODO: IMPLEMENT ECTO SLAB
                    // JNEEntityType.ECTO_SLAB.get()
                    EntityType.MAGMA_CUBE,
                    EntityType.SLIME
            );
            // TODO: IMPLEMENT PHAMO ARROW
            // tag(EntityTypeTags.ARROWS).add(JNEEntityType.PHASMO_ARROW.get());
            // TODO: IMPLEMENT ENTITIES
            // tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(JNEEntityType.ECTO_SLAB.get(), JNEEntityType.STAMPEDE.get(), JNEEntityType.BANSHEE.get());
            tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(JNEEntityType.APPARITION.get(), JNEEntityType.WISP.get()).addTag(JNETags.EntityTypes.POSSESSED);
            // TODO: IMPLEMENT ECTO SLAB
            // tag(EntityTypeTags.FROG_FOOD).addTag(JNEEntityType.ECTO_SLAB.get())
            tag(EntityTypeTags.SKELETONS).add(JNEEntityType.VESSEL.get());
            tag(EntityTypeTags.UNDEAD).add(JNEEntityType.WISP.get(), JNEEntityType.APPARITION.get());
        }
    }

    final class FluidTypeTagProvider extends FluidTagsProvider {

        public FluidTypeTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, NetherExp.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(JNETags.Fluids.ECTOPLASM).add(JNEFluids.ECTOPLASM_SOURCE.get(), JNEFluids.ECTOPLASM_FLOWING.get());
            tag(JNETags.Fluids.TURNS_TO_BLACK_ICE).add(Fluids.WATER, Fluids.FLOWING_WATER);
        }
    }

    final class ItemTagProvider extends ItemTagsProvider {

        public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
            super(output, lookupProvider, blockTags);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(JNETags.Items.ARTIFACTS).add(JNEItems.SHOTGUN_CORE.get());
            copy(JNETags.Blocks.CLARET_STEMS, JNETags.Items.CLARET_STEMS);
            tag(JNETags.Items.COOKED_HOGHAM).add(JNEItems.COOKED_HOGHAM.get());
            tag(JNETags.Items.DOESNT_MODIFY_POTION_STACK_SIZE).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "tether_potion"));
            // Having to use addOptional for specifically vanilla tags is a workaround for either a bug or my own incompetence.
            tag(JNETags.Items.FROGMIST_VISIBLE_ITEMS).addOptionalTag(ItemTags.HOES).addTag(JNETags.Items.FROGMISTS);
            copy(JNETags.Blocks.FROGMISTS, JNETags.Items.FROGMISTS);
            tag(JNETags.Items.GLOWSPORES).add(JNEItems.NIGHTSPORES.get(), JNEItems.LIGHTSPORES.get());
            tag(JNETags.Items.SHOTGUNS).add(JNEItems.SHOTGUN_FIST.get(), JNEItems.PUMP_CHARGE_SHOTGUN.get());
            copy(JNETags.Blocks.SHROOMLIGHTS, JNETags.Items.SHROOMLIGHTS);
            
            tag(JNETags.Items.SILVER_ARMORS).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_helmet")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_chestplate")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_leggings")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_boots")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_helmet")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_chestplate")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_leggings")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_boots")).addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_helmet")).addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_chestplate")).addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_leggings")).addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_boots")).addOptional(ResourceLocation.fromNamespaceAndPath("galosphere", "sterling_helmet")).addOptional(ResourceLocation.fromNamespaceAndPath("galosphere", "sterling_chestplate")).addOptional(ResourceLocation.fromNamespaceAndPath("galosphere", "sterling_leggings")).addOptional(ResourceLocation.fromNamespaceAndPath("galosphere", "sterling_boots"));
            tag(JNETags.Items.SILVER_WEAPONS).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_sword")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_axe")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_pickaxe")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_shovel")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_hoe")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_sword")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_axe")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_pickaxe")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_shovel")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_hoe")).addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_sword")).addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_axe")).addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_pickaxe")).addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_shovel")).addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_hoe"));

            tag(ItemTags.ARROWS).add(JNEItems.PHASMO_ARROW.get());
            tag(ItemTags.COALS).add(JNEItems.FOSSIL_FUEL.get());
            tag(ItemTags.DECORATED_POT_SHERDS).add(JNEItems.SEALED_POTTERY_SHERD.get(), JNEItems.SPECTRE_POTTERY_SHERD.get(), JNEItems.MARIONETTE_POTTERY_SHERD.get(), JNEItems.ELDRITCH_POTTERY_SHERD.get(), JNEItems.DECEPTION_POTTERY_SHERD.get(), JNEItems.FIREARM_POTTERY_SHERD.get(), JNEItems.BOTANICAL_POTTERY_SHERD.get());
            tag(ItemTags.LOGS).addTag(JNETags.Items.CLARET_STEMS);
            tag(ItemTags.MEAT).add(JNEItems.HOGHAM.get(), JNEItems.COOKED_HOGHAM.get());
            tag(ItemTags.NON_FLAMMABLE_WOOD).add(JNEBlocks.CEREBRAGE_CLARET_STEM.get().asItem(), JNEBlocks.CEREBRAGE_CLARET_HYPHAE.get().asItem(), JNEBlocks.STRIPPED_CLARET_STEM.get().asItem(), JNEBlocks.STRIPPED_CLARET_HYPHAE.get().asItem(), JNEBlocks.CLARET_PLANKS.get().asItem(), JNEBlocks.CLARET_SLAB.get().asItem(), JNEBlocks.CLARET_STAIRS.get().asItem(), JNEBlocks.CLARET_FENCE.get().asItem(), JNEBlocks.CLARET_FENCE_GATE.get().asItem(), JNEBlocks.CLARET_DOOR.get().asItem(), JNEBlocks.CLARET_TRAPDOOR.get().asItem(), JNEBlocks.CLARET_BUTTON.get().asItem(), JNEBlocks.CLARET_PRESSURE_PLATE.get().asItem());
            tag(ItemTags.PIGLIN_FOOD).add(JNEItems.HOGHAM.get(), JNEItems.COOKED_HOGHAM.get());
            tag(ItemTags.PLANKS).add(JNEBlocks.CLARET_PLANKS.get().asItem());
            tag(ItemTags.TRIM_TEMPLATES).add(JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        }
    }
}
