package net.jadenxgamer.netherexp.forge.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.Mth;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class AddEnchantedItemModifier extends LootModifier {
    public static final Supplier<Codec<AddEnchantedItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).and(
                    inst.group(
                            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter((m) -> m.item),
                            ForgeRegistries.ENCHANTMENTS.getCodec().fieldOf("enchantment").forGetter((m) -> m.enchantment),
                            Codec.INT.optionalFieldOf("count", 1).forGetter((m) -> m.count),
                            Codec.INT.optionalFieldOf("min_level", 1).forGetter((m) -> m.minLevel),
                            Codec.INT.optionalFieldOf("max_level", 1).forGetter((m) -> m.maxLevel)
                    )
            ).apply(inst, AddEnchantedItemModifier::new)));

    private final Item item;
    private final Enchantment enchantment;
    private final int count;
    private final int minLevel;
    private final int maxLevel;

    protected AddEnchantedItemModifier(LootItemCondition[] conditionsIn, Item item, Enchantment enchantment, int count, int minLevel, int maxLevel) {
        super(conditionsIn);
        this.item = item;
        this.enchantment = enchantment;
        this.count = count;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ItemStack stack = new ItemStack(item, count);
        int level = Mth.nextInt(context.getRandom(), minLevel, maxLevel);
        EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(enchantment, level));

        if (stack.getCount() < stack.getMaxStackSize()) {
            generatedLoot.add(stack);
        } else {
            int i = stack.getCount();

            while (i > 0) {
                ItemStack subStack = stack.copy();
                subStack.setCount(Math.min(stack.getMaxStackSize(), i));
                i -= subStack.getCount();
                generatedLoot.add(subStack);
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}