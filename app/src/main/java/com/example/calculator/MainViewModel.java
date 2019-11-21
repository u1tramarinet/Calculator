package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private enum State {
        EMPTY,
        NUMBER,
        OPERATOR
    }

    private State state = State.EMPTY;
    private final MutableLiveData<String> formula = new MutableLiveData<>();

    private List<Integer> values = new ArrayList<>();
    private List<Operator> operators = new ArrayList<>();

    public LiveData<String> formula() {
        return formula;
    }

    public void addNumber(int number) {
        switch (state) {
            case EMPTY:
            case OPERATOR:
                values.add(number);
                break;
            case NUMBER:
                int targetIndex = values.size() - 1;
                int nVal = values.get(targetIndex) * 10 + number;
                values.set(targetIndex, nVal);
                break;
        }
        state = State.NUMBER;
        updateFormula();
    }

    public void addOperator(@NonNull Operator operator) {
        switch (state) {
            case EMPTY:
                return;
            case OPERATOR:
                int oLen = operators.size();
                operators.set(oLen - 1, operator);
                break;
            case NUMBER:
                operators.add(operator);
                break;
        }
        state = State.OPERATOR;
        updateFormula();
    }

    public void clear() {
        values.clear();
        operators.clear();
        state = State.EMPTY;
        updateFormula();
    }

    public void calculate() {
        if ((state != State.NUMBER) || (values.size() == 1)) {
            return;
        }
        int i = 0;

        // handle "*" or "/"
        while (i < operators.size()) {
            Operator operator = operators.get(i);
            switch (operator) {
                case ASTERISK:
                case SLASH:
                    values.set(i, Operator.calculate(values.get(i), operator, values.get(i + 1)));
                    values.remove(i + 1);
                    operators.remove(i);
                    break;
                case PLUS:
                case MINUS:
                    i++;
                    break;
            }
        }

        // handle "+" or "-"
        i = 0;
        while (i < operators.size()) {
            Operator operator = operators.get(i);
            switch (operator) {
                case PLUS:
                case MINUS:
                    values.set(i, Operator.calculate(values.get(i), operator, values.get(i + 1)));
                    values.remove(i + 1);
                    operators.remove(i);
                    break;
                case ASTERISK:
                case SLASH:
                    i++;
                    break;
            }
        }
        updateFormula();
    }

    private void updateFormula() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < values.size(); i++) {
            sb.append(values.get(i).toString());
            if (i < operators.size()) {
                sb.append(operators.get(i).sign);
            }
        }
        formula.postValue(sb.toString());
    }
}
