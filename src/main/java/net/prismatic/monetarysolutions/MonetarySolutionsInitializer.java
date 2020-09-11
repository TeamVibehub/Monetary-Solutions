package net.prismatic.monetarysolutions;

import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.util.EntityComponents;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.prismatic.monetarysolutions.components.PlayerMoneyComponent;

public class MonetarySolutionsInitializer implements ModInitializer {

    public static final ComponentType<PlayerMoneyComponent> MONEY =
            ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("monetarysolutions:money"), PlayerMoneyComponent.class);

    @Override
    public void onInitialize() {
        EntityComponentCallback.event(PlayerEntity.class).register((player, components) -> components.put(MONEY, new PlayerMoneyComponent()));
        EntityComponents.setRespawnCopyStrategy(MONEY, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
