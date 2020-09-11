package net.prismatic.monetarysolutions;

import com.mojang.brigadier.arguments.StringArgumentType;
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
import net.prismatic.monetarysolutions.api.BigDecimalUtils;
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
                .then(CommandManager.argument("target", EntityArgumentType.player())
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
                ))
            )
            .then(CommandManager.literal("set")
                .then(CommandManager.argument("target", EntityArgumentType.player())
                    .then(CommandManager.argument("amount", StringArgumentType.string())
                        .requires(source -> source.hasPermissionLevel(1))
                            .executes(context -> {
                                PlayerEntity player = EntityArgumentType.getPlayer(context, "target");
                                if (player != null) {
                                    if (!(BigDecimalUtils.isNegative(new BigDecimal(StringArgumentType.getString(context, "amount"))))) {
                                        Money money = new Money(player);
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
            )
            .then(CommandManager.literal("pay")
                .then(CommandManager.argument("target", EntityArgumentType.player())
                    .then(CommandManager.argument("amount", StringArgumentType.string())
                        .executes(context -> {
                            PlayerEntity player = EntityArgumentType.getPlayer(context, "target");
                            if (player != null) {
                                if (!(BigDecimalUtils.isNegative(new BigDecimal(StringArgumentType.getString(context, "amount"))))) {
                                    Money senderMoney = new Money(context.getSource().getPlayer());
                                    Money targetMoney = new Money(player);
                                    BigDecimal senderMoneyTemp = BigDecimalUtils.decrease(senderMoney.get(), StringArgumentType.getString(context, "amount"));
                                    if (BigDecimalUtils.isNegative(senderMoneyTemp)) {
                                        return -1;
                                    } else {
                                        senderMoney.decrease(new BigDecimal(StringArgumentType.getString(context, "amount")));
                                        targetMoney.increase(new BigDecimal(StringArgumentType.getString(context, "amount")));
                                        return 1;
                                    }
                                } else {
                                    return 0;
                                }
                            } else {
                                return 0;
                            }
                        }
                    )
                )
            )
        )));
    }
}
