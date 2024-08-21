package net.jadenxgamer.netherexp.forge.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ReplaceItemModifier extends LootModifier {
    public static final Supplier<Codec<ReplaceItemModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("target_item").forGetter(m -> m.targetItem))
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("replacement_item").forGetter(m -> m.replacementItem))
                    .apply(inst, ReplaceItemModifier::new)));
    private final Item targetItem;
    private final Item replacementItem;

    public ReplaceItemModifier(LootItemCondition[] conditionsIn, Item targetItem, Item replacementItem) {
        super(conditionsIn);
        this.targetItem = targetItem;
        this.replacementItem = replacementItem;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(context)) {
                return generatedLoot;
            }
        }

        for (int i = 0; i < generatedLoot.size(); i++) {
            ItemStack stack = generatedLoot.get(i);
            if (stack.getItem() == this.targetItem) {
                ItemStack replacementStack = new ItemStack(this.replacementItem, stack.getCount());
                generatedLoot.set(i, replacementStack);
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
