package net.prismatic.monetarysolutions.api;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.prismatic.monetarysolutions.MonetarySolutionsInitializer;
import net.prismatic.monetarysolutions.components.PlayerMoneyComponent;

import java.math.BigDecimal;

public class Money {
    private PlayerMoneyComponent money;

    public Money(PlayerEntity player) {
        this.money = MonetarySolutionsInitializer.MONEY.get(ComponentProvider.fromEntity(player));
    }

    public BigDecimal get() {
        return new BigDecimal(money.amount());
    }

    public boolean decrease(BigDecimal decreasor) {
        boolean result = money.spend(decreasor.toString());
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    public void increase(BigDecimal increasor) {
        money.pay(increasor.toString());
    }
}
