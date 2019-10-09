package com.omelchenkoaleks.multithreading._005_handler_runnable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.TimeUnit;

/*
    Handler может выполнить кусок кода - Runnable. Runnable будет выполнен в потоке, с которым
    работает Handler. Для отправки кода в работу используется метод post(). Runnable
    может быть выполнен с задержкой (postDelayed(), и может быть удален из очереди (removeCallback).
 */
public class HandlerRunnableActivity extends AppCompatActivity {
    private static final String TAG = "HandlerRunnableActivity";

    private TextView mInfoTextView;
    private CheckBox mClickCheckBox;
    private ProgressBar mInfoProgressBar;

    private int count;
    private final int max = 100;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_005_handler_runnable);

        mHandler = new Handler();

        mInfoTextView = findViewById(R.id.count_info_text_view);
        mClickCheckBox = findViewById(R.id.click_check_box);
        mInfoProgressBar = findViewById(R.id.count_progress_bar);

        mClickCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mInfoTextView.setVisibility(View.VISIBLE);
                    // показываем информацию
                    mHandler.post(showInfo);
                } else {
                    mInfoTextView.setVisibility(View.GONE);
                    // отменяем показ информации
                    mHandler.removeCallbacks(showInfo);
                }
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    for (count = 1; count < max; count++) {
                        TimeUnit.MILLISECONDS.sleep(100);
                        // обновляем ProgressBar
                        mHandler.post(updateProgress);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    // обновление ProgressBar
    Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            mInfoProgressBar.setProgress(count);
        }
    };

    // показ информации
    Runnable showInfo = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "showInfo");
            mInfoTextView.setText("Count: " + count);
            // планирует сам себя через 1000 мсек
            mHandler.postDelayed(showInfo, 1000);
        }
    };
}
