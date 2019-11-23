package com.example.calculator;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment {

    private MainViewModel viewModel;
    private TextView textViewFormula;
    private TextView textViewResult;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = MainActivity.obtainViewModel(getActivity());
        viewModel.formula().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String formula) {
                textViewFormula.setText(formula);
            }
        });
        viewModel.result().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long result) {
                textViewResult.setText(getString(R.string.format_result, result));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(@NonNull View root) {
        textViewFormula = root.findViewById(R.id.text_formula);
        textViewResult = root.findViewById(R.id.text_result);

        initNumberButtons(root);
        initOperatorButton(root, R.id.button_slash, Operator.SLASH);
        initOperatorButton(root, R.id.button_asterisk, Operator.ASTERISK);
        initOperatorButton(root, R.id.button_plus, Operator.PLUS);
        initOperatorButton(root, R.id.button_minus, Operator.MINUS);

        Button buttonClear = root.findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.clear();
            }
        });
        Button buttonEqual = root.findViewById(R.id.button_equal);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.calculate();
            }
        });
    }

    private void initNumberButtons(@NonNull View root) {
        @IdRes int[] buttonNumberIds = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
                R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9
        };

        for (int i = 0; i < buttonNumberIds.length; i++) {
            initNumberButton(root, buttonNumberIds[i], i);
        }
    }

    private void initNumberButton(@NonNull View root, @IdRes int buttonId, int number) {
        root.findViewById(buttonId)
                .setOnClickListener(new OnNumberClickListener(number));
    }

    private void initOperatorButton(@NonNull View root, @IdRes int buttonId, @NonNull Operator operator) {
        root.findViewById(buttonId)
                .setOnClickListener(new OnOperatorClickListener(operator));
    }

    private void addNumber(long number) {
        viewModel.addNumber(number);
    }

    private void addOperator(@NonNull Operator operator) {
        viewModel.addOperator(operator);
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
