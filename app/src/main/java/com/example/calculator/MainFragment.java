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

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;

    private Button buttonSlash;
    private Button buttonAsterisk;
    private Button buttonPlus;
    private Button buttonMinus;

    private Button buttonClear;
    private Button buttonEqual;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
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
            public void onChanged(String s) {
                textViewFormula.setText(s);
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

    private void initViews(View root) {
        textViewFormula = root.findViewById(R.id.text_formula);

        button0 = obtainNumberButton(root, R.id.button_0, 0);
        button1 = root.findViewById(R.id.button_1);
        button1.setOnClickListener(new OnNumberClickListener(1));
        button2 = root.findViewById(R.id.button_2);
        button2.setOnClickListener(new OnNumberClickListener(2));
        button3 = root.findViewById(R.id.button_3);
        button3.setOnClickListener(new OnNumberClickListener(3));
        button4 = root.findViewById(R.id.button_4);
        button4.setOnClickListener(new OnNumberClickListener(4));
        button5 = root.findViewById(R.id.button_5);
        button5.setOnClickListener(new OnNumberClickListener(5));
        button6 = root.findViewById(R.id.button_6);
        button6.setOnClickListener(new OnNumberClickListener(6));
        button7 = root.findViewById(R.id.button_7);
        button7.setOnClickListener(new OnNumberClickListener(7));
        button8 = root.findViewById(R.id.button_8);
        button8.setOnClickListener(new OnNumberClickListener(8));
        button9 = root.findViewById(R.id.button_9);
        button9.setOnClickListener(new OnNumberClickListener(9));

        buttonSlash = root.findViewById(R.id.button_slash);
        buttonSlash.setOnClickListener(new OnOperatorClickListener(Operator.SLASH));
        buttonAsterisk = root.findViewById(R.id.button_asterisk);
        buttonAsterisk.setOnClickListener(new OnOperatorClickListener(Operator.ASTERISK));
        buttonPlus = root.findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(new OnOperatorClickListener(Operator.PLUS));
        buttonMinus = root.findViewById(R.id.button_minus);
        buttonMinus.setOnClickListener(new OnOperatorClickListener(Operator.MINUS));

        buttonClear = root.findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.clear();
            }
        });
        buttonEqual = root.findViewById(R.id.button_equal);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.calculate();
            }
        });
    }

    private Button obtainNumberButton(View root, @IdRes int id, int number) {
        View view = root.findViewById(id);
        view.setOnClickListener(new OnNumberClickListener(number));
        return (Button) view;
    }

    private Button obtainNumberButton(View view, int number) {
        view.setOnClickListener(new OnNumberClickListener(number));
        return (Button) view;
    }

    private void addNumber(int number) {
        viewModel.addNumber(number);
    }

    private void addOperator(@NonNull Operator operator) {
        viewModel.addOperator(operator);
    }

    private class OnNumberClickListener implements View.OnClickListener {
        private final int number;
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
