package com.omelchenkoaleks.multithreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.omelchenkoaleks.multithreading._001_handler.HandlerActivity;
import com.omelchenkoaleks.multithreading._002_handler_want.WantActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.handler_button:
                Intent handlerIntent = new Intent(this, HandlerActivity.class);
                startActivity(handlerIntent);

            case R.id.handler_want_button:
                Intent handlerWantIntent = new Intent(this, WantActivity.class);
                startActivity(handlerWantIntent);
        }
    }
}
