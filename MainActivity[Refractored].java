package com.example.datecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkBoxAdd;
    private CheckBox checkBoxSubtract;
    private EditText inputNumber;
    private EditText inputDate;
    private TextView resultView;
    private Button btnCalculate;
    private Button btnClear;
    private RadioGroup radioGroupUnit;
    private RadioButton radioYear;
    private RadioButton radioMonth;
    private RadioButton radioWeek;
    private RadioButton radioDay;

    private static final String[] MONTH_NAMES = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        inputNumber = findViewById(R.id.editTextNumber3);
        inputDate = findViewById(R.id.et);
        resultView = findViewById(R.id.result);
        btnCalculate = findViewById(R.id.button);
        btnClear = findViewById(R.id.button2);
        checkBoxAdd = findViewById(R.id.checkBox);
        checkBoxSubtract = findViewById(R.id.checkBox2);
        radioGroupUnit = findViewById(R.id.radiogroup);
        radioYear = findViewById(R.id.radioButton);
        radioMonth = findViewById(R.id.radioButton2);
        radioWeek = findViewById(R.id.radioButton3);
        radioDay = findViewById(R.id.radioButton4);
    }

    private void setupListeners() {
        // Mutually exclusive checkboxes
        checkBoxAdd.setOnClickListener(v -> {
            if (checkBoxAdd.isChecked()) {
                checkBoxSubtract.setChecked(false);
            }
        });

        checkBoxSubtract.setOnClickListener(v -> {
            if (checkBoxSubtract.isChecked()) {
                checkBoxAdd.setChecked(false);
            }
        });

        btnCalculate.setOnClickListener(v -> calculateNewDate());

        btnClear.setOnClickListener(v -> clearInputs());
    }

    private void clearInputs() {
        resultView.setText("");
        inputNumber.setText("");
        inputDate.setText("");
        checkBoxAdd.setChecked(false);
        checkBoxSubtract.setChecked(false);
        Toast.makeText(getApplicationContext(), "Cleared", Toast.LENGTH_SHORT).show();
    }

    private void calculateNewDate() {
        String dateStr = inputDate.getText().toString();
        String numberStr = inputNumber.getText().toString();

        if (dateStr.isEmpty() || numberStr.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Input", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateStr.length() != 10) {
            Toast.makeText(getApplicationContext(), "Enter As per The Format", Toast.LENGTH_LONG).show();
            return;
        }

        if (numberStr.length() > 6) {
            // Prevent very large input (arbitrary limit)
            Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_SHORT).show();
            return;
        }

        int day, month, year;
        try {
            day = Integer.parseInt(dateStr.substring(0, 2));
            month = Integer.parseInt(dateStr.substring(3, 5));
            year = Integer.parseInt(dateStr.substring(6, 10));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            Toast.makeText(getApplicationContext(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Basic validation for month and day
        if (!isValidDate(day, month, year)) {
            Toast.makeText(getApplicationContext(), "Out Of Range", Toast.LENGTH_SHORT).show();
            clearInputs();
            return;
        }

        int valueToAdd;
        try {
            valueToAdd = Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isAdd = checkBoxAdd.isChecked();
        boolean isSubtract = checkBoxSubtract.isChecked();

        if (!isAdd && !isSubtract) {
            Toast.makeText(getApplicationContext(), "Select Add or Subtract", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate leap year once
        boolean leapYear = isLeapYear(year);

        // Calculate new date according to selected unit
        if (radioYear.isChecked()) {
            // Add or subtract years directly
            year = isAdd ? year + valueToAdd : year - valueToAdd;
        } else if (radioMonth.isChecked()) {
            int[] res = addMonths(year, month, valueToAdd, isAdd);
            year = res[0];
            month = res[1];
            day = adjustDayForMonth(day, month, year, leapYear);
        } else if (radioWeek.isChecked()) {
            // Multiply by 7 days
            valueToAdd *= 7;
            int[] res = addDays(year, month, day, valueToAdd, isAdd);
            year = res[0];
            month = res[1];
            day = res[2];
        } else if (radioDay.isChecked()) {
            int[] res = addDays(year, month, day, valueToAdd, isAdd);
            year = res[0];
            month = res[1];
            day = res[2];
        } else {
            Toast.makeText(getApplicationContext(), "Select a unit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Format and display result
        String newDateString = String.format("%d %s %d", day, MONTH_NAMES[month - 1], year);
        resultView.setText(newDateString);
        Toast.makeText(getApplicationContext(), newDateString, Toast.LENGTH_LONG).show();
    }

    /**
     * Checks if the given year is a leap year.
     */
    private boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else return year % 400 == 0;
    }

    /**
     * Validates the date range for day and month, considering leap years.
     */
    private boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > 12) return false;
        if (day < 1) return false;

        boolean leapYear = isLeapYear(year);

        int maxDay = 31;
        switch (month) {
            case 2:
                maxDay = leapYear ? 29 : 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDay = 30;
                break;
        }
        return day <= maxDay;
    }

    /**
     * Adjust day value to correct max days in a month.
     */
    private int adjustDayForMonth(int day, int month, int year, boolean leapYear) {
        int maxDay = 31;
        switch (month) {
            case 2:
                maxDay = leapYear ? 29 : 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDay = 30;
                break;
        }
        return Math.min(day, maxDay);
    }

    /**
     * Adds or subtracts months, adjusting year and month accordingly.
     * Returns int array with {year, month}.
     */
    private int[] addMonths(int year, int month, int monthsToAdd, boolean isAdd) {
        int totalMonths = (year * 12) + (month - 1);
        totalMonths = isAdd ? totalMonths + monthsToAdd : totalMonths - monthsToAdd;

        int newYear = totalMonths / 12;
        int newMonth = (totalMonths % 12) + 1;
        return new int[]{newYear, newMonth};
    }

    /**
     * Adds or subtracts days from a date. Returns int array {year, month, day}.
     * Naive implementation that iteratively adds or subtracts days, adjusting month/year.
     */
    private int[] addDays(int year, int month, int day, int daysToAdd, boolean isAdd) {
        int direction = isAdd ? 1 : -1;
        while (daysToAdd > 0) {
            int maxDay = getDaysInMonth(month, year);
            day += direction;

            if (day > maxDay) {
                day = 1;
                month++;
                if (month > 12) {
                    month = 1;
                    year++;
                }
            } else if (day < 1) {
                month--;
                if (month < 1) {
                    month = 12;
                    year--;
                }
                day = getDaysInMonth(month, year);
            }

            daysToAdd--;
        }
        return new int[]{year, month, day};
    }

    /**
     * Returns the number of days in the given month accounting for leap years.
     */
    private int getDaysInMonth(int month, int year) {
        boolean leapYear = isLeapYear(year);
        switch (month) {
            case 2:
                return leapYear ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }
}

