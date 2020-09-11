package net.prismatic.monetarysolutions.components;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import nerdhub.cardinal.components.api.ComponentType;
import net.minecraft.nbt.CompoundTag;
import net.prismatic.monetarysolutions.MonetarySolutionsInitializer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class PlayerMoneyComponent implements PlayerComponent {
    private BigDecimal money;

    public PlayerMoneyComponent() {
        this.money = new BigDecimal("0.00");
    }

    @Override
    public @NotNull ComponentType<?> getComponentType() {
        return MonetarySolutionsInitializer.MONEY;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        this.money = new BigDecimal(tag.getString("money"));
    }
    @Override
    public @NotNull CompoundTag toTag(CompoundTag tag) {
        tag.putString("money", this.money.toString());
        return tag;
    }

    public void spend(String amount) {
        BigDecimal subtractor = this.money.subtract(new BigDecimal(amount));
        if (subtractor.signum() == -1) {
            return;
        } else {
            this.money = subtractor;
        }
    }

    public void pay(String amount) {
        this.money = this.money.add(new BigDecimal(amount));
    }

    public String amount() {
        return this.money.toString();
    }
}
