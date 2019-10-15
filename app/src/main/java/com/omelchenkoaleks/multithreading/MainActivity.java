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
import com.omelchenkoaleks.multithreading._007_async_task_begin.AsyncTaskBeginActivity;
import com.omelchenkoaleks.multithreading._008_async_task_attr.AsyncTaskAttrActivity;
import com.omelchenkoaleks.multithreading._009_async_task_get.AsyncTaskGetActivity;
import com.omelchenkoaleks.multithreading._010_async_task_cancel.CancelActivity;
import com.omelchenkoaleks.multithreading._011_async_task_status.AsyncTaskStatusActivity;
import com.omelchenkoaleks.multithreading._012_async_task_rotation.RotationActivity;
import com.omelchenkoaleks.multithreading._013_loader.LoaderActivity;

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

            case R.id.async_task_begin_button:
                Intent asyncTaskBeginIntent = new Intent(this, AsyncTaskBeginActivity.class);
                startActivity(asyncTaskBeginIntent);

            case R.id.async_task_attr_button:
                Intent asyncTaskParameterIntent = new Intent(this, AsyncTaskAttrActivity.class);
                startActivity(asyncTaskParameterIntent);

            case R.id.async_task_get_button:
                Intent asyncTaskGetIntent = new Intent(this, AsyncTaskGetActivity.class);
                startActivity(asyncTaskGetIntent);

            case R.id.async_task_cancel_button:
                Intent asyncTaskCancelIntent = new Intent(this, CancelActivity.class);
                startActivity(asyncTaskCancelIntent);

            case R.id.status_async_button:
                Intent statusIntent = new Intent(this, AsyncTaskStatusActivity.class);
                startActivity(statusIntent);

            case R.id.rotation_button:
                Intent rotationIntent = new Intent(this, RotationActivity.class);
                startActivity(rotationIntent);

            case R.id.loader_button:
                Intent loaderIntent = new Intent(this, LoaderActivity.class);
                startActivity(loaderIntent);

        }
    }
}
