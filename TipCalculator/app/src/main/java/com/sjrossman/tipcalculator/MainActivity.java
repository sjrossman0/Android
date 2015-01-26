package com.sjrossman.tipcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends ActionBarActivity {
    private int percent, people;
    private double total, perPerson;
    private EditText etTotal;
    private TextView tvPercent, tvPeople, tvPerPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.total = 100.00;
        this.percent = 15;
        this.people = 1;
        this.perPerson = 0.0;

        etTotal = (EditText) findViewById(R.id.etTotal);
        etTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTotal.getText().toString().length() > 0) {
                    total = Double.parseDouble(etTotal.getText().toString());
                } else {
                    total = 0;
                }
            }
        });
        tvPercent = (TextView) findViewById(R.id.tvPercent);
        tvPeople = (TextView) findViewById(R.id.tvPeople);
        tvPerPerson = (TextView) findViewById(R.id.tvPerPerson);
        rebindViews();
    }

    public void decrementPercent(View view) {
        percent = Math.max(percent - 1, 0);
        rebindViews();
    }

    public void incrementPercent(View view) {
        percent = Math.min(percent + 1, 100);
        rebindViews();
    }

    public void decrementPeople(View view) {
        people = Math.max(people - 1, 1);
        rebindViews();
    }

    public void incrementPeople(View view) {
        people += 1;
        rebindViews();
    }

    public void calculate(View view) {
        if (etTotal.getText().toString().length() > 0) {
            double tipMultiplier = 1 + percent/100.0;
            double totalWithTip = total * tipMultiplier;
            perPerson = totalWithTip / people;
            rebindViews();
        } else {
            Toast.makeText(getApplicationContext(), "Total is required, dumbass.", Toast.LENGTH_LONG).show();
        }
    }

    private void rebindViews() {
        etTotal.setText(getCurrencyString(total, false));
        tvPercent.setText(getPercentString());
        tvPeople.setText(String.valueOf(people));
        tvPerPerson.setText(getCurrencyString(perPerson, true));
    }

    private String getCurrencyString(double value, boolean includeDollar) {
        String result = NumberFormat.getCurrencyInstance().format(value);
        return includeDollar ? result : result.replace("$","");
    }

    private String getPercentString() {
        return NumberFormat.getPercentInstance().format(percent/100.0);
    }
}