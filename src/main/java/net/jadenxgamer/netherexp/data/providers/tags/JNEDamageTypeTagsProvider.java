package net.jadenxgamer.netherexp.data.providers.tags;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNEDamageTypes;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class JNEDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public JNEDamageTypeTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
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
