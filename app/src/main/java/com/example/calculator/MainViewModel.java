package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    @NonNull
    private final Calculator calculator = Calculator.getInstance();

    private final MutableLiveData<String> formula = new MutableLiveData<>();
    private final MutableLiveData<Long> result = new MutableLiveData<>();

    public MainViewModel() {
        formula.setValue("0");
        result.setValue(0L);
    }

    public LiveData<String> formula() {
        return formula;
    }

    public LiveData<Long> result() {
        return result;
    }

    public void addNumber(long number) {
        calculator.addNumber(number, new Calculator.Callback() {
            @Override
            public void onFormulaUpdated(@NonNull String formula) {
                updateFormula(formula);
            }

            @Override
            public void onResultUpdated(long result) {
                updateResult(result);
            }
        });
    }

    public void addOperator(@NonNull Operator operator) {
        calculator.addOperator(operator, new Calculator.Callback() {
            @Override
            public void onFormulaUpdated(@NonNull String formula) {
                updateFormula(formula);
            }

            @Override
            public void onResultUpdated(long result) {
            }
        });
    }

    public void clear() {
        calculator.clear(new Calculator.Callback() {
            @Override
            public void onFormulaUpdated(@NonNull String formula) {
                updateFormula(formula);
            }

            @Override
            public void onResultUpdated(long result) {
                updateResult(result);
            }
        });
    }

    public void calculate() {
        calculator.calculate(new Calculator.Callback() {
            @Override
            public void onFormulaUpdated(@NonNull String formula) {
                updateFormula(formula);
            }

            @Override
            public void onResultUpdated(long result) {
                updateResult(result);
            }
        });
    }

    private void updateFormula(@NonNull String formula) {
        this.formula.postValue(formula);
    }

    private void updateResult(long result) {
        this.result.postValue(result);
    }
}
