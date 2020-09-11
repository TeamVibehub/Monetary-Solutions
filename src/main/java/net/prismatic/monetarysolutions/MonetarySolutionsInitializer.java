package net.prismatic.monetarysolutions;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.util.EntityComponents;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.prismatic.monetarysolutions.api.Money;
import net.prismatic.monetarysolutions.components.PlayerMoneyComponent;

import java.math.BigDecimal;

public class MonetarySolutionsInitializer implements ModInitializer {

    public static final ComponentType<PlayerMoneyComponent> MONEY =
            ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("monetarysolutions:money"), PlayerMoneyComponent.class);

    @Override
    public void onInitialize() {
        EntityComponentCallback.event(PlayerEntity.class).register((player, components) -> components.put(MONEY, new PlayerMoneyComponent()));
        EntityComponents.setRespawnCopyStrategy(MONEY, RespawnCopyStrategy.ALWAYS_COPY);
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(CommandManager.literal("money")
            .then(CommandManager.literal("get")
                .then(CommandManager.argument("target", EntityArgumentType.player()))
                    .executes(context -> {
                        PlayerEntity player = EntityArgumentType.getPlayer(context, "target");
                        if (player != null) {
                            Money money = new Money(player);
                            context.getSource().sendFeedback(new LiteralText(money.get().toString()), false);
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                )
            )
            .then(CommandManager.literal("set")
                .then(CommandManager.argument("target", EntityArgumentType.player()))
                    .then(CommandManager.argument("amount", StringArgumentType.string()))
                        .requires(source -> source.hasPermissionLevel(1))
                            .executes(context -> {
                                PlayerEntity player = EntityArgumentType.getPlayer(context, "target");
                                if (player != null) {
                                    if (new BigDecimal(StringArgumentType.getString(context, "amount")).signum() != -1) {
                                        PlayerMoneyComponent money = MONEY.get(ComponentProvider.fromEntity(player));
                                        money.set(StringArgumentType.getString(context, "amount"));
                                        return 1;
                                    } else {
                                        return -1;
                                    }
                                } else {
                                    return -1;
                                }
                            }
                        )
                    )
                )
        );
    }
}
