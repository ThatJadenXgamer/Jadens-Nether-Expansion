package net.jadenxgamer.netherexp.registry.enchantment;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.enchantment.custom.*;
import net.jadenxgamer.netherexp.registry.item.custom.IShotgun;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JNEEnchantments {

    public static final EnchantmentCategory SHOTGUN = EnchantmentCategory.create("shotgun", (item -> item instanceof IShotgun));
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, NetherExp.MOD_ID);

    public static final RegistryObject<Enchantment> PHANTASM_HULL = ENCHANTMENTS.register("phantasm_hull", PhantasmHullEnchantment::new);
    public static final RegistryObject<Enchantment> CARTRIDGE = ENCHANTMENTS.register("cartridge", CartridgeEnchantment::new);
    public static final RegistryObject<Enchantment> RECOIL = ENCHANTMENTS.register("recoil", RecoilEnchantment::new);
    public static final RegistryObject<Enchantment> BARRAGE = ENCHANTMENTS.register("barrage", BarrageEnchantment::new);
    public static final RegistryObject<Enchantment> ARTEMIS = ENCHANTMENTS.register("artemis", ArtemisEnchantment::new);

    public static void init(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
