package fr.vertours.buddtwo.constants;

import java.math.BigDecimal;

public class ApplicationConstants {

    private ApplicationConstants() {
    }

    /**
     * Amount available at the creation of an account
     */
    public static final BigDecimal INITIAL_BALANCE = new BigDecimal("0.00");

    /**
     * tax levied during transactions
     */
    private static final BigDecimal FEE = new BigDecimal("0.05");

    /**
     * Information on the nature of the transaction
     */
    public static final String BANKINGTRANSFER = "BANK TRANSFER";

    /**
     * Sens of banking Movement : CREDIT
     */
    public static final String CREDIT = "CREDIT";

    /**
     * Sens of banking Movement : DEBIT
     */
    public static final String DEBIT = "DEBIT";

    /**
     *
     */
    public static final int CHARACTER_LIMIT = 50;

    /**
     * calculate the amount of the fee
     * @param amount
     * @return the value of the fee according to the amount before tax
     */
    public static BigDecimal feeAmount(final BigDecimal amount) {
        return amount.multiply(FEE);
    }
}
