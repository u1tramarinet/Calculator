package com.example.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private enum State {
        EMPTY,
        NUMBER,
        OPERATOR;
    }

    private State state = State.EMPTY;
    private final MutableLiveData<String> formula = new MutableLiveData<>();

    public LiveData<String> formula() {
        return formula;
    }

    public void addNumber(int number) {
        addToFomula(String.valueOf(number));
        state = State.NUMBER;
    }

    public void addOperato(String operator) {
        addToFomula(operator);
        state = State.OPERATOR;
    }

    public void clear() {
        formula.postValue("");
        state = State.EMPTY;
    }

    public void calculate() {
        if (state != State.NUMBER) {
            return;
        }
    }

    private void addToFomula(String s) {
        String f = formula.getValue();
        if (f != null) {
            s = f + s;
        }
        formula.postValue(s);
    }
}
