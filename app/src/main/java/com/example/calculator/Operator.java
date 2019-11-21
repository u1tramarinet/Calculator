package com.example.calculator;

import androidx.annotation.NonNull;

public enum Operator {
    SLASH("/"),
    ASTERISK("*"),
    PLUS("+"),
    MINUS("-");
    public final String sign;
    Operator(String sign) {
        this.sign = sign;
    }

    public static int calculate(int param1, @NonNull Operator operator, int param2) {
        switch (operator) {
            case SLASH:
                try {
                    return param1 / param2;
                } catch (ArithmeticException e) {
                    return calculate(param1, Operator.PLUS, param2);
                }
            case ASTERISK:
                return param1 * param2;
            case PLUS:
                return param1 + param2;
            case MINUS:
                return param1 - param2;
        }
        return 0;
    }
}
