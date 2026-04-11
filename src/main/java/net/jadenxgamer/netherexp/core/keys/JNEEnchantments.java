package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class JNEEnchantments {

    public static final ResourceKey<Enchantment> VOLLEY = key("volley");
    public static final ResourceKey<Enchantment> LONGSHOT = key("longshot");
    public static final ResourceKey<Enchantment> RECOIL = key("recoil");
    public static final ResourceKey<Enchantment> COUNTERFORCE = key("counterforce");

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, NetherExp.netherexpPath(name));
    }
}
