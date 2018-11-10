package cn.exrick.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class CurrencyStringUtils {

    public static enum CurrencyFormat {
        Money("#.##");

        private DecimalFormat decimalFormat;

        private CurrencyFormat(String decimalFormatString) {
            this.decimalFormat = new DecimalFormat(decimalFormatString);
        }

        public DecimalFormat getDecimalFormat() {
            return decimalFormat;
        }
    }

    public static String toString(BigDecimal value, CurrencyFormat currencyFormat) {
        return currencyFormat.getDecimalFormat().format(value);
    }

}
