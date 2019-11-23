package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    @NonNull
    private final Calculator calculator = Calculator.getInstance();
    @NonNull
    private final MutableLiveData<String> formula = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Long> result = new MutableLiveData<>();
    @NonNull
    private Calculator.Callback callback = new Calculator.Callback() {
        @Override
        public void onFormulaUpdated(@NonNull String formula) {
            updateFormula(formula);
        }

        @Override
        public void onResultUpdated(long result) {
            updateResult(result);
        }
    };

    public MainViewModel() {
        result.setValue(0L);
    }

    public LiveData<String> formula() {
        return formula;
    }

    public LiveData<Long> result() {
        return result;
    }

    public void addNumber(long number) {
        calculator.addNumber(number, callback);
    }

    public void addOperator(@NonNull Operator operator) {
        calculator.addOperator(operator, callback);
    }

    public void clear() {
        calculator.clear(callback);
    }

    public void calculate() {
        calculator.calculate(callback);
    }

    private void updateFormula(@NonNull String formula) {
        this.formula.postValue(formula);
    }

    private void updateResult(long result) {
        this.result.postValue(result);
    }
}
