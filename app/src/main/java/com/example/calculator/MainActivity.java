package com.example.calculator;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewFormula;
    private TextView textViewResult;

    @NonNull
    private final Calculator calculator = Calculator.getInstance();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        updateResult(0L);
    }

    private void initViews() {
        textViewFormula = findViewById(R.id.text_formula);
        textViewResult = findViewById(R.id.text_result);

        initNumberButtons();
        initOperatorButton(R.id.button_slash, Operator.SLASH);
        initOperatorButton(R.id.button_asterisk, Operator.ASTERISK);
        initOperatorButton(R.id.button_plus, Operator.PLUS);
        initOperatorButton(R.id.button_minus, Operator.MINUS);

        Button buttonClear = findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.clear(callback);
            }
        });
        Button buttonEqual = findViewById(R.id.button_equal);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.calculate(callback);
            }
        });
    }

    private void initNumberButtons() {
        @IdRes int[] buttonNumberIds = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
                R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9
        };

        for (int i = 0; i < buttonNumberIds.length; i++) {
            initNumberButton(buttonNumberIds[i], i);
        }
    }

    private void initNumberButton(@IdRes int buttonId, int number) {
        findViewById(buttonId)
                .setOnClickListener(new OnNumberClickListener(number));
    }

    private void initOperatorButton(@IdRes int buttonId, @NonNull Operator operator) {
        findViewById(buttonId)
                .setOnClickListener(new OnOperatorClickListener(operator));
    }

    private void addNumber(long number) {
        calculator.addNumber(number, callback);
    }

    private void addOperator(@NonNull Operator operator) {
        calculator.addOperator(operator, callback);
    }

    private void updateFormula(@NonNull String formula) {
        textViewFormula.setText(formula);
    }

    private void updateResult(long result) {
        textViewResult.setText(getString(R.string.format_result, result));
    }

    private class OnNumberClickListener implements View.OnClickListener {
        private final long number;

        OnNumberClickListener(int number) {
            this.number = number;
        }

        @Override
        public void onClick(View view) {
            addNumber(number);
        }
    }

    private class OnOperatorClickListener implements View.OnClickListener {
        @NonNull
        private final Operator operator;

        OnOperatorClickListener(@NonNull Operator operator) {
            this.operator = operator;
        }

        @Override
        public void onClick(View view) {
            addOperator(operator);
        }
    }
}
