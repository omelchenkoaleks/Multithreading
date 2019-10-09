package com.omelchenkoaleks.multithreading._006_handler_test_methods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.TimeUnit;

/*
    Тестируем методы - без Handler
        Activity.runOnUiThread(Runnable)
        View.post(Runnable)
        View.postDelayed(Runnable, long)
 */
public class TestMethodsActivity extends AppCompatActivity {

    private TextView mLooksInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_006_test_methods);

        mLooksInfoTextView = findViewById(R.id.looks_info_text_view);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(run_1);
                    TimeUnit.SECONDS.sleep(1);
                    mLooksInfoTextView.postDelayed(run_3, 2000);
                    mLooksInfoTextView.post(run_2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private Runnable run_1 = new Runnable() {
        @Override
        public void run() {
            mLooksInfoTextView.setText("run_1");
        }
    };

    private Runnable run_2 = new Runnable() {
        @Override
        public void run() {
            mLooksInfoTextView.setText("run_2");
        }
    };

    private Runnable run_3 = new Runnable() {
        @Override
        public void run() {
            mLooksInfoTextView.setText("run_3");
        }
    };
}
