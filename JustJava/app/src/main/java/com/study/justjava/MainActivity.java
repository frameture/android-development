package com.study.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int quantity;
    private TextView quant, total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quant = (TextView)findViewById(R.id.quantity);
        quantity = Integer.parseInt(quant.getText().toString());
        total = (TextView)findViewById(R.id.total);

        findViewById(R.id.btn_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity += 1;
                quant.setText("" + quantity);
                quant.invalidate();
            }
        });

        findViewById(R.id.btn_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity -= 1;
                if(quantity <= 0)
                    quantity = 0;
                quant.setText("" + quantity);
                quant.invalidate();
            }
        });

        findViewById(R.id.btn_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total.setText( "Total: $ " + quantity * 10);
                total.invalidate();
            }
        });
    }
}
