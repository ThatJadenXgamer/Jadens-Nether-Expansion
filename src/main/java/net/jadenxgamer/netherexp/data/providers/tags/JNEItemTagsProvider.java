package net.jadenxgamer.netherexp.data.providers.tags;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public final class JNEItemTagsProvider extends ItemTagsProvider {

    /**
     * Create a new item tags provider.
     *
     * @param output             the output location
     * @param registries         a {@linkplain CompletableFuture} supplying the registries
     * @param existingFileHelper a {@linkplain ExistingFileHelper} to find existing files
     */
    public JNEItemTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries,
            CompletableFuture<TagLookup<Block>> blockTags,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, registries, blockTags, NetherExp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(JNETags.Items.ARTIFACTS).add(JNEItems.SHOTGUN_CORE.get());
        copy(JNETags.Blocks.CLARET_STEMS, JNETags.Items.CLARET_STEMS);
        tag(JNETags.Items.COOKED_HOGHAM).add(JNEItems.COOKED_HOGHAM.get());
        tag(JNETags.Items.DOESNT_MODIFY_POTION_STACK_SIZE).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "tether_potion"));

        tag(JNETags.Items.FROGMIST_VISIBLE_ITEMS).addTag(JNETags.Items.FROGMISTS).addTag(ItemTags.HOES);

        copy(JNETags.Blocks.FROGMISTS, JNETags.Items.FROGMISTS);
        tag(JNETags.Items.GLOWSPORES).add(JNEItems.NIGHTSPORES.get(), JNEItems.LIGHTSPORES.get());
        tag(JNETags.Items.SHOTGUNS).add(JNEItems.SHOTGUN_FIST.get(), JNEItems.PUMP_CHARGE_SHOTGUN.get());
        copy(JNETags.Blocks.SHROOMLIGHTS, JNETags.Items.SHROOMLIGHTS);

        tag(JNETags.Items.SILVER_ARMORS)
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_helmet"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_chestplate"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_leggings"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_boots"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_helmet"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_chestplate"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_leggings"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_boots"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_helmet"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_chestplate"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_leggings"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_boots"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("galosphere", "sterling_helmet"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("galosphere", "sterling_chestplate"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("galosphere", "sterling_leggings"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("galosphere", "sterling_boots"));

        tag(JNETags.Items.SILVER_WEAPONS)
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_sword"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_axe"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_pickaxe"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_shovel"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "necromium_hoe"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_sword"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_axe"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_pickaxe"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_shovel"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "silver_hoe"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_sword"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_axe"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_pickaxe"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_shovel"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_hoe"));

        tag(ItemTags.ARROWS).add(JNEItems.PHASMO_ARROW.get());
        tag(ItemTags.COALS).add(JNEItems.FOSSIL_FUEL.get());
        tag(ItemTags.DECORATED_POT_SHERDS).add(
                JNEItems.SEALED_POTTERY_SHERD.get(),
                JNEItems.SPECTRE_POTTERY_SHERD.get(),
                JNEItems.MARIONETTE_POTTERY_SHERD.get(),
                JNEItems.ELDRITCH_POTTERY_SHERD.get(),
                JNEItems.DECEPTION_POTTERY_SHERD.get(),
                JNEItems.FIREARM_POTTERY_SHERD.get(),
                JNEItems.BOTANICAL_POTTERY_SHERD.get());

        tag(ItemTags.LOGS).addTag(JNETags.Items.CLARET_STEMS);
        tag(ItemTags.MEAT).add(JNEItems.HOGHAM.get(), JNEItems.COOKED_HOGHAM.get());
        tag(ItemTags.NON_FLAMMABLE_WOOD).add(
                JNEBlocks.CEREBRAGE_CLARET_STEM.get().asItem(),
                JNEBlocks.CEREBRAGE_CLARET_HYPHAE.get().asItem(),
                JNEBlocks.STRIPPED_CLARET_STEM.get().asItem(),
                JNEBlocks.STRIPPED_CLARET_HYPHAE.get().asItem(),
                JNEBlocks.CLARET_PLANKS.get().asItem(),
                JNEBlocks.CLARET_SLAB.get().asItem(),
                JNEBlocks.CLARET_STAIRS.get().asItem(),
                JNEBlocks.CLARET_FENCE.get().asItem(),
                JNEBlocks.CLARET_FENCE_GATE.get().asItem(),
                JNEBlocks.CLARET_DOOR.get().asItem(),
                JNEBlocks.CLARET_TRAPDOOR.get().asItem(),
                JNEBlocks.CLARET_BUTTON.get().asItem(),
                JNEBlocks.CLARET_PRESSURE_PLATE.get().asItem());

        tag(ItemTags.PIGLIN_FOOD).add(JNEItems.HOGHAM.get(), JNEItems.COOKED_HOGHAM.get());
        tag(ItemTags.PLANKS).add(JNEBlocks.CLARET_PLANKS.get().asItem());
        tag(ItemTags.TRIM_TEMPLATES).add(
                JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get(),
                JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(),
                JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get());
    }
}
