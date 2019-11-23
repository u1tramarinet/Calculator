package com.example.calculator;

import androidx.annotation.NonNull;

public enum Operator {
    SLASH("/", Priority.HIGH),
    ASTERISK("*", Priority.HIGH),
    PLUS("+", Priority.LOW),
    MINUS("-", Priority.LOW);

    public final String sign;
    public final Priority priority;

    Operator(@NonNull String sign, Priority priority) {
        this.sign = sign;
        this.priority = priority;
    }
}
