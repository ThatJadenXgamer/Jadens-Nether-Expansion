package net.jadenxgamer.netherexp.core.misc.neoforge.glm;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.LootModifier;

public class ReplaceItemModifier extends LootModifier {
    private final Item target;
    private final Item replacement;

    public ReplaceItemModifier(LootItemCondition[] conditions, Item target, Item replacement) {
        super(conditions);
        this.target = target;
        this.replacement = replacement;
    }

    public static final MapCodec<ReplaceItemModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            codecStart(instance)
                    .and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("target").forGetter(m -> m.target))
                    .and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("replacement").forGetter(m -> m.replacement))
                    .apply(instance, ReplaceItemModifier::new)
    );

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ObjectArrayList<ItemStack> result = new ObjectArrayList<>(generatedLoot);
        for (int i = 0; i < result.size(); i++) {
            ItemStack stack = result.get(i);
            if (stack.is(this.target)) {
                ItemStack newStack = new ItemStack(this.replacement, stack.getCount());
                newStack.applyComponents(stack.getComponentsPatch());
                result.set(i, newStack);
            }
        }
        return result;
    }

    @Override
    public MapCodec<? extends LootModifier> codec() {
        return CODEC;
    }
}