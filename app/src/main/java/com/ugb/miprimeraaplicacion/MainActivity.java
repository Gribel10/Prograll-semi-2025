package com.ugb.miprimeraaplicacion;

import static com.ugb.miprimeraaplicacion.R.id.txtNum1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tempVal;
    RadioGroup rgb;
    RadioButton opt;




        public void onClick(View v) {
            tempVal = findViewById(R.id.txtNum1);
            double num1 = Double.parseDouble(tempVal.getText().toString());
            tempVal = findViewById(R.id.txtNum2);
            double num2 = Double.parseDouble(tempVal.getText().toString());
            num2 = tempVal.isEnabled() ? Double.parseDouble(tempVal.getText().toString()) : 0;
            double respuesta = 0.0;


            opt = findViewById(R.id.optSuma);
            if (opt.isChecked()) {
                respuesta = num1 + num2;
            }
            opt = findViewById(R.id.optResta);
            if (opt.isChecked()) {
                respuesta = num1 - num2;
            }
            opt = findViewById(R.id.optMultiplicacion);
            if (opt.isChecked()) {
                respuesta = num1 * num2;
            }
            opt = findViewById(R.id.optDivision);
            if (opt.isChecked()) {
                respuesta = num1 / num2;
            }

            opt = findViewById(R.id.optRaiz);
            if (opt.isChecked()) {
                respuesta = Math.sqrt(num1);
            }
            opt = findViewById(R.id.optFactorial);
            if (opt.isChecked()) {
                respuesta = 1;
                for (int i = 2; i <= num1; i++) {
                    respuesta *= i;
                }
            }
            opt = findViewById(R.id.optCubica);
            if (opt.isChecked()) {
                respuesta = Math.cbrt(num1);
            }

            tempVal = findViewById(R.id.lblRespuesta);
            tempVal.setText("Respuesta: " + respuesta);
            tempVal.setText("Respuesta: " + respuesta);
        }
    }







