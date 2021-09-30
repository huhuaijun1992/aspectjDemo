package com.hhj.aspectjdemo.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hhj.aspectjdemo.R;
import com.hhj.aspectjdemo.loginAspect.LoginFilter;
//import com.hhj.aspectjdemo.loginAspect.LoginFilter;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.hello);
        button = findViewById(R.id.btn_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                startActivity(intent);
//              openSecondActivity();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openSecondActivity();
            }
        });

    }

    @LoginFilter(loginDefined = 0)
    void openSecondActivity(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

}