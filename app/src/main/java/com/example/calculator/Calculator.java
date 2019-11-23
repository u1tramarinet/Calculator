package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private static Calculator INSTANCE = new Calculator();

    private enum State {
        EMPTY,
        NUMBER,
        OPERATOR
    }

    @NonNull
    private State state = State.EMPTY;

    @NonNull
    private List<Long> values = new ArrayList<>();
    @NonNull
    private List<Operator> operators = new ArrayList<>();

    private Calculator() {
    }

    public static Calculator getInstance() {
        return INSTANCE;
    }

    public void addNumber(long number, @Nullable Callback callback) {
        switch (state) {
            case EMPTY:
            case OPERATOR:
                values.add(number);
                break;
            case NUMBER:
                int indexLast = values.size() - 1;
                long value = values.get(indexLast) * 10 + number;
                values.set(indexLast, value);
                break;
        }
        state = State.NUMBER;
        updateFormula(callback);
        calculate(new ArrayList<>(values), new ArrayList<>(operators), callback);
    }

    public void addOperator(@NonNull Operator operator, @Nullable Callback callback) {
        switch (state) {
            case EMPTY:
                return;
            case OPERATOR:
                int indexLast = operators.size() - 1;
                operators.set(indexLast, operator);
                break;
            case NUMBER:
                operators.add(operator);
                break;
        }
        state = State.OPERATOR;
        updateFormula(callback);
    }

    public void clear(@Nullable Callback callback) {
        values.clear();
        operators.clear();
        state = State.EMPTY;
        updateFormula(callback);
        calculate(new ArrayList<>(values), new ArrayList<>(operators), callback);
    }

    public void calculate(@Nullable Callback callback) {
        Priority[] priorities = Priority.values();

        for (Priority priority : priorities) {
            calculate(values, operators, priority);
        }

        updateFormula(callback);
        updateResult(callback);
    }

    private void calculate(@NonNull List<Long> values, @NonNull List<Operator> operators, @Nullable Callback callback) {
        Priority[] priorities = Priority.values();

        for (Priority priority : priorities) {
            calculate(values, operators, priority);
        }

        updateResult(values, callback);
    }

    private void calculate(@NonNull List<Long> values, @NonNull List<Operator> operators, @NonNull Priority priority) {
        int i = 0;
        while (i < operators.size()) {
            Operator operator = operators.get(i);
            if (operator.priority == priority) {
                values.set(i, calculate(values.get(i), operator, values.get(i + 1)));
                values.remove(i + 1);
                operators.remove(i);
            } else {
                i++;
            }
        }
    }

    private long calculate(long param1, @NonNull Operator operator, long param2) {
        switch (operator) {
            case SLASH:
                try {
                    return param1 / param2;
                } catch (ArithmeticException e) {
                    return param1 + param2;
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

    private void updateFormula(@Nullable Callback callback) {
        updateFormula(values, operators, callback);
    }

    private void updateFormula(@NonNull List<Long> values, @NonNull List<Operator> operators, @Nullable Callback callback) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i).toString());
            if (i < operators.size()) {
                sb.append(operators.get(i).sign);
            }
        }

        if (callback != null) {
            callback.onFormulaUpdated(sb.toString());
        }
    }

    private void updateResult(@Nullable Callback callback) {
        updateResult(values, callback);
    }

    private void updateResult(@NonNull List<Long> values, @Nullable Callback callback) {
        if (callback != null) {
            long result = (values.size() != 0) ? values.get(0) : 0;
            callback.onResultUpdated(result);
        }
    }

    public interface Callback {
        void onFormulaUpdated(@NonNull String formula);

        void onResultUpdated(long result);
    }
}
