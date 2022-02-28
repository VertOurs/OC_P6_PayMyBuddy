package fr.vertours.buddtwo.constants;

import java.math.BigDecimal;

public class ApplicationConstants {

    public static final BigDecimal INITIAL_BALANCE = new BigDecimal("0.00");
    private static final BigDecimal FEE = new BigDecimal("0.05");
    public static final String BANKINGTRANSFER = "BANK TRANSFER";

    public static BigDecimal feeAmount(BigDecimal amount) {
        return amount.multiply(FEE);
    }
}
