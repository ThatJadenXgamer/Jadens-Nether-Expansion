package net.jadenxgamer.netherexp.data.providers.tags;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class JNEEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public JNEEntityTypeTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
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
