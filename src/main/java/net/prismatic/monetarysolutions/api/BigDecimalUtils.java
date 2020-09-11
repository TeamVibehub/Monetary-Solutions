package net.prismatic.monetarysolutions.api;

import java.math.BigDecimal;

public class BigDecimalUtils {

    /**
     * Increases a BigDecimal by a String amount
     * @param base The BigDecimal to increase
     * @param amount The amount to increase by
     * @return The increased BigDecimal
     */
    public BigDecimal increase(BigDecimal base, String amount) {
        return base.add(new BigDecimal(amount));
    }

    /**
     * Decreases a BigDecimal by a String amount
     * @param base The BigDecimal to decrease
     * @param amount The amount to decrease by
     * @return The decreased BigDecimal
     */
    public static BigDecimal decrease(BigDecimal base, String amount) {
        return base.subtract(new BigDecimal(amount));
    }

    /**
     * Checks if a BigDecimal is negative
     * @param decimal The BigDecimal to check
     * @return The check result
     */
    public static boolean isNegative(BigDecimal decimal) {
        return decimal.signum() == -1;
    }

    /**
     * Divides a BigDecimal by a String divisor
     * @param base The BigDecimal to divide
     * @param amount The divisor
     * @return The divided BigDecimal
     */
    public BigDecimal divide(BigDecimal base, String amount) {
        return base.divide(new BigDecimal(amount));
    }

    /**
     * Multiplies a BigDecimal by a String multiplier
     * @param base The BigDecimal to multiply
     * @param amount The multiplier
     * @return The multiplied BigDecimal
     */
    public BigDecimal multiply(BigDecimal base, String amount) {
        return base.multiply(new BigDecimal(amount));
    }
}
