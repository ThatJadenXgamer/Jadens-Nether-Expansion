package net.jadenxgamer.netherexp.data.providers;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.*;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class JNEAdvancementProvider extends AdvancementProvider {

    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     */
    public JNEAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        // we pass all advancement generators to the super constructor
        super(output, registries, existingFileHelper, List.of(new NetherAdvancementGenerator()));
    }

    private static final class NetherAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {

            // get the root advancement for nether advancements
            AdvancementHolder root = AdvancementSubProvider.createPlaceholder("nether/root");

            AdvancementHolder add_spores_to_block = taskBuilder(JNEItems.NIGHTSPORES.get(), "add_spores_to_block", root)
                    .addCriterion("nightspores_used", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(JNETags.Blocks.NIGHTSPORES_APPLICABLE)),
                            ItemPredicate.Builder.item().of(JNEItems.NIGHTSPORES.get())
                    ))
                    .addCriterion("lightspores_used", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(JNETags.Blocks.LIGHTSPORES_APPLICABLE)),
                            ItemPredicate.Builder.item().of(JNEItems.LIGHTSPORES.get())
                    )).requirements(AdvancementRequirements.Strategy.OR)
                    .save(saver, NetherExp.id("nether/add_spores_to_block"), existingFileHelper);

            AdvancementHolder soul_sand_valley = unannouncedTaskBuilder(Items.SOUL_SAND, "soul_sand_valley", root)
                    .addCriterion("soul_sand_valley_entered", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.inBiome(registries.holderOrThrow(Biomes.SOUL_SAND_VALLEY))
                    )).save(saver, NetherExp.id("nether/soul_sand_valley"), existingFileHelper);

            AdvancementHolder emf_raider = goalBuilder(JNEItems.SANCTUM_COMPASS.get(), "emf_raider", soul_sand_valley)
                    .addCriterion("has_active_sanctum_compass", JNECriteriaTriggers.ACTIVATE_SANCTUM_COMPASS.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/emf_raider"), existingFileHelper);

            /*

            TODO: IMPLEMENT SANCTUM

            AdvancementHolder occult_manor = announcedTaskBuilder(JNEBlocks.CHISELED_SOUL_SLATE_TILES.get(), "castlemania", emf_raider)
                    .addCriterion("sanctum_entered", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.inStructure(registries.holderOrThrow(JNEStructures.SANCTUM))
                    )).save(saver, NetherExp.id("nether/occult_manor"), existingFileHelper);
            */
            AdvancementHolder occult_manor = AdvancementSubProvider.createPlaceholder("netherexp:nether/occult_manor");

            /*

            TODO: IMPLEMENT TREACHEROUS CANDLE

            AdvancementHolder rekindled_betrayal = goalBuilder(JNEItems.TREACHEROUS_FLAME.get(), "rekindled_betrayal", occult_manor)
                    .addCriterion("treacherous_candle_lit", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(JNEBlocks.TREACHEROUS_CANDLE.get())),
                            ItemPredicate.Builder.item().of(Items.FLINT_AND_STEEL)
                    )).save(saver, NetherExp.id("nether/rekindled_betrayal"), existingFileHelper);
            */
            AdvancementHolder rekindled_betrayal = AdvancementSubProvider.createPlaceholder("netherexp:nether/rekindled_betrayal");

            /*

            TODO: IMPLEMENT BRAZIER CHEST

            AdvancementHolder eldritch_manipulation = goalBuilder(JNEBlocks.BRAZIER_CHEST.get(), "eldritch_manipulation", occult_manor)
                    .addCriterion("brazier_chest_activated", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(JNEBlocks.BRAZIER_CHEST.get())),
                            ItemPredicate.Builder.item().of(JNEItems.TREACHEROUS_FLAME.get())
                    )).save(saver, NetherExp.id("nether/eldritch_manipulation"), existingFileHelper);
            */
            AdvancementHolder eldritch_manipulation = AdvancementSubProvider.createPlaceholder("netherexp:nether/eldritch_manipulation");

            AdvancementHolder angel_with_a_shotgun = taskBuilder(JNEItems.SHOTGUN_FIST.get(), "angel_with_a_shotgun", eldritch_manipulation)
                    .addCriterion("obtained_shotgun", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(JNETags.Items.SHOTGUNS)))
                    .save(saver, NetherExp.id("nether/angel_with_a_shotgun"), existingFileHelper);

            AdvancementHolder find_fortress = AdvancementSubProvider.createPlaceholder("nether/find_fortress");

            AdvancementHolder pizza_hunt = challengeBuilder(JNEItems.NETHER_PIZZA_SLICE.get(), "pizza_hunt", find_fortress)
                    .addCriterion("consume_pizza_slice", ConsumeItemTrigger.TriggerInstance.usedItem(JNEItems.NETHER_PIZZA_SLICE.get()))
                    .save(saver, NetherExp.id("nether/pizza_hunt"), existingFileHelper);

            AdvancementHolder ayo_the_pizza_here = taskBuilder(JNEBlocks.NETHER_PIZZA.get(), "ayo_the_pizza_here", pizza_hunt)
                    .addCriterion("allay_deliver_pizza_to_player", PickedUpItemTrigger.TriggerInstance.thrownItemPickedUpByPlayer(
                            Optional.empty(),
                            Optional.of(ItemPredicate.Builder.item().of(JNEBlocks.NETHER_PIZZA.get()).build()),
                            Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.ALLAY)))
                    )).save(saver, NetherExp.id("nether/ayo_the_pizza_here"), existingFileHelper);
            /*

            TODO: IMPLEMENT TREACHEROUS CANDLE

            AdvancementHolder back_with_a_vengeance = announcedTaskBuilder(JNEBlocks.TREACHEROUS_CANDLE.get(), "back_with_a_vengeance", rekindled_betrayal)
                    .requirements("honeycomb_used_on_treacherous_candle", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(JNEBlocks.TREACHEROUS_CANDLE.get())),
                            ItemPredicate.Builder.item().of(Items.HONEYCOMB)
                    )).save(saver, NetherExp.id("nether/back_with_a_vengeance"), existingFileHelper);
             */
            AdvancementHolder back_with_a_vengeance = AdvancementSubProvider.createPlaceholder("netherexp:nether/back_with_a_vengeance");

            AdvancementHolder explore_nether = AdvancementSubProvider.createPlaceholder("nether/explore_nether");

            AdvancementHolder brain_food = taskBuilder(JNEItems.CEREBRAGE.get(), "brain_food", explore_nether)
                    .addCriterion("planted_cerebrage", JNECriteriaTriggers.PLANTED_CEREBRAGE.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/brain_food"), existingFileHelper);

            AdvancementHolder big_brain_time = goalBuilder(JNEBlocks.CEREBRAGE_CLARET_STEM.get(), "big_brain_time", brain_food)
                    .addCriterion("grow_cerebrage_claret", JNECriteriaTriggers.GROW_CEREBRAGE_CLARET.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/big_brain_time"), existingFileHelper);

            /*

            TODO: IMPLEMENT BLACK ICE GLACIERS

            AdvancementHolder ssv_sub_biomes = goalBuilder(JNEBlocks.NETHERITE_PLATED_BLOCK.get(), "fright_before_xmas", soul_sand_valley)
                    .addCriterion("black_ice_glaciers_entered", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.inBiome(registries.holderOrThrow(JNEBiomes.BLACK_ICE_GLACIERS))
                    )).requirements(AdvancementRequirements.Strategy.AND)
                    .rewards(AdvancementRewards.Builder.experience(200))
                    .save(saver, NetherExp.id("nether/ssv_sub_biomes"), existingFileHelper);

             */
            AdvancementHolder ssv_sub_biomes = AdvancementSubProvider.createPlaceholder("netherexp:nether/ssv_sub_biomes");

            /*

            TODO: IMPLEMENT BLACK ICICLE ENTITY

            AdvancementHolder black_ice_black_everything_black = taskBuilder(JNEBlocks.BLACK_ICICLE.get(), "black_ice_black_everything_black", ssv_sub_biomes)
                    .addCriterion("shot_black_icicle", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntityWithDamage(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(JNEEntityType.BLACK_ICICLE.get())))))
                    .save(saver, NetherExp.id("nether/black_ice_black_everything_black"), existingFileHelper);
             */
            AdvancementHolder black_ice_black_everything_black = AdvancementSubProvider.createPlaceholder("netherexp:nether/black_ice_black_everything_black");

            AdvancementHolder frightening_rush = taskBuilder(JNEBlocks.SOUL_SWIRLS.get(), "frightening_rush", soul_sand_valley)
                    .addCriterion("soul_swirls_entered", EnterBlockTrigger.TriggerInstance.entersBlock(JNEBlocks.SOUL_SWIRLS.get()))
                    .save(saver, NetherExp.id("nether/frightening_rush"), existingFileHelper);

            /*

            TODO: IMPLEMENT PHASMO ARROWS

            AdvancementHolder blind_shot = announcedTaskBuilder(JNEItems.PHASMO_ARROW.get(), "blind_shot", frightening_rush)
                    .addCriterion("shot_phasmo_arrow", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntityWithDamage(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(JNEEntityType.PHSMO_ARROW.get())))))
                    .save(saver, NetherExp.id("nether/blind_shot"), existingFileHelper);

             */
            AdvancementHolder blind_shot = AdvancementSubProvider.createPlaceholder("netherexp:nether/blind_shot");

            AdvancementHolder enter_soul_glass = taskBuilder(JNEItems.PHASMO_SHARD.get(), "phasmophobic", frightening_rush)
                    .addCriterion("soul_glass_entered", EnterBlockTrigger.TriggerInstance.entersBlock(JNEBlocks.SOUL_GLASS.get()))
                    .save(saver, NetherExp.id("nether/enter_soul_glass"), existingFileHelper);


            AdvancementHolder ghost_mucus = taskBuilder(JNEFluids.ECTOPLASM_BUCKET.get(), "ghost_mucus", soul_sand_valley)
                    .addCriterion("has_ectoplasm_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(JNEFluids.ECTOPLASM_BUCKET.get()))
                    .save(saver, NetherExp.id("nether/ghost_mucus"), existingFileHelper);

            AdvancementHolder exorcism = goalBuilder(JNEItems.WISP_BOTTLE.get(), "exorcism", ghost_mucus)
                    .addCriterion("exorcism", JNECriteriaTriggers.EXORCISM.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/exorcism"), existingFileHelper);

            /*

            TODO: IMPLEMENT MEGA FOSSIL CAMPSITE

            AdvancementHolder rattling_remnants = announcedTaskBuilder(Blocks.BONE_BLOCK, "rattling_remnants", soul_sand_valley)
                    .addCriterion("mega_fossil_campsite_entered", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.inStructure(structureGetter.getOrThrow(JNEStructures.MEGA_FOSSIL_CAMPSITE))
                    )).save(saver, NetherExp.id("nether/rattling_remnants"), existingFileHelper);
            */
            AdvancementHolder rattling_remnants = AdvancementSubProvider.createPlaceholder("netherexp:nether/rattling_remnants");

            AdvancementHolder gravedigger = taskBuilder(JNEItems.FOSSIL_FUEL.get(), "gravedigger", rattling_remnants)
                    .addCriterion("broken_fossil_fuel_ore", JNECriteriaTriggers.BROKEN_FOSSIL_FUEL_ORE.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/gravedigger"), existingFileHelper);

            AdvancementHolder hp_minecraftian = goalBuilder(Items.FLINT_AND_STEEL, "hp_minecraftian", occult_manor)
                    .addCriterion("revice_carcass", JNECriteriaTriggers.REVIVE_CARCASS.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/hp_minecraftian"), existingFileHelper);

            AdvancementHolder lorekeeper = challengeBuilder(JNEBlocks.OSSIFIED_GARGOYLE_STATUE.get(), "lorekeeper", hp_minecraftian)
                    .addCriterion("has_statues", InventoryChangeTrigger.TriggerInstance.hasItems(
                            JNEBlocks.OSSIFIED_GARGOYLE_STATUE.get(),
                            JNEBlocks.TRAMPLE_GARGOYLE_STATUE.get(),
                            JNEBlocks.PHASE_GARGOYLE_STATUE.get(),
                            JNEBlocks.GHOUL_GARGOYLE_STATUE.get(),
                            JNEBlocks.WRETCHED_GARGOYLE_STATUE.get(),
                            JNEBlocks.SEALED_GARGOYLE_STATUE.get(),
                            JNEBlocks.OCCULT_GARGOYLE_STATUE.get(),
                            JNEBlocks.TREACHEROUS_GARGOYLE_STATUE.get(),
                            JNEBlocks.CIRRIPEDIA_GARGOYLE_STATUE.get(),
                            JNEBlocks.OBFUSCATED_GARGOYLE_STATUE.get()
                    )).save(saver, NetherExp.id("nether/lorekeeper"), existingFileHelper);

            AdvancementHolder no_skin = taskBuilder(JNEItems.TREACHEROUS_FLAME.get(), "no_skin", hp_minecraftian)
                    .addCriterion("immortal_carcass", JNECriteriaTriggers.IMMORTAL_CARCASS.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/no_skin"), existingFileHelper);

            AdvancementHolder non_renewable_yeah_right = goalBuilder(Items.SKELETON_SKULL, "non_renewable_yeah_right", gravedigger)
                    .addCriterion("killed_skeleton_on_fossil_ore_convertible", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS).steppingOn(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(JNETags.Blocks.FOSSIL_ORE_CONVERTIBLE)))))
                    .save(saver, NetherExp.id("nether/non_renewable_yeah_right"), existingFileHelper);

            AdvancementHolder brew_potion = AdvancementSubProvider.createPlaceholder("nether/brew_potion");

            AdvancementHolder overkill = challengeBuilder(Items.GOLDEN_SWORD, "overkill", brew_potion)
                    .addCriterion("overkill", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntityWithDamage(DamagePredicate.Builder.damageInstance().dealtDamage(MinMaxBounds.Doubles.atLeast(20)).type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(JNETags.DamageTypes.OVERKILL_VALID)))))
                    .save(saver, NetherExp.id("nether/overkill"), existingFileHelper);

            AdvancementHolder the_caduceus = taskBuilder(JNEItems.ANTIDOTE.get(), "the_caduceus", occult_manor)
                    .addCriterion("has_antidote", InventoryChangeTrigger.TriggerInstance.hasItems(JNEItems.ANTIDOTE.get()))
                    .save(saver, NetherExp.id("nether/the_caduceus"), existingFileHelper);

            AdvancementHolder plague_doctor = challengeBuilder(JNEItems.GRENADE_ANTIDOTE.get(), "plague_doctor", the_caduceus)
                    .addCriterion("has_all_immunities", EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects()
                                                                                                                 .and(JNEMobEffects.SPEED_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.SLOWNESS_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.STRENGTH_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.JUMP_BOOST_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.REGENERATION_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.FIRE_RESISTANCE_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.WATER_BREATHING_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.INVISIBILITY_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.WEAKNESS_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.POISON_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.RESISTANCE_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.ABSORPTION_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.HASTE_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.MINING_FATIGUE_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.DARKNESS_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.LEVITATION_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.HUNGER_IMMUNITY)
                                                                                                                 .and(JNEMobEffects.WITHER_IMMUNITY)
                    )).rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, NetherExp.id("nether/plague_doctor"), existingFileHelper);

            AdvancementHolder rodeo_stampede = taskBuilder(JNEItems.SKULL_ON_A_STICK.get(), "rodeo_stampede", exorcism)
                    .addCriterion("tame_stampede", JNECriteriaTriggers.TAME_STAMPEDE.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/rodeo_stampede"), existingFileHelper);

            AdvancementHolder sanctum_of_wax = taskBuilder(JNEItems.ANCIENT_WAX.get(), "sanctum_of_wax", hp_minecraftian)
                    .addCriterion("make_fake_carcass", JNECriteriaTriggers.MAKE_FAKE_CARCASS.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/sanctum_of_wax"), existingFileHelper);

            AdvancementHolder stayin_frosty = taskBuilder(Items.LAVA_BUCKET, "stayin_frosty", brew_potion)
                    .addCriterion("fire_resistance_in_lava", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity().effects(MobEffectsPredicate.Builder.effects().and(MobEffects.FIRE_RESISTANCE)).located(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(Blocks.LAVA)))))
                    .save(saver, NetherExp.id("nether/stayin_frosty"), existingFileHelper);

            AdvancementHolder the_nether_is_full = goalBuilder(JNEItems.PUMP_CHARGE_SHOTGUN.get(), "the_nether_is_full", angel_with_a_shotgun)
                    .addCriterion("has_pump_charge_shotgun", InventoryChangeTrigger.TriggerInstance.hasItems(JNEItems.PUMP_CHARGE_SHOTGUN.get()))
                    .save(saver, NetherExp.id("nether/the_nether_is_full"), existingFileHelper);

            AdvancementHolder ultra_overkill = challengeBuilder(JNEItems.SHOTGUN_CORE.get(), "ultrakill", the_nether_is_full)
                    .addCriterion("killed_with_pump_charge", JNECriteriaTriggers.KILLED_WITH_PUMP_CHARGE.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .save(saver, NetherExp.id("nether/ultra_overkill"), existingFileHelper);

            AdvancementHolder froghorn = challengeBuilder(JNEBlocks.VERDANT_FROGMIST.get(), "froghorn", blind_shot)
                    .addCriterion("has_frogmists", InventoryChangeTrigger.TriggerInstance.hasItems(
                            JNEBlocks.OCHRE_FROGMIST.get(),
                            JNEBlocks.PEARLESCENT_FROGMIST.get(),
                            JNEBlocks.VERDANT_FROGMIST.get()
                    )).save(saver, NetherExp.id("nether/froghorn"), existingFileHelper);
        }

        /**
         * Gets a builder for a challenge advancement
         *
         * @param icon the advancement icon
         * @param name the internal advancement name
         * @param parent the parent
         * @return the builder
         */
        private static Advancement.Builder challengeBuilder(ItemLike icon, String name, AdvancementHolder parent) {
            // #recipeAdvancement() does nothing but disable telemetry
            return Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(
                            icon,
                            Component.translatable("advancements.nether." + name + ".title"),
                            Component.translatable("advancements.nether." + name + ".description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    );
        }

        /**
         * Gets a builder for a goal advancement
         *
         * @param icon the advancement icon
         * @param name the internal advancement name
         * @param parent the parent
         * @return the builder
         */
        private static Advancement.Builder goalBuilder(ItemLike icon, String name, AdvancementHolder parent) {
            // #recipeAdvancement() does nothing but disable telemetry
            return Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(
                            icon,
                            Component.translatable("advancements.nether." + name + ".title"),
                            Component.translatable("advancements.nether." + name + ".description"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    );
        }

        /**
         * Gets a builder for a task advancement
         *
         * @param icon the advancement icon
         * @param name the internal advancement name
         * @param parent the parent
         * @return the builder
         */
        private static Advancement.Builder taskBuilder(ItemLike icon, String name, AdvancementHolder parent) {
            // #recipeAdvancement() does nothing but disable telemetry
            return Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(
                            icon,
                            Component.translatable("advancements.nether." + name + ".title"),
                            Component.translatable("advancements.nether." + name + ".description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    );
        }

        /**
         * Gets a builder for a task advancement that doesn't send chat messages or toasts
         *
         * @param icon the advancement icon
         * @param name the internal advancement name
         * @param parent the parent
         * @return the builder
         */
        private static Advancement.Builder unannouncedTaskBuilder(ItemLike icon, String name, AdvancementHolder parent) {
            // #recipeAdvancement() does nothing but disable telemetry
            return Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(
                            icon,
                            Component.translatable("advancements.nether." + name + ".title"),
                            Component.translatable("advancements.nether." + name + ".description"),
                            null,
                            AdvancementType.TASK,
                            false,
                            false,
                            false
                    );
        }
    }
}
