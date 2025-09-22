package net.jadenxgamer.netherexp.data;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface JNETagProviders {

    static List<TriFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, ExistingFileHelper, IntrinsicHolderTagsProvider<?>>> getProviderFactories() {
        return List.of(
                BlockTags::new
        );
    }

    final class BlockTags extends BlockTagsProvider {

        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
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
        }
    }

}
