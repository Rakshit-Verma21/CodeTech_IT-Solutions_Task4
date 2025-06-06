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

    CheckBox checkBox1;
    CheckBox checkBox2;
    EditText data;
    EditText date;
    TextView result;
    Button ok;
    String resu;
    String day;
    String month;
    String year;
    int dayint;
    int monthint;
    int yearint;
    String resu2;
    int daysAdded;
    int output;
    String newday;
    String newyear;
    String newdate;
    Button clear;
    String Month="";
    String temp="";
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    int monthcounter=0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = findViewById(R.id.editTextNumber3);
        date = findViewById(R.id.et);
        result = findViewById(R.id.result);
        ok = findViewById(R.id.button);
        clear = findViewById(R.id.button2);
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        radioGroup=findViewById(R.id.radiogroup);
        radioButton=findViewById(R.id.radioButton);
        radioButton2=findViewById(R.id.radioButton2);
        radioButton3=findViewById(R.id.radioButton3);
        radioButton4=findViewById(R.id.radioButton4);




        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox1.isChecked()==true)
                {
                    checkBox2.setChecked(false);
                }

            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkBox2.isChecked()==true)
                {
                    checkBox1.setChecked(false);
                }

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                resu = date.getText().toString();
                if(resu.length()>10)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Date",Toast.LENGTH_SHORT).show();
                }
                if(resu.length()==10) {
                    resu2 = data.getText().toString();
                    if (resu.equals("") || resu2.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Input", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            day = resu.substring(0, 2);
                            month = resu.substring(3, 5);
                            year = resu.substring(6, 10);

                            dayint = Integer.parseInt(day);
                            monthint = Integer.parseInt(month);
                            yearint = Integer.parseInt(year);

                            daysAdded = Integer.parseInt(resu2);
                            if (yearint % 4 == 0) {
                                output = 1;
                            } else {
                                if (yearint % 100 == 0) {
                                    if (yearint % 400 == 0) {
                                        output = 1;
                                    } else {
                                        output = 0;
                                    }
                                }
                            }
                            if (dayint > 31 || monthint > 12) {
                                Toast.makeText(getApplicationContext(), "Out Of Range", Toast.LENGTH_SHORT).show();

                                data.setText("");
                                date.setText("");


                                daysAdded = 0;

                            }

                            int daycounter = 0;
                            if (month.equals("08")) {
                                monthint = 8;
                            } else if (month.equals("09")) {
                                monthint = 9;
                            }
                            if (radioButton.isChecked()) {
                                if (checkBox1.isChecked()) {
                                    yearint = yearint + daysAdded;
                                } else {
                                    yearint = yearint - daysAdded;
                                }

                            }
                            if (radioButton2.isChecked()) {
                                if (checkBox1.isChecked()) {
                                    monthcounter = monthint;
                                    while (daysAdded > 0) {
                                        if (monthcounter < 12) {

                                            monthint++;
                                            monthcounter++;
                                            daysAdded--;


                                        } else if (monthcounter == 12) {
                                            monthint = 1;
                                            monthcounter = 1;
                                            yearint++;
                                            daysAdded--;

                                        }
                                    }
                                    if (dayint < 30) {
                                        dayint = dayint;
                                    } else {

                                        if (monthint == 01 || monthint == 03 || monthint == 05 || monthint == 07 || monthint == 8 || monthint == 10 || monthint == 12) {
                                            dayint = 31;


                                        } else if (monthint == 04 || monthint == 06 || monthint == 9 || monthint == 11) {
                                            dayint = 30;
                                        } else if (output == 1) {
                                            dayint = 29;
                                        } else {
                                            dayint = 28;
                                        }
                                    }
                                }
                                if (checkBox2.isChecked()) {

                                    monthcounter = monthint;
                                    while (daysAdded > 0) {
                                        if (monthcounter > 1) {

                                            monthint--;
                                            daysAdded--;
                                            monthcounter--;

                                        } else if (monthcounter == 1) {
                                            monthint = 12;
                                            monthcounter = 12;
                                            yearint--;

                                            daysAdded--;

                                        }
                                    }

                                    if (dayint < 30) {
                                        dayint = dayint;
                                    } else {

                                        if (monthint == 01 || monthint == 03 || monthint == 05 || monthint == 07 || monthint == 8 || monthint == 10 || monthint == 12) {
                                            dayint = 31;


                                        } else if (monthint == 04 || monthint == 06 || monthint == 9 || monthint == 11) {
                                            dayint = 30;
                                        } else if (output == 1) {
                                            dayint = 29;
                                        } else {
                                            dayint = 28;
                                        }
                                    }
                                }
                            }


                            if (radioButton4.isChecked() || radioButton3.isChecked()) {
                                if (radioButton3.isChecked()) {
                                    daysAdded = daysAdded * 7;
                                } else {
                                    daysAdded = daysAdded;
                                }

                                if (checkBox1.isChecked()) {


                                    daycounter = dayint;
                                    while (daysAdded > 0) {


                                        if (monthint == 01 || monthint == 03 || monthint == 05 || monthint == 07 || monthint == 8 || monthint == 10) {
                                            if (daycounter < 31) {
                                                dayint++;
                                                daycounter++;

                                                daysAdded--;

                                            } else if (daycounter == 31) {
                                                monthint++;

                                                dayint = 0;

                                                daycounter = 0;

                                            }

                                        } else if (monthint == 04 || monthint == 06 || monthint == 9 || monthint == 11) {
                                            if (daycounter < 30) {
                                                dayint++;
                                                daycounter++;

                                                daysAdded--;

                                            } else if (daycounter == 30) {
                                                monthint++;

                                                dayint = 0;

                                                daycounter = 0;

                                            }

                                        } else if (monthint == 02) {
                                            if (output == 1) {

                                                if (daycounter < 29) {
                                                    dayint++;
                                                    daycounter++;

                                                    daysAdded--;

                                                } else if (daycounter == 29) {
                                                    monthint++;
                                                    dayint = 0;

                                                    daycounter = 0;

                                                }
                                            } else {
                                                if (daycounter < 28) {
                                                    dayint++;
                                                    daycounter++;

                                                    daysAdded--;

                                                } else if (daycounter == 28) {
                                                    monthint++;

                                                    dayint = 0;

                                                    daycounter = 0;

                                                }
                                            }
                                        } else if (monthint == 12) {
                                            if (daycounter < 31) {
                                                dayint++;
                                                daycounter++;
                                                daysAdded--;
                                            } else if (daycounter == 31) {
                                                dayint = 0;
                                                monthint = 1;
                                                yearint++;
                                                daycounter = 0;
                                            }


                                        }
                                    }
                                }
                                if (checkBox2.isChecked()) {
                                    daycounter = dayint;
                                    while (daysAdded > 0) {
                                        if (monthint == 12 || monthint == 03 || monthint == 05 || monthint == 07 || monthint == 8 || monthint == 10) {
                                            if (daycounter > 0) {
                                                dayint--;
                                                daycounter--;

                                                daysAdded--;

                                            } else if (daycounter == 0) {
                                                monthint--;

                                                dayint = 31;

                                                daycounter = 31;

                                            }

                                        } else if (monthint == 04 || monthint == 06 || monthint == 9 || monthint == 11) {
                                            if (daycounter > 0) {
                                                dayint--;
                                                daycounter--;

                                                daysAdded--;

                                            } else if (daycounter == 0) {
                                                monthint--;

                                                dayint = 30;

                                                daycounter = 30;

                                            }

                                        } else if (monthint == 02) {
                                            if (output == 1) {

                                                if (daycounter > 0) {
                                                    dayint--;
                                                    daycounter--;

                                                    daysAdded--;

                                                } else if (daycounter == 0) {
                                                    monthint--;
                                                    dayint = 29;

                                                    daycounter = 29;

                                                }
                                            } else {
                                                if (daycounter > 0) {
                                                    dayint--;
                                                    daycounter--;

                                                    daysAdded--;

                                                } else if (daycounter == 0) {
                                                    monthint--;

                                                    dayint = 28;

                                                    daycounter = 28;

                                                }
                                            }
                                        } else if (monthint == 01) {
                                            if (daycounter > 0) {
                                                dayint--;
                                                daycounter--;
                                                daysAdded--;
                                            } else if (daycounter == 0) {
                                                dayint = 31;
                                                monthint = 12;
                                                yearint--;
                                                daycounter = 31;
                                            }
                                        }
                                    }


                                }

                            }

                            if (monthint <= 12) {
                                String monthArr[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                                for (int i = 0; i <= 12; i++) {
                                    Month = Month + monthArr[monthint - 1];
                                    temp = temp + Month;
                                    Month = "";
                                    i = 0;
                                    break;
                                }
                                newday = String.valueOf(dayint);
                                newyear = String.valueOf(yearint);
                                newday = newday + " " + temp + " " + newyear;


                                result.setText(newday);
                                temp = "";

                                Toast.makeText(getApplicationContext(), newday, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Out OF Range", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                    {
                        Toast.makeText(getApplicationContext(),"Enter As per The Format",Toast.LENGTH_LONG).show();
                    }
                }



        });




        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result.setText("");
                data.setText("");
                date.setText("");
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                Toast.makeText(getApplicationContext(),"Cleared",Toast.LENGTH_SHORT).show();
            }
        });



    }
}