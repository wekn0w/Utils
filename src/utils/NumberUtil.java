package utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberUtil {
    public static BigInteger toBigIntegerSafety(Object value) {
        BigInteger ret = null;
        if (value != null) {
            if (value instanceof BigInteger) {
                ret = (BigInteger) value;
            } else if (value instanceof String) {
                ret = new BigInteger((String) value);
            } else if (value instanceof BigDecimal) {
                ret = ((BigDecimal) value).toBigInteger();
            } else if (value instanceof Number) {
                ret = BigInteger.valueOf(((Number) value).longValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigInteger.");
            }
        }
        return ret;
    }

    public static BigDecimal toBigDecimalSafety(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof Number) {
                ret = BigDecimal.valueOf(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }
}
