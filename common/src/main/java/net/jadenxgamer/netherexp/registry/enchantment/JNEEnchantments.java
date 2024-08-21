package net.jadenxgamer.netherexp.registry.enchantment;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.enchantment.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;

public class JNEEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(NetherExp.MOD_ID, Registries.ENCHANTMENT);

    public static final RegistrySupplier<Enchantment> PHANTASM_HULL = ENCHANTMENTS.register("phantasm_hull", PhantasmHullEnchantment::new);
    public static final RegistrySupplier<Enchantment> BLOODSHED = ENCHANTMENTS.register("bloodshed", BloodshedEnchantment::new);
    public static final RegistrySupplier<Enchantment> CARTRIDGE = ENCHANTMENTS.register("cartridge", CartridgeEnchantment::new);
    public static final RegistrySupplier<Enchantment> RECOIL = ENCHANTMENTS.register("recoil", RecoilEnchantment::new);
    public static final RegistrySupplier<Enchantment> BARRAGE = ENCHANTMENTS.register("barrage", BarrageEnchantment::new);
    public static final RegistrySupplier<Enchantment> ARTEMIS = ENCHANTMENTS.register("artemis", ArtemisEnchantment::new);

    public static void init() {
        ENCHANTMENTS.register();
    }
}
