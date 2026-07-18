package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class JNEAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NetherExp.MOD_ID);

    public static final Supplier<AttachmentType<ResourceLocation>> LAST_FIRE = ATTACHMENT_TYPES.register(
            "last_fire", () -> AttachmentType.builder(() -> NetherExp.minecraftPath("fire"))
                    .serialize(ResourceLocation.CODEC)
                    .sync(ResourceLocation.STREAM_CODEC)
                    .build()
    );

    public static void init(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
