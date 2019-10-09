package com.omelchenkoaleks.multithreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.omelchenkoaleks.multithreading._001_handler.HandlerActivity;
import com.omelchenkoaleks.multithreading._002_handler_want.WantActivity;
import com.omelchenkoaleks.multithreading._003_handler_arg1_arg2_obj.HandlerAttributeActivity;
import com.omelchenkoaleks.multithreading._004_handler_delayd_remove_callback.DelayedActivity;
import com.omelchenkoaleks.multithreading._005_handler_runnable.HandlerRunnableActivity;
import com.omelchenkoaleks.multithreading._006_handler_test_methods.TestMethodsActivity;

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

            case R.id.handler_attribute_button:
                Intent handlerAttributyIntent = new Intent(this, HandlerAttributeActivity.class);
                startActivity(handlerAttributyIntent);

            case R.id.handler_delayed_button:
                Intent handlerDelayedIntent = new Intent(this, DelayedActivity.class);
                startActivity(handlerDelayedIntent);

            case R.id.handler_runnable_button:
                Intent handlerRunnableIntent = new Intent(this, HandlerRunnableActivity.class);
                startActivity(handlerRunnableIntent);

            case R.id.handler_test_methods_button:
                Intent handlerTestMethodsIntent = new Intent(this, TestMethodsActivity.class);
                startActivity(handlerTestMethodsIntent);
        }
    }
}
