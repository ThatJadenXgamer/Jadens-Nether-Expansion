package net.jadenxgamer.netherexp.core.misc;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

public class JNEEnumExtensions {
    public static final EnumProxy<Rarity> ARTIFACT_ENUM_PROXY = new EnumProxy<>(
            Rarity.class, Rarity.getExtensionInfo().totalCount() + 1, "netherexp:artifact", (UnaryOperator<Style>) style -> style.withColor(15218975)
    );
}
