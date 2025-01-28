package com.ugb.miprimeraaplicacion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tempval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnCalcular);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempval = findViewById(R.id.txtNum1);
                double num1 = Double.parseDouble(tempval.getText().toString());
                tempval = findViewById(R.id.txtNum2);
                double num2 = Double.parseDouble(tempval.getText().toString());
                double respuesta = num1 + num2;
                tempval = findViewById(R.id.lblRespuesta);
                tempval.setText("Respuesta: "+ respuesta);

            }
        });
    }
}