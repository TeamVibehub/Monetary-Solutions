package net.prismatic.monetarysolutions.api;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.prismatic.monetarysolutions.MonetarySolutionsInitializer;
import net.prismatic.monetarysolutions.components.PlayerMoneyComponent;
import java.math.BigDecimal;

public class Money {
    private final PlayerMoneyComponent money;

    /**
     * Constructs a new Money instance from a player.
    @param player The player to get data from
     */
    public Money(PlayerEntity player) {
        this.money = MonetarySolutionsInitializer.MONEY.get(ComponentProvider.fromEntity(player));
    }

    /**
     * Returns a new BigDecimal with the value of the player's money
     */
    public BigDecimal get() {
        return new BigDecimal(money.amount());
    }

    /**
     * Decreases the player's money by the provided BigDecimal
     * @param decreasor The BigDecimal to use as the decreasor
     */
    public boolean decrease(BigDecimal decreasor) {
        return money.spend(decreasor.toString());
    }

    /**
     * Increases the player's money by the provided BigDecimal
     * @param increasor The BigDecimal to use as the increasor
     */
    public void increase(BigDecimal increasor) {
        money.pay(increasor.toString());
    }

    /**
     * Sets the player's money to a specific amount
     * @param amount What to set the player's money to
     */
    public void set(String amount) {
        money.set(amount);
    }
}
